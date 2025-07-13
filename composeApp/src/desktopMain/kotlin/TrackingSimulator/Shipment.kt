package TrackingSimulator

import TrackingSimulator.ShippingUpdates.CreatedUpdate
import TrackingSimulator.ShippingUpdates.ShippingUpdate

class Shipment(
    update: CreatedUpdate
) {
    val id: String = update.getId()
    var status: String = update.getStatus()
        private set
    val notes: MutableList<String> = mutableListOf()
        private set
    val update_history: MutableList<ShippingUpdate> = mutableListOf(update)
        private set
    val expected_delivery_date_timestamp: Long? = null
        private set
    var current_location: String? = null
        private set

    fun addUpdate(update: ShippingUpdate) {
        // TODO
        // update instance vars
        // call notify observers
    }
}
