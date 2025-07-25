package trackingSimulator.shipmentDecorators

import trackingSimulator.shippingUpdates.CreatedUpdate

class BulkDecorator(update: CreatedUpdate): ShipmentDecorator(update) {
}