package trackingSimulator.shippingUpdates

import trackingSimulator.shippingUpdates.updateStrategies.NewExpectedDeliveryDateStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoLocationStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoNoteStrategy

class ShippedUpdate(updateString: String) : ShippingUpdate(updateString) {
    override val locationStrategy = NoLocationStrategy()
    override val noteStrategy = NoNoteStrategy()
    override val deliveryDateStrategy = NewExpectedDeliveryDateStrategy()
}