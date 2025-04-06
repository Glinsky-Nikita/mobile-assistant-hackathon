
from fastapi import FastAPI, UploadFile, HTTPException, status
from fastapi.responses import JSONResponse
import sqlite3
import os
import uuid
import json
import urllib3
import requests
import asyncio
from typing import Optional

app = FastAPI()

DATABASE_URL = "mydatabase.db"
UPLOAD_DIR = "uploads"
API_URL = 'https://ai.rt.ru/api/1.0/whisper/audio'
DOWNLOAD_URL = 'https://ai.rt.ru/api/1.0/download'
TOKEN = 'eyJhbGciOiJIUzM4NCJ9.eyJzY29wZXMiOlsid2hpc3BlciJdLCJzdWIiOiJoazYiLCJpYXQiOjE3NDI0NzkwNjIsImV4cCI6MjYwNjM5MjY2Mn0.TI2FFayVt1jZHxKh7KhB4_9WdwFmXPNstaNpkyBNUibI2nIRzBWj1ION7HjSTU1n'
GIGACHAT_API_URL = 'https://ai.rt.ru/api/1.0/gigachat/chat'
GIGACHAT_TOKEN = 'eyJhbGciOiJIUzM4NCJ9.eyJzY29wZXMiOlsiZ2lnYUNoYXQiXSwic3ViIjoiaGs2IiwiaWF0IjoxNzQzMDAzMTM1LCJleHAiOjE3NDQyMTI3MzV9.EYimXdXXzCtNRGvY5qH3dFYzngAHcVveLZthxIC8YyuXEZBfO3XERmhkgEBkrc45'

os.makedirs(UPLOAD_DIR, exist_ok=True)


def create_table():
    conn = sqlite3.connect(DATABASE_URL)
    cursor = conn.cursor()
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS files (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            filename TEXT NOT NULL,
            filepath TEXT NOT NULL,
            file_diar TEXT,
            file_summ TEXT
        )
    """)
    conn.commit()
    conn.close()


create_table()


async def diarization_audio(file_path: str, file_id: int) -> Optional[str]:
    """Разметка аудио по спикерам и сохранение в БД."""
    try:
        uuid_str = str(uuid.uuid4())

        headers = {
            "Authorization": f"Bearer {TOKEN}"
        }

        request_payload = {
            "uuid": uuid_str,
            "audio": {
                "model": "whisper-fast",
                "task": "diar",
                "sigm_tr": 1,
                "prompt": "обращай внимание на голоса, каждый говорящий человек говорит скорее всего не одну фразу",
                "vad_filter": True,
                "rnoise": True
            }
        }
        request_json_str = json.dumps(request_payload)

        if file_path.endswith('.mp4'):
            mime = 'video/mp4'
        elif file_path.endswith('.mp3'):
            mime = 'audio/mpeg'
        elif file_path.endswith('.3gp') or file_path.endswith('.wav'):
            mime = 'audio/mpeg'
        else:
            mime = 'application/octet-stream'

        with open(file_path, 'rb') as f:
            files = {
                "request": (None, request_json_str, 'application/json'),
                "file": (file_path, f, mime)
            }
            response = requests.post(API_URL, headers=headers, files=files, verify=False)
            print(response)

        if response.ok:
            data = response.json()
            attachment_id = None

            for item in data:
                msg = item['message']
                print(msg)
                content = msg.get('content', '').strip().lower()
                if msg['type'] == 'attachment' and content == "субтитры":
                    attachment_id = msg['id']
                    break

            if attachment_id:
                print(f"Найден ID вложения: {attachment_id}")
                download_params = {
                    'id': str(attachment_id),
                    'serviceType': 'whisper',
                    'role': 'assistant'
                }

                download_response = requests.get(
                    DOWNLOAD_URL,
                    headers=headers,
                    params=download_params,
                    verify=False
                )
                if download_response.ok:
                    diarization_text = download_response.content.decode('utf-8')
                    print("Субтитры успешно получены.")
                else:
                    diarization_text = f"Ошибка при скачивании субтитров: {download_response.status_code} {download_response.text}"
                    print(diarization_text)
            else:
                diarization_text = "В ответе не найдено вложение с субтитрами."
                print(diarization_text)

        else:
            diarization_text = f"Ошибка при отправке запроса: {response.status_code} {response.text}"
            print(diarization_text)

    except Exception as e:
        print(f"Ошибка при разметке по спикерам или записи в БД: {e}")
        diarization_text = f"Ошибка при разметке: {e}"

  
    try:
        conn = sqlite3.connect(DATABASE_URL)
        cursor = conn.cursor()
        cursor.execute("UPDATE files SET file_diar = ? WHERE id = ?", (diarization_text, file_id))
        conn.commit()
        conn.close()
    except Exception as db_error:
        print(f"Ошибка записи в БД: {db_error}")

    return diarization_text


async def summarization_text(full_text: str, file_id: int) -> Optional[str]:
    try:
        chat_payload = {
            "uuid": str(uuid.uuid4()),
            "chat": {
            "messages": [
                {
                    "role": "user",
                    "content": f"Привет, перескажи вкратце текст, и если есть, обязательно отметь сроки и договорённости упомянутые в тексте, если нет, то просто кратко перескажи текст.\n"
                },
                {
                    "role": "assistant",
                    "content": "Привет! Я сделаю краткую аннотацию твоего текста по запросу!"
                },
                {
                    "role": "user",
                    "content": f"Вот, держи текст, пересказ которого с вниманием к датам, срокам и договорённостям(если такие есть) я ожидаю:\n{full_text}"
                }
            ],
                "model": "GigaChat",
                "temperature": 0.7,
                "top_p": 0.9,
                "max_tokens": 1024,
                "repetition_penalty": 1.1
            }
        }

        headers = {
            "Authorization": f"Bearer {GIGACHAT_TOKEN}",
            "Content-Type": "application/json"
        }

        response = requests.post(
            GIGACHAT_API_URL,
            headers=headers,
            data=json.dumps(chat_payload),
            verify=False
        )

        if response.ok:
            result = response.json()
            summary_text = result[0]["message"]["content"]
            print("Ответ модели:\n", summary_text)
        else:
            summary_text = f"Ошибка: {response.status_code} {response.text}"
            print(summary_text)

    except Exception as e:
        print(f"Ошибка при создании summary: {e}")
        summary_text = f"Ошибка при создании summary: {e}"

   
    try:
        conn = sqlite3.connect(DATABASE_URL)
        cursor = conn.cursor()
        cursor.execute("UPDATE files SET file_summ = ? WHERE id = ?", (summary_text, file_id))
        conn.commit()
        conn.close()
    except Exception as db_error:
        print(f"Ошибка записи в БД: {db_error}")

    return summary_text


@app.post("/upload/")
async def upload_file(file: UploadFile):
    try:
        if not file:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="No file uploaded.")

        filename = file.filename
        filepath = os.path.join(UPLOAD_DIR, filename)

        with open(filepath, "wb") as f:
            f.write(await file.read())

        conn = sqlite3.connect(DATABASE_URL)
        cursor = conn.cursor()
        cursor.execute("INSERT INTO files (filename, filepath, file_diar, file_summ) VALUES (?, ?, ?, ?)",
                       (filename, filepath, None, None))
        file_id = cursor.lastrowid
        conn.commit()
        conn.close()

  
        diarization_text = await diarization_audio(filepath, file_id)
        summary_text = await summarization_text(diarization_text or "", file_id)  # Передаем пустую строку, если diarization_text is None

    
        conn = sqlite3.connect(DATABASE_URL)
        cursor = conn.cursor()
        cursor.execute("SELECT file_diar, file_summ FROM files WHERE id = ?", (file_id,))
        result = cursor.fetchone()
        conn.close()

        file_diar = result[0] if result else None
        file_summ = result[1] if result else None


        return JSONResponse(
            status_code=status.HTTP_201_CREATED,
            content={
                "message": "File uploaded and processed successfully",
                "filename": filename,
                "filepath": filepath,
                "file_diar": file_diar,
                "file_summ": file_summ,
                "file_id": file_id
            }
        )

    except HTTPException as e:
        return JSONResponse(status_code=e.status_code, content={"detail": e.detail})
    except Exception as e:
        print(f"Ошибка при обработке файла: {e}")
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=f"Internal server error: {e}")





if __name__ == "__main__":
    import uvicorn

    urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
    uvicorn.run(app, host="0.0.0.0", port=8000)
