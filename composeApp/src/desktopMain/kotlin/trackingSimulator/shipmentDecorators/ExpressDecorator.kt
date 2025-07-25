package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.ShipmentInterface
import trackingSimulator.shipmentDecorators.ShipmentDecorator
import trackingSimulator.shippingUpdates.ShippingUpdate

class ExpressDecorator(shipment: ShipmentInterface): ShipmentDecorator(shipment) {
    val createdTimeStamp = update_history.first().timestamp
    val threeDaysTimeStamp = createdTimeStamp + (3 * 24 * 60 * 60 * 1000)

    override fun addUpdate(update: ShippingUpdate) {
        super.addUpdate(update)
        if (expected_delivery_date_timestamp !== null && expected_delivery_date_timestamp!! > threeDaysTimeStamp) {
            decoratedShipment.addNote("EXPRESS SHIPMENT WILL NOT ARRIVE ON TIME")
        }
    }
}