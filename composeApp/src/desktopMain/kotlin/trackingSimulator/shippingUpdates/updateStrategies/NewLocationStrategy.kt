package trackingSimulator.shippingUpdates.updateStrategies

class NewLocationStrategy: GetLocationStrategy {
    override fun getLocation(updateSplit: List<String>): String? {
        return updateSplit[3]
    }
}