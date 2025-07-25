package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.shipmentDecorators.ShipmentDecorator

class BulkDecorator(shipment: Shipment): ShipmentDecorator(shipment) {
    // Add bulk-specific behavior here
}