package com.example.catfacts.ui

import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ProgressBar() {
    Box(
        backgroundColor = Color.Black.copy(alpha = 0.4f),
        modifier = Modifier.fillMaxSize(),
        gravity = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Red)
    }
}