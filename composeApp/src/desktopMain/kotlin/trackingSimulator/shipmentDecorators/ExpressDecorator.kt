package trackingSimulator.shipmentDecorators

import trackingSimulator.shippingUpdates.CreatedUpdate

class ExpressDecorator(update: CreatedUpdate): ShipmentDecorator(update) {
}