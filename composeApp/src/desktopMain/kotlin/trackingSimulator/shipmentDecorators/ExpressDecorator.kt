package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.shipmentDecorators.ShipmentDecorator

class ExpressDecorator(shipment: Shipment): ShipmentDecorator(shipment) {
    // Add express-specific behavior here
}