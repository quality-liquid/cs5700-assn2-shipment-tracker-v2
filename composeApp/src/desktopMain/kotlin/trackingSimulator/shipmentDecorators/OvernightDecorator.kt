package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment

class OvernightDecorator(shipment: Shipment): ShipmentDecorator(shipment) {
    // Add overnight-specific behavior here
}