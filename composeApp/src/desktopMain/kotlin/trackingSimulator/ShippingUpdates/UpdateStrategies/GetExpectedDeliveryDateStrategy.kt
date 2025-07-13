package trackingSimulator.ShippingUpdates.UpdateStrategies

interface GetExpectedDeliveryDateStrategy {
    abstract fun getDeliveryDate(): Long?
}