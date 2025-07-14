package trackingSimulator.shippingUpdates.updateStrategies

class NewExpectedDeliveryDateStrategy: GetExpectedDeliveryDateStrategy {
    override fun getDeliveryDate(updateSplit: List<String>): Long? {
        return updateSplit[3].toLong()
    }
}