package trackingSimulator.ShippingUpdates

import trackingSimulator.ShippingUpdates.UpdateStrategies.GetExpectedDeliveryDateStrategy
import trackingSimulator.ShippingUpdates.UpdateStrategies.GetLocationStrategy
import trackingSimulator.ShippingUpdates.UpdateStrategies.GetNoteStrategy

abstract class ShippingUpdate(
    val updateString: String,
) {
    val updateSplit: List<String> = updateString.split(",")
    val status = updateSplit[0]
    val shipmentId = updateSplit[1]
    abstract val locationStrategy: GetLocationStrategy
    abstract val deliveryDateStrategy: GetExpectedDeliveryDateStrategy
    abstract val noteStrategy: GetNoteStrategy

    fun getStatus(): String {
        return this.status
    }

    fun getId(): String {
        return this.shipmentId
    }

    fun getLocation(): String? {
        return this.locationStrategy.getLocation()
    }

    fun getDeliveryDate(): Long? {
        return this.deliveryDateStrategy.getDeliveryDate()
    }

    fun getNote(): String? {
        return this.noteStrategy.getNote()
    }

}