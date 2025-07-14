package trackingSimulator

interface ShipmentObservable {
    fun addObserver(observer: ShipmentObserver)
    fun removeObserver(observer: ShipmentObserver)
    fun notifyObservers()
}