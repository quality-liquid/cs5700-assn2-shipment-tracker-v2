package trackingSimulator.shippingUpdates.updateStrategies

class NoLocationStrategy: GetLocationStrategy {
    override fun getLocation(updateSplit: List<String>): String? {
        return null
    }
}