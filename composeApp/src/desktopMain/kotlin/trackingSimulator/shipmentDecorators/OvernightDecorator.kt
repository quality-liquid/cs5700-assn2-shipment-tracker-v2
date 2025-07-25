package trackingSimulator.shipmentDecorators

import trackingSimulator.shippingUpdates.CreatedUpdate

class OvernightDecorator(update: CreatedUpdate): ShipmentDecorator(update) {
}