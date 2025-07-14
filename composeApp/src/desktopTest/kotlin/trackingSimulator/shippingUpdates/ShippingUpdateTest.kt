package trackingSimulator.shippingUpdates

import kotlin.test.*

class ShippingUpdateTest {
    
    @Test
    fun testCreatedUpdate() {
        val update = CreatedUpdate("created,TEST123,1000")
        assertEquals("created", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(1000L, update.timestamp)
        assertNull(update.getLocation())
        assertNull(update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testShippedUpdate() {
        val update = ShippedUpdate("shipped,TEST123,2000,1700000000")
        assertEquals("shipped", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(2000L, update.timestamp)
        assertNull(update.getLocation())
        assertEquals(1700000000L, update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testLocationUpdate() {
        val update = LocationUpdate("location,TEST123,3000,New York")
        assertEquals("location", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(3000L, update.timestamp)
        assertEquals("New York", update.getLocation())
        assertNull(update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testNoteAddedUpdate() {
        val update = NoteAddedUpdate("noteadded,TEST123,4000,Package is fragile")
        assertEquals("noteadded", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(4000L, update.timestamp)
        assertNull(update.getLocation())
        assertNull(update.getDeliveryDate())
        assertEquals("Package is fragile", update.getNote())
    }
    
    @Test
    fun testDelayedUpdate() {
        val update = DelayedUpdate("delayed,TEST123,5000,1700086400")
        assertEquals("delayed", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(5000L, update.timestamp)
        assertNull(update.getLocation())
        assertEquals(1700086400L, update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testDeliveredUpdate() {
        val update = DeliveredUpdate("delivered,TEST123,6000")
        assertEquals("delivered", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(6000L, update.timestamp)
        assertNull(update.getLocation())
        assertNull(update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testCanceledUpdate() {
        val update = CanceledUpdate("canceled,TEST123,7000")
        assertEquals("canceled", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(7000L, update.timestamp)
        assertNull(update.getLocation())
        assertNull(update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testLostUpdate() {
        val update = LostUpdate("lost,TEST123,8000")
        assertEquals("lost", update.status)
        assertEquals("TEST123", update.shipmentId)
        assertEquals(8000L, update.timestamp)
        assertNull(update.getLocation())
        assertNull(update.getDeliveryDate())
        assertNull(update.getNote())
    }
    
    @Test
    fun testUpdateParsing() {
        val complexUpdate = LocationUpdate("location,SHIP456,1234567890,Boston MA")
        assertEquals("location", complexUpdate.status)
        assertEquals("SHIP456", complexUpdate.shipmentId)
        assertEquals(1234567890L, complexUpdate.timestamp)
        assertEquals("Boston MA", complexUpdate.getLocation())
    }
    
    @Test
    fun testGetId() {
        val update = CreatedUpdate("created,TESTID,1000")
        assertEquals("TESTID", update.getId())
    }
}
