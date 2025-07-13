package userInterface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

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