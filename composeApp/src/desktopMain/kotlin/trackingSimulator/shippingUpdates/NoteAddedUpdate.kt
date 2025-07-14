package trackingSimulator.shippingUpdates

import trackingSimulator.shippingUpdates.updateStrategies.NewNoteStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoExpectedDeliveryDateStrategy
import trackingSimulator.shippingUpdates.updateStrategies.NoLocationStrategy

class NoteAddedUpdate(updateString: String) : ShippingUpdate(updateString) {
    override val locationStrategy = NoLocationStrategy()
    override val noteStrategy = NewNoteStrategy()
    override val deliveryDateStrategy = NoExpectedDeliveryDateStrategy()
}