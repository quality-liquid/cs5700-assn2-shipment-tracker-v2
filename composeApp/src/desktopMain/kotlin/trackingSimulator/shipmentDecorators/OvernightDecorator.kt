package trackingSimulator.shipmentDecorators

import trackingSimulator.ShipmentInterface

class OvernightDecorator(shipment: ShipmentInterface): ShipmentDecorator(shipment) {
    // Add overnight-specific behavior here
}