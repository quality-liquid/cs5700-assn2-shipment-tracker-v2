package trackingSimulator.shippingUpdates

import trackingSimulator.shippingUpdates.updateStrategies.NoExpectedDeliveryDateStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoLocationStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoNoteStrategy

class LostUpdate(updateString: String) : ShippingUpdate(updateString) {
    override val locationStrategy = NoLocationStrategy()
    override val noteStrategy = NoNoteStrategy()
    override val deliveryDateStrategy = NoExpectedDeliveryDateStrategy()
}