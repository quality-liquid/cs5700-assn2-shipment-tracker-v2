package userInterface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

@Composable
fun Client() {
    var text by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val client = remember { 
        HttpClient(Java) {
            // Use Java engine which is more stable for desktop applications
        }
    }
    val scope = rememberCoroutineScope()

    // Clean up HttpClient when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            client.close()
        }
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            // Input section (fixed at top)
            Row {
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        errorMessage = ""
                    },
                    label = { Text("Update String: ") },
                    isError = errorMessage.isNotEmpty(),
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                Button(
                    onClick = {
                        if (text.isNotBlank()) {
                            scope.launch {
                                isLoading = true
                                errorMessage = ""
                                try {
                                    val result = makeRequest(client, text)
                                    response = result
                                    text = ""
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                    e.printStackTrace()
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    },
                    enabled = !isLoading && text.isNotBlank()
                ) {
                    Text(if (isLoading) "Sending..." else "Add Update")
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp)
            ) {
                if (response.isNotEmpty()) {
                    Text(
                        text = "Response: $response",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

suspend fun makeRequest(client: HttpClient, update: String): String {
    val response = client.post("http://localhost:8888") {
        setBody(update)
    }
    return response.bodyAsText()
}