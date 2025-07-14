package trackingSimulator

interface ShipmentObserver {
    fun ShipmentUpdated(shipment: Shipment)
}