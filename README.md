# Кейс «Мобильный ассистент»


# Разработка мобильного предложения под ОС Android  распознавания речи для Ростелеком Бизнес

## Название команды: Juns
### Участники:
    Красилов Николай
    Глинский Никита
    Сивцов Андрей
    Шевченко Артём
    Кубанцев Даниил
    Ткачёв Дмитрий

## Цель проекта: 

AI-ассистент превращает записи 
деловых встреч в готовые отчёты. Он расшифровывает аудио и видео, 
определяет спикеров и 
подписывает реплики. 

Система сама выделяет главное  — решения, сроки и спорные 
моменты. Особый упор на 
обязательства: кто, что и когда 
должен сделать. 

Всё доступно в 
удобном интерфейсе: полная 
расшифровка, краткое содержание и список задач.

## Технологический стек:

### Backend

Язык: Python

Фреймворк: FastAPI 

Сервер: uvicorn

База данных: SQLite 

HTTP-клиенты: httpx, requests

Фоновые задачи: BackgroundTasks (FastAPI)

### AI

ASR: Whisper 

LLM: GigaChat 

NLP: встроенные возможности LLM

### Mobile

Сетевые запросы: Retrofit2 + OkHttp3 

Асинхронность: Kotlin Coroutines 

Архитектура: MVI

## Запуск локального сервера
```bash
 cd Backend; python server.py 
```

## Дизайн

Дизайн проекта доступен в Figma: [Ссылка на дизайн Figma](https://www.figma.com/design/cDjZZ6uyMkCqkyzAdi7PzP/%D0%98%D0%BD%D1%82%D0%B5%D1%80%D1%84%D0%B5%D0%B9%D1%81)

## Видеозапись работы приложения

Видеозапись доступна на Google Drive: [Ссылка на Google Drive](https://drive.google.com/drive/folders/1WNNalByn4EINlf5lShU1oie9cVFWqGK5?usp=sharing)

