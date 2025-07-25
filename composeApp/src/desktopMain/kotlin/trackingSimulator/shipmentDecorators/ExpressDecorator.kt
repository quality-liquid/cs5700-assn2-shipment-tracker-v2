package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.ShipmentInterface
import trackingSimulator.shipmentDecorators.ShipmentDecorator
import trackingSimulator.shippingUpdates.ShippingUpdate

class ExpressDecorator(shipment: ShipmentInterface): ShipmentDecorator(shipment) {

    override fun addUpdate(update: ShippingUpdate) {
        super.addUpdate(update)
        // TODO
    }
}