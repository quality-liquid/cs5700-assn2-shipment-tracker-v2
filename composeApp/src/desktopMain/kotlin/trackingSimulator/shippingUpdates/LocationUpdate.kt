package trackingSimulator.shippingUpdates

import trackingSimulator.shippingUpdates.updateStrategies.NewLocationStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoExpectedDeliveryDateStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoNoteStrategy

class LocationUpdate(updateString: String) : ShippingUpdate(updateString) {
    override val locationStrategy = NewLocationStrategy()
    override val noteStrategy = NoNoteStrategy()
    override val deliveryDateStrategy = NoExpectedDeliveryDateStrategy()
}