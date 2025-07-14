package trackingSimulator.shippingUpdates.updateStrategies

interface GetExpectedDeliveryDateStrategy {
    abstract fun getDeliveryDate(updateSplit: List<String>): Long?
}