package trackingSimulator.shippingUpdates

import trackingSimulator.shippingUpdates.updateStrategies.NoExpectedDeliveryDateStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoLocationStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoNoteStrategy

class CreatedUpdate(updateString: String) : ShippingUpdate(updateString) {
    override val locationStrategy = NoLocationStrategy()
    override val noteStrategy = NoNoteStrategy()
    override val deliveryDateStrategy = NoExpectedDeliveryDateStrategy()

    fun getShipmentType(): String {
        if (updateSplit.size >= 3) {
            return updateSplit[3]
        } else return "standard"
    }
}