package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.ShipmentInterface
import trackingSimulator.shipmentDecorators.ShipmentDecorator

class BulkDecorator(shipment: ShipmentInterface): ShipmentDecorator(shipment) {
    // Add bulk-specific behavior here
}