package userInterface

import androidx.compose.runtime.*
import trackingSimulator.Shipment
import trackingSimulator.ShipmentInterface
import trackingSimulator.ShipmentObserver
import trackingSimulator.simulator
import java.text.SimpleDateFormat
import java.util.*

class TrackerViewHelper(
    shipmentId: String
) : ShipmentObserver {
    
    private val shipment: ShipmentInterface? = simulator.findShipment(shipmentId)
    
    val shipmentId: String = shipmentId
    val isValid: Boolean = shipment != null
    
    // Use mutable state for UI updates
    var status by mutableStateOf(shipment?.status ?: "Not Found")
        private set
    var currentLocation by mutableStateOf(shipment?.current_location ?: "Unknown")
        private set
    var expectedDeliveryDate by mutableStateOf(formatDeliveryDate(shipment?.expected_delivery_date_timestamp))
        private set
    var notes by mutableStateOf(shipment?.notes?.toList() ?: emptyList<String>())
        private set
    var updateHistory by mutableStateOf(formatUpdateHistory())
        private set
    
    init {
        shipment?.addObserver(this)
        if (isValid) {
            prepareInitialData()
        }
    }
    
    override fun ShipmentUpdated(shipment: Shipment) {
        // Update state properties - this will trigger recomposition
        status = shipment.status
        currentLocation = shipment.current_location ?: "Unknown"
        expectedDeliveryDate = formatDeliveryDate(shipment.expected_delivery_date_timestamp)
        notes = shipment.notes.toList()
        updateHistory = formatUpdateHistory()
    }
    
    private fun prepareInitialData() {
        shipment?.let {
            status = it.status
            currentLocation = it.current_location ?: "Unknown"
            expectedDeliveryDate = formatDeliveryDate(it.expected_delivery_date_timestamp)
            notes = it.notes.toList()
            updateHistory = formatUpdateHistory()
        }
    }
    
    private fun formatDeliveryDate(timestamp: Long?): String {
        return if (timestamp != null) {
            val date = Date(timestamp)
            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        } else {
            "Not available"
        }
    }
    
    private fun formatUpdateHistory(): List<String> {
        return shipment?.update_history?.map { update ->
            "${update.status} - ${formatTimestamp(update.timestamp)}"
        } ?: emptyList()
    }
    
    private fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        return SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(date)
    }
    
    fun getStatusColor(): String {
        return when (status.lowercase()) {
            "created" -> "#FFA500"    // Orange
            "shipped" -> "#4CAF50"    // Green
            "in transit" -> "#2196F3" // Blue
            "delivered" -> "#8BC34A"  // Light Green
            "delayed" -> "#FF5722"    // Red Orange
            else -> "#757575"         // Gray
        }
    }
    
    fun hasNotes(): Boolean = notes.isNotEmpty()
    
    fun hasLocation(): Boolean = currentLocation != "Unknown"
    
    fun hasDeliveryDate(): Boolean = expectedDeliveryDate != "Not available"
    
    fun dispose() {
        shipment?.removeObserver(this)
    }
}