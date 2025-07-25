package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.shippingUpdates.CreatedUpdate
import trackingSimulator.shippingUpdates.ShippingUpdate

abstract class ShipmentDecorator(update: CreatedUpdate): Shipment(update) {
    override fun addUpdate(update: ShippingUpdate) {}
}