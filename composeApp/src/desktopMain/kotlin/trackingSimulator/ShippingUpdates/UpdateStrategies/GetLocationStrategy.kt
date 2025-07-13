package trackingSimulator.ShippingUpdates.UpdateStrategies

interface GetLocationStrategy {
    abstract fun getLocation(): String?
}