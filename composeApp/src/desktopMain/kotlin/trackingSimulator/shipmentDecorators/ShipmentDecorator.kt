package trackingSimulator.shipmentDecorators

import trackingSimulator.ShipmentInterface
import trackingSimulator.ShipmentObserver
import trackingSimulator.shippingUpdates.ShippingUpdate

abstract class ShipmentDecorator(protected val decoratedShipment: ShipmentInterface): ShipmentInterface {
    override fun addObserver(observer: ShipmentObserver) {
        decoratedShipment.addObserver(observer)
    }

    override fun removeObserver(observer: ShipmentObserver) {
        decoratedShipment.removeObserver(observer)
    }

    override fun notifyObservers() {
        decoratedShipment.notifyObservers()
    }

    override fun addUpdate(update: ShippingUpdate) {
        decoratedShipment.addUpdate(update)
    }

    // Delegate properties to the decorated shipment
    override val id: String get() = decoratedShipment.id
    override val notes: List<String> get() = decoratedShipment.notes
    override val update_history: List<ShippingUpdate> get() = decoratedShipment.update_history
    override val status: String get() = decoratedShipment.status
    override val expected_delivery_date_timestamp: Long? get() = decoratedShipment.expected_delivery_date_timestamp
    override val current_location: String? get() = decoratedShipment.current_location
}