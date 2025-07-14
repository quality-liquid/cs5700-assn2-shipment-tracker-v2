package trackingSimulator.shippingUpdates.updateStrategies

import kotlin.test.*

class UpdateStrategiesTest {
    
    @Test
    fun testNewLocationStrategy() {
        val strategy = NewLocationStrategy()
        val updateSplit = listOf("location", "TEST123", "1000", "Boston")
        assertEquals("Boston", strategy.getLocation(updateSplit))
    }
    
    @Test
    fun testNoLocationStrategy() {
        val strategy = NoLocationStrategy()
        val updateSplit = listOf("created", "TEST123", "1000")
        assertNull(strategy.getLocation(updateSplit))
    }
    
    @Test
    fun testNewNoteStrategy() {
        val strategy = NewNoteStrategy()
        val updateSplit = listOf("noteadded", "TEST123", "1000", "This is a test note")
        assertEquals("This is a test note", strategy.getNote(updateSplit))
    }
    
    @Test
    fun testNoNoteStrategy() {
        val strategy = NoNoteStrategy()
        val updateSplit = listOf("created", "TEST123", "1000")
        assertNull(strategy.getNote(updateSplit))
    }
    
    @Test
    fun testNewExpectedDeliveryDateStrategy() {
        val strategy = NewExpectedDeliveryDateStrategy()
        val updateSplit = listOf("shipped", "TEST123", "1000", "1700000000")
        assertEquals(1700000000L, strategy.getDeliveryDate(updateSplit))
    }
    
    @Test
    fun testNoExpectedDeliveryDateStrategy() {
        val strategy = NoExpectedDeliveryDateStrategy()
        val updateSplit = listOf("created", "TEST123", "1000")
        assertNull(strategy.getDeliveryDate(updateSplit))
    }
    
    @Test
    fun testLocationStrategyWithCommas() {
        val strategy = NewLocationStrategy()
        val updateSplit = listOf("location", "TEST123", "1000", "Boston, MA")
        assertEquals("Boston, MA", strategy.getLocation(updateSplit))
    }
    
    @Test
    fun testNoteStrategyWithCommas() {
        val strategy = NewNoteStrategy()
        val updateSplit = listOf("noteadded", "TEST123", "1000", "Package delayed, weather issues")
        assertEquals("Package delayed, weather issues", strategy.getNote(updateSplit))
    }
}
