package trackingSimulator.shippingUpdates.updateStrategies

class NoExpectedDeliveryDateStrategy: GetExpectedDeliveryDateStrategy {
    override fun getDeliveryDate(updateSplit: List<String>): Long? {
        return null
    }
}