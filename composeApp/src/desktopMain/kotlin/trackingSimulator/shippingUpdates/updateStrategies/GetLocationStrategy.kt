package trackingSimulator.shippingUpdates.updateStrategies

interface GetLocationStrategy {
    abstract fun getLocation(updateSplit: List<String>): String?
}