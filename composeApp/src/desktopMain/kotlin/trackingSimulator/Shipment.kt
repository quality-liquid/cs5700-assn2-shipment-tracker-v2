package trackingSimulator

import trackingSimulator.shippingUpdates.CreatedUpdate
import trackingSimulator.shippingUpdates.ShippingUpdate

class Shipment(
    update: CreatedUpdate
): ShipmentInterface {
    override val id: String = update.getId()
    private val _notes: MutableList<String> = mutableListOf()
    override val notes: List<String> get() = _notes
    private val _update_history: MutableList<ShippingUpdate> = mutableListOf(update)
    override val update_history: List<ShippingUpdate> get() = _update_history
    override var status: String = update.status
        private set
    override var expected_delivery_date_timestamp: Long? = null
        private set
    override var current_location: String? = null
        private set

    // observer stuff
    private val observers: MutableList<ShipmentObserver> = mutableListOf()

    override fun addObserver(observer: ShipmentObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: ShipmentObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        for (observer in observers) {
            observer.ShipmentUpdated(this)
        }
    }

    override fun addNote(note: String) {
        _notes += note
    }

    override fun addUpdate(update: ShippingUpdate) {
        this.status = update.status
        val note: String? = update.getNote()
        val expectedDeliveryDate: Long? = update.getDeliveryDate()
        val location: String? = update.getLocation()

        // update the shipment history
        this._update_history += update

        // if there are updates to notes, delivery date, or location, update them
        if (note != null) {
            this._notes += note
        }
        if (expectedDeliveryDate != null) {
            this.expected_delivery_date_timestamp = expectedDeliveryDate
        }
        if (location != null) {
            this.current_location = location
        }
        notifyObservers()
    }
}
