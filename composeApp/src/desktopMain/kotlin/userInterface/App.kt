package userInterface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("") }
    val trackedShipments = remember { mutableStateListOf<TrackerViewHelper>() }
    var errorMessage by remember { mutableStateOf("") }
    
    // Clean up observers when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            trackedShipments.forEach { it.dispose() }
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
                    label = { Text("Shipment ID: ") },
                    isError = errorMessage.isNotEmpty(),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                Button(
                    onClick = {
                        if (text.isNotBlank()) {
                            val tracker = TrackerViewHelper(text)
                            if (tracker.isValid) {
                                trackedShipments.add(tracker)
                                text = ""
                                errorMessage = ""
                            } else {
                                errorMessage = "Shipment ID '$text' not found"
                            }
                        }
                    }
                ) {
                    Text("Track Shipment")
                }
            }
            
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Scrollable shipments section
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp)
            ) {
                trackedShipments.forEach { tracker ->
                    Card(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Tracking ID: ${tracker.shipmentId}",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "Status: ${tracker.status}",
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            
                            // Only show location if it exists
                            if (tracker.hasLocation()) {
                                Text(
                                    text = "Current Location: ${tracker.currentLocation}",
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            
                            // Only show delivery date if it exists
                            if (tracker.hasDeliveryDate()) {
                                Text(
                                    text = "Expected Delivery: ${tracker.expectedDeliveryDate}",
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            
                            // Show notes if they exist
                            if (tracker.hasNotes()) {
                                Text(
                                    text = "Notes:",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                                )
                                tracker.notes.forEach { note ->
                                    Text(
                                        text = "• $note",
                                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                                    )
                                }
                            }
                            
                            if (tracker.updateHistory.isNotEmpty()) {
                                Text(
                                    text = "Shipping Updates:",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                                )
                                tracker.updateHistory.forEach { update ->
                                    Text(
                                        text = "• $update",
                                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}