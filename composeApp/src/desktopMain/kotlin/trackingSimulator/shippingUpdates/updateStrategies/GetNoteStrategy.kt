package trackingSimulator.shippingUpdates.updateStrategies

interface GetNoteStrategy {
    abstract fun getNote(): String?
}