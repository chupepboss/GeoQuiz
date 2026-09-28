package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeoQuizTheme {
                MainScreen()
            }
        }
    }
}

fun checkAnswer(
    userAnswer: Boolean,
    correctAnswer: Boolean
): Boolean {
    return userAnswer == correctAnswer
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentQuestion by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    val questions = listOf(
        "Canberra is the capital of Australia.",
        "The Pacific Ocean is larger than the Atlantic Ocean.",
        "The Suez Canal connects the Red Sea and the Indian Ocean.",
        "The source of the Nile River is in Egypt.",
        "The Amazon River is the longest river in the Americas.",
        "Lake Baikal is the world's oldest and deepest freshwater lake."
    )

    val answers = listOf(
        true,
        true,
        false,
        false,
        true,
        true
    )

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(showResult) {
        if (showResult) {
            snackbarHostState.showSnackbar(
                "Результат: $correctAnswers из ${questions.size} правильных ответов"
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "GeoQuiz",
                fontSize = 30.sp
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Text(
                text = "Question ${currentQuestion + 1} of ${questions.size}",
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = questions[currentQuestion],
                modifier = Modifier.fillMaxWidth(),
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            if (!answered) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Button(
                        onClick = {
                            if (checkAnswer(true, answers[currentQuestion])) {
                                correctAnswers++
                            }

                            answered = true

                            if (currentQuestion == questions.size - 1) {
                                showResult = true
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("True")
                    }

                    Button(
                        onClick = {
                            if (checkAnswer(false, answers[currentQuestion])) {
                                correctAnswers++
                            }

                            answered = true

                            if (currentQuestion == questions.size - 1) {
                                showResult = true
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("False")
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            if (currentQuestion < questions.size - 1) {
                Button(
                    onClick = {
                        currentQuestion++
                        answered = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    GeoQuizTheme {
        MainScreen()
    }
}