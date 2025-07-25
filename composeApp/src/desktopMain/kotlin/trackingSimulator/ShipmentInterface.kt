package trackingSimulator

import trackingSimulator.shippingUpdates.ShippingUpdate

interface ShipmentInterface : ShipmentObservable {
    val id: String
    val notes: List<String>
    val update_history: List<ShippingUpdate>
    val status: String
    val expected_delivery_date_timestamp: Long?
    val current_location: String?
    
    fun addUpdate(update: ShippingUpdate)
}