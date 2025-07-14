package trackingSimulator.shippingUpdates.updateStrategies

interface GetNoteStrategy {
    abstract fun getNote(updateSplit: List<String>): String?
}