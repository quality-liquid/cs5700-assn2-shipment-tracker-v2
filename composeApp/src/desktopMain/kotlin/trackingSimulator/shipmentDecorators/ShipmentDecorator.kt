package trackingSimulator.shipmentDecorators

import trackingSimulator.Shipment
import trackingSimulator.ShipmentObservable
import trackingSimulator.ShipmentObserver
import trackingSimulator.shippingUpdates.ShippingUpdate

abstract class ShipmentDecorator(protected val decoratedShipment: Shipment): ShipmentObservable {
    override fun addObserver(observer: ShipmentObserver) {
        decoratedShipment.addObserver(observer)
    }

    override fun removeObserver(observer: ShipmentObserver) {
        decoratedShipment.removeObserver(observer)
    }

    override fun notifyObservers() {
        decoratedShipment.notifyObservers()
    }

    open fun addUpdate(update: ShippingUpdate) {
        decoratedShipment.addUpdate(update)
    }

    // Delegate properties to the decorated shipment
    val id: String get() = decoratedShipment.id
    val notes: List<String> get() = decoratedShipment.notes
    val update_history: List<ShippingUpdate> get() = decoratedShipment.update_history
    val status: String get() = decoratedShipment.status
    val expected_delivery_date_timestamp: Long? get() = decoratedShipment.expected_delivery_date_timestamp
    val current_location: String? get() = decoratedShipment.current_location
}