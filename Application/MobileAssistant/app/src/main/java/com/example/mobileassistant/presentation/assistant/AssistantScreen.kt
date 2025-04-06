package com.example.mobileassistant.presentation.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.mobileassistant.presentation.compnents.CircleIconButton
import com.example.mobileassistant.ui.theme.GradBackgound
import com.example.mobileassistant.ui.theme.GradPrimary
import com.example.mobileassistant.ui.theme.GrayBackground
import com.example.mobileassistant.ui.theme.PurpleGrey80
import com.example.mobileassistant.ui.theme.WW
import kotlinx.coroutines.delay

@Composable
fun VoiceRecorderScreen(
    viewModel: AssistantViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val iconSize = 18.dp
    var time by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(state.lifeCycleStep == LifeCycleStep.Recording) {
        while (state.lifeCycleStep == LifeCycleStep.Recording) {
            time += 1
            delay(1000)
        }

    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(GradBackgound)

        ) {


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .shadow(40.dp, spotColor = PurpleGrey80)
                    .background(Color.White, RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 60.dp)
                    .clip(RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (state.lifeCycleStep is LifeCycleStep.Test) {
                        Button(onClick = { viewModel.onEvent(AssistantEvent.onTestEvent) }) {
                            Text(text = "send file")
                        }
                    }


                    if (state.lifeCycleStep is LifeCycleStep.Default) {
                        CircleIconButton(
                            onClick = { viewModel.onEvent(AssistantEvent.StartRecording) },
                            icon = Icons.Default.PlayArrow,
                            iconSize = iconSize,
                            backgroundColor = GradPrimary,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                    if (state.lifeCycleStep is LifeCycleStep.Recording) {
                        CircleIconButton(
                            onClick = { viewModel.onEvent(AssistantEvent.StopRecording) },
                            icon = Icons.Sharp.Check,
                            iconSize = iconSize,
                            backgroundColor = GradPrimary,
                            modifier = Modifier
                                .size(80.dp)
                        )
                    }
                    if (state.lifeCycleStep is LifeCycleStep.HaveFile) {

                        CircleIconButton(
                            onClick = { viewModel.onEvent(AssistantEvent.DeleteFile) },
                            icon = Icons.Default.Delete,
                            iconSize = iconSize,
                            backgroundColor = GradPrimary,
                            modifier = Modifier
                                .size(80.dp)
                        )

                        if(state.isPlaying) {
                            CircleIconButton(
                                onClick = { viewModel.onEvent(AssistantEvent.StopPlaying) },
                                icon = Icons.Default.Done,
                                iconSize = iconSize,
                                backgroundColor = GradPrimary,
                                modifier = Modifier
                                    .size(80.dp)
                            )
                        } else {
                            CircleIconButton(
                                onClick = { viewModel.onEvent(AssistantEvent.StartPlaying) },
                                icon = Icons.Default.PlayArrow,
                                iconSize = iconSize,
                                backgroundColor = GradPrimary,
                                modifier = Modifier
                                    .size(80.dp)
                            )
                        }

                        CircleIconButton(
                            onClick = { viewModel.onEvent(AssistantEvent.SendFile) },
                            icon = Icons.Default.Send,
                            iconSize = 18.dp,
                            backgroundColor = GradPrimary,
                            modifier = Modifier
                                .size(80.dp)
                        )
                    }



                }

                Spacer(modifier = Modifier.height(40.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable {
                            viewModel.onEvent(AssistantEvent.ShowText)
                        }
                ) {

                    if (state.showText){
                        LazyColumn(
                            modifier = Modifier.fillMaxHeight(0.8f)
                        ) {
                            item(state.text){
                                Text(
                                    text = state.text,
                                    color = Color.Gray,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .background(Color.White)
                                        )
                            }

                        }
                    } else {
                        Text(
                            text = state.text,
                            color = Color.Gray,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .background(Color.White)
                                .zIndex(1f)


                        )
                    }

                }





            }

        }


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(bottom = 200.dp)

                .size(300.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(20.dp))



        ) {
            if (state.showText){

            } else {
                Text(
                    text = numberToTime(time),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }
}

private fun numberToTime(seconds: Int): String {
    val min = seconds / 60
    val sec = seconds % 60

    val str_sec = if (sec < 10) {
        "0${sec}"
    } else {
        sec
    }


    return "${min}:${str_sec}"
}

@Preview(showBackground = true)
@Composable
fun VoiceRecorderScreenPreview() {
    val vm = AssistantViewModel(null, null)
    VoiceRecorderScreen(vm)
}