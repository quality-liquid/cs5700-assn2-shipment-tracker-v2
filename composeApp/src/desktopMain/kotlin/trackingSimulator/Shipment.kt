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
        // TODO
        // update instance vars
        // call notify observers
    }
}
