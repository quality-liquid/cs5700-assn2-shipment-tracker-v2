package trackingSimulator.shippingUpdates.updateStrategies

class NewNoteStrategy: GetNoteStrategy {
    override fun getNote(updateSplit: List<String>): String? {
        return updateSplit[2]
    }
}