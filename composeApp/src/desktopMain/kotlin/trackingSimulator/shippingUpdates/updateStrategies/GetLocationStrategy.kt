package trackingSimulator.shippingUpdates.updateStrategies

interface GetLocationStrategy {
    abstract fun getLocation(): String?
}