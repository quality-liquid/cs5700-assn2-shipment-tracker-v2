package trackingSimulator.shippingUpdates

import trackingSimulator.shippingUpdates.updateStrategies.GetExpectedDeliveryDateStrategy
import trackingSimulator.shippingUpdates.updateStrategies.GetLocationStrategy
import trackingSimulator.shippingUpdates.updateStrategies.GetNoteStrategy

abstract class ShippingUpdate(
    val updateString: String,
) {
    val updateSplit: List<String> = updateString.split(",")
    val status = updateSplit[0]
    val shipmentId = updateSplit[1]
    val timestamp = updateSplit[2].toLong()
    abstract val locationStrategy: GetLocationStrategy
    abstract val deliveryDateStrategy: GetExpectedDeliveryDateStrategy
    abstract val noteStrategy: GetNoteStrategy

    fun getId(): String {
        return this.shipmentId
    }

    fun getLocation(): String? {
        return this.locationStrategy.getLocation(updateSplit)
    }

    fun getDeliveryDate(): Long? {
        return this.deliveryDateStrategy.getDeliveryDate(updateSplit)
    }

    fun getNote(): String? {
        return this.noteStrategy.getNote(updateSplit)
    }

}