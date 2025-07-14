package trackingSimulator

import trackingSimulator.shippingUpdates.CreatedUpdate
import trackingSimulator.shippingUpdates.ShippingUpdate

class Shipment(
    update: CreatedUpdate
) {
    val id: String = update.getId()
    private val _notes: MutableList<String> = mutableListOf()
    val notes: List<String> get() = _notes
    private val _update_history: MutableList<ShippingUpdate> = mutableListOf(update)
    val update_history: List<ShippingUpdate> get() = _update_history
    var status: String = update.getStatus()
        private set
    var expected_delivery_date_timestamp: Long? = null
        private set
    var current_location: String? = null
        private set

    fun addUpdate(update: ShippingUpdate) {
        this.status = update.getStatus()
        val note: String? = update.getNote()
        val expectedDeliveryDate: Long? = update.getDeliveryDate()
        val location: String? = update.getLocation()

        // update the shipment history
        this._update_history += update

        // if there are updates to notes, delivery date, or location, update them
        if (note != null) {
            this._notes += note
        }
        if (expectedDeliveryDate != null) {
            this.expected_delivery_date_timestamp = expectedDeliveryDate
        }
        if (location != null) {
            this.current_location = location
        }
    }
}
