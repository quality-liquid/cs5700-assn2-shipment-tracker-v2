package trackingSimulator.shippingUpdates.updateStrategies

class NoNoteStrategy: GetNoteStrategy {
    override fun getNote(updateSplit: List<String>): String? {
        return null
    }
}
