package com.example.recipefinder.ui.screens

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipefinder.NotesViewModel
import com.example.recipefinder.R

// Composable function representing the timer screen
@Composable
fun TimerScreen(
    onBackClicked: () -> Unit,
    notesViewModel: NotesViewModel = viewModel()
) {
    // State for managing input of minutes and seconds
    var inputMinutes by remember { mutableStateOf("1") }
    var inputSeconds by remember { mutableStateOf("0") }

    // State for managing timer-related states
    var isTimerRunning by remember { mutableStateOf(false) }
    var timeLeftInSeconds by remember { mutableStateOf(0) }
    var isTimerFinished by remember { mutableStateOf(false) }

    // LaunchedEffect for updating the timer display when the timer is running
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            object : CountDownTimer(timeLeftInSeconds * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInSeconds = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    isTimerRunning = false
                    isTimerFinished = true
                }
            }.start()
        }
    }

    // Show a Toast and play an alarm sound when the timer finishes
    if (isTimerFinished) {
        val mp: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.alarm_sound)
        mp.start()
        Toast.makeText(
            LocalContext.current,
            "Timer finished!",
            Toast.LENGTH_SHORT
        ).show()
        isTimerFinished = false
    }

    // Column composable for arranging UI elements vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Countdown Timer Display
        Box(
            modifier = Modifier
                .padding(start = 140.dp)
                .background(MaterialTheme.colorScheme.background)
                .clickable {
                    // Start or stop the timer on click
                    isTimerRunning = !isTimerRunning
                }
        ) {
            val minutes = timeLeftInSeconds / 60
            val seconds = timeLeftInSeconds % 60

            // Text composable to display the countdown timer
            Text(
                text = "$minutes:${"%02d".format(seconds)}", // Format as "minutes:seconds"
                modifier = Modifier.padding(16.dp),
                style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            )
        }

        // Timer input fields with labels
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Minutes input field
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    value = inputMinutes,
                    onValueChange = { inputMinutes = it },
                    label = { Text("Minutes") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(end = 4.dp, start = 25.dp).width(150.dp)
                )
            }

            // Seconds input field
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    value = inputSeconds,
                    onValueChange = { inputSeconds = it },
                    label = { Text("Seconds") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(start = 4.dp).width(150.dp)
                )
            }
        }

        // Start Timer button
        Button(
            onClick = {
                val totalSeconds = inputMinutes.toInt() * 60 + inputSeconds.toInt()
                if (totalSeconds > 0) {
                    isTimerRunning = true
                    timeLeftInSeconds = totalSeconds
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Start Timer")
        }

        // Back button
        Button(
            onClick = { onBackClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}