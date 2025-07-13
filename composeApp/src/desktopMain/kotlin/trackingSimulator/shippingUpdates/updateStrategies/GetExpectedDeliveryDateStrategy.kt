package trackingSimulator.shippingUpdates.updateStrategies

interface GetExpectedDeliveryDateStrategy {
    abstract fun getDeliveryDate(): Long?
}