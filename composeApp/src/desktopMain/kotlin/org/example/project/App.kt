package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import assn2_shipment_tracker_v2.composeapp.generated.resources.Res
import assn2_shipment_tracker_v2.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("") }
    val output_text = remember { mutableStateListOf<String>() }
    MaterialTheme {
        Column {
            Row {
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText  // Update the state
                    },
                    label = { Text("Shipment ID: ") }
                )
                Button(
                    {
                        output_text.add(text)
                        text = ""
                    }
                ) {
                    Text("Print text")
                }
            }
            output_text.forEach { text: String ->
                Text(text)
            }
        }
    }
}