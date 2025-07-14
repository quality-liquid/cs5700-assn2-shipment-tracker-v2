package trackingSimulator

import trackingSimulator.shippingUpdates.*
import kotlin.test.*

class ShipmentTest {
    
    private lateinit var shipment: Shipment
    private lateinit var mockObserver: MockShipmentObserver
    
    @BeforeTest
    fun setup() {
        val createdUpdate = CreatedUpdate("created,TEST123,1000")
        shipment = Shipment(createdUpdate)
        mockObserver = MockShipmentObserver()
    }
    
    @Test
    fun testShipmentCreation() {
        assertEquals("TEST123", shipment.id)
        assertEquals("created", shipment.status)
        assertNull(shipment.current_location)
        assertNull(shipment.expected_delivery_date_timestamp)
        assertTrue(shipment.notes.isEmpty())
        assertEquals(1, shipment.update_history.size)
    }
    
    @Test
    fun testObserverPattern() {
        shipment.addObserver(mockObserver)
        assertEquals(0, mockObserver.updateCount)
        
        val shippedUpdate = ShippedUpdate("shipped,TEST123,2000,1700000000")
        shipment.addUpdate(shippedUpdate)
        
        assertEquals(1, mockObserver.updateCount)
        assertEquals(shipment, mockObserver.lastShipment)
    }
    
    @Test
    fun testRemoveObserver() {
        shipment.addObserver(mockObserver)
        shipment.removeObserver(mockObserver)
        
        val shippedUpdate = ShippedUpdate("shipped,TEST123,2000,1700000000")
        shipment.addUpdate(shippedUpdate)
        
        assertEquals(0, mockObserver.updateCount)
    }
    
    @Test
    fun testStatusUpdate() {
        val shippedUpdate = ShippedUpdate("shipped,TEST123,2000,1700000000")
        shipment.addUpdate(shippedUpdate)
        
        assertEquals("shipped", shipment.status)
        assertEquals(2, shipment.update_history.size)
    }
    
    @Test
    fun testLocationUpdate() {
        val locationUpdate = LocationUpdate("location,TEST123,2000,New York")
        shipment.addUpdate(locationUpdate)
        
        assertEquals("New York", shipment.current_location)
        assertEquals("location", shipment.status)
    }
    
    @Test
    fun testDeliveryDateUpdate() {
        val shippedUpdate = ShippedUpdate("shipped,TEST123,2000,1700000000")
        shipment.addUpdate(shippedUpdate)
        
        assertEquals(1700000000L, shipment.expected_delivery_date_timestamp)
    }
    
    @Test
    fun testNoteUpdate() {
        val noteUpdate = NoteAddedUpdate("noteadded,TEST123,2000,Package delayed due to weather")
        shipment.addUpdate(noteUpdate)
        
        assertEquals(1, shipment.notes.size)
        assertEquals("Package delayed due to weather", shipment.notes[0])
    }
    
    @Test
    fun testMultipleNotes() {
        val noteUpdate1 = NoteAddedUpdate("noteadded,TEST123,2000,First note")
        val noteUpdate2 = NoteAddedUpdate("noteadded,TEST123,3000,Second note")
        
        shipment.addUpdate(noteUpdate1)
        shipment.addUpdate(noteUpdate2)
        
        assertEquals(2, shipment.notes.size)
        assertEquals("First note", shipment.notes[0])
        assertEquals("Second note", shipment.notes[1])
    }
    
    @Test
    fun testUpdateHistory() {
        val shippedUpdate = ShippedUpdate("shipped,TEST123,2000,1700000000")
        val locationUpdate = LocationUpdate("location,TEST123,3000,Boston")
        
        shipment.addUpdate(shippedUpdate)
        shipment.addUpdate(locationUpdate)
        
        assertEquals(3, shipment.update_history.size)
        assertEquals("created", shipment.update_history[0].status)
        assertEquals("shipped", shipment.update_history[1].status)
        assertEquals("location", shipment.update_history[2].status)
    }
    
    private class MockShipmentObserver : ShipmentObserver {
        var updateCount = 0
        var lastShipment: Shipment? = null
        
        override fun ShipmentUpdated(shipment: Shipment) {
            updateCount++
            lastShipment = shipment
        }
    }
}
