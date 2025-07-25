package trackingSimulator.shipmentDecorators

import trackingSimulator.ShipmentInterface
import trackingSimulator.shippingUpdates.ShippingUpdate

class OvernightDecorator(shipment: ShipmentInterface): ShipmentDecorator(shipment) {
    val createdTimeStamp = update_history.first().timestamp
    val oneDayTimeStamp = createdTimeStamp + (3 * 24 * 60 * 60 * 1000)
    val twoDayTimeStamp = oneDayTimeStamp + (3 * 24 * 60 * 60 * 1000)

    override fun addUpdate(update: ShippingUpdate) {
        super.addUpdate(update)
        if (expected_delivery_date_timestamp == null) {
            return
        } else if (expected_delivery_date_timestamp!! < oneDayTimeStamp) {
            decoratedShipment.addNote("OVERNIGHT SHIPMENT WILL ARRIVE EARLY")
        } else if (expected_delivery_date_timestamp!! > twoDayTimeStamp) {
            decoratedShipment.addNote("OVERNIGHT SHIPMENT WILL ARRIVE EARLY")
        }
    }
}