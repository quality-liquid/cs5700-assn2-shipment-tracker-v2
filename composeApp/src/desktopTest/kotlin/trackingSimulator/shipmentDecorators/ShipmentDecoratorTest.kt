package trackingSimulator.shipmentDecorators

import trackingSimulator.*
import trackingSimulator.shippingUpdates.*
import kotlin.test.*

class ShipmentDecoratorTest {
    
    @Test
    fun testExpressDecoratorOnTime() {
        val createdUpdate = CreatedUpdate("created,EXPRESS001,1000,express")
        val shipment = Shipment(createdUpdate)
        val expressShipment = ExpressDecorator(shipment)
        
        // Ship with delivery in 2 days (within 3 day limit)
        val twoDaysLater = 1000 + (2 * 24 * 60 * 60 * 1000)
        val shippedUpdate = ShippedUpdate("shipped,EXPRESS001,2000,$twoDaysLater")
        expressShipment.addUpdate(shippedUpdate)
        
        assertTrue(expressShipment.notes.isEmpty())
    }
    
    @Test
    fun testExpressDecoratorLate() {
        val createdUpdate = CreatedUpdate("created,EXPRESS002,1000,express")
        val shipment = Shipment(createdUpdate)
        val expressShipment = ExpressDecorator(shipment)
        
        // Ship with delivery in 4 days (beyond 3 day limit)
        val fourDaysLater = 1000 + (4 * 24 * 60 * 60 * 1000)
        val shippedUpdate = ShippedUpdate("shipped,EXPRESS002,2000,$fourDaysLater")
        expressShipment.addUpdate(shippedUpdate)
        
        assertEquals(1, expressShipment.notes.size)
        assertEquals("EXPRESS SHIPMENT WILL NOT ARRIVE ON TIME", expressShipment.notes[0])
    }
    
    @Test
    fun testBulkDecoratorNormal() {
        val createdUpdate = CreatedUpdate("created,BULK001,1000,bulk")
        val shipment = Shipment(createdUpdate)
        val bulkShipment = BulkDecorator(shipment)
        
        // Ship with delivery in 4 days (normal for bulk)
        val fourDaysLater = 1000 + (4 * 24 * 60 * 60 * 1000)
        val shippedUpdate = ShippedUpdate("shipped,BULK001,2000,$fourDaysLater")
        bulkShipment.addUpdate(shippedUpdate)
        
        assertTrue(bulkShipment.notes.isEmpty())
    }
    
    @Test
    fun testBulkDecoratorTooSoon() {
        val createdUpdate = CreatedUpdate("created,BULK002,1000,bulk")
        val shipment = Shipment(createdUpdate)
        val bulkShipment = BulkDecorator(shipment)
        
        // Ship with delivery in 2 days (too soon for bulk)
        val twoDaysLater = 1000 + (2 * 24 * 60 * 60 * 1000)
        val shippedUpdate = ShippedUpdate("shipped,BULK002,2000,$twoDaysLater")
        bulkShipment.addUpdate(shippedUpdate)
        
        assertEquals(1, bulkShipment.notes.size)
        assertEquals("BULK SHIPMENT WILL ARRIVE TOO SOON", bulkShipment.notes[0])
    }
    
    @Test
    fun testOvernightDecoratorEarly() {
        val createdUpdate = CreatedUpdate("created,OVERNIGHT001,1000,overnight")
        val shipment = Shipment(createdUpdate)
        val overnightShipment = OvernightDecorator(shipment)
        
        // Ship with delivery in 12 hours (very early)
        val halfDayLater = 1000 + (12 * 60 * 60 * 1000)
        val shippedUpdate = ShippedUpdate("shipped,OVERNIGHT001,2000,$halfDayLater")
        overnightShipment.addUpdate(shippedUpdate)
        
        assertEquals(1, overnightShipment.notes.size)
        assertEquals("OVERNIGHT SHIPMENT WILL ARRIVE EARLY", overnightShipment.notes[0])
    }
    
    @Test
    fun testOvernightDecoratorLate() {
        val createdUpdate = CreatedUpdate("created,OVERNIGHT002,1000,overnight")
        val shipment = Shipment(createdUpdate)
        val overnightShipment = OvernightDecorator(shipment)
        
        // Ship with delivery in 7 days (very late for overnight)
        val sevenDaysLater = 1000 + (7 * 24 * 60 * 60 * 1000)
        val shippedUpdate = ShippedUpdate("shipped,OVERNIGHT002,2000,$sevenDaysLater")
        overnightShipment.addUpdate(shippedUpdate)
        
        assertEquals(1, overnightShipment.notes.size)
        assertEquals("OVERNIGHT SHIPMENT WILL ARRIVE EARLY", overnightShipment.notes[0])
    }
    
    @Test
    fun testDecoratorDelegation() {
        val createdUpdate = CreatedUpdate("created,DELEGATE001,1000,express")
        val shipment = Shipment(createdUpdate)
        val decorated = ExpressDecorator(shipment)
        
        assertEquals("DELEGATE001", decorated.id)
        assertEquals("created", decorated.status)
        assertEquals(1, decorated.update_history.size)
        assertNull(decorated.current_location)
        assertNull(decorated.expected_delivery_date_timestamp)
        assertTrue(decorated.notes.isEmpty())
    }
    
    @Test
    fun testDecoratorObserverPattern() {
        val createdUpdate = CreatedUpdate("created,OBSERVER001,1000,express")
        val shipment = Shipment(createdUpdate)
        val decorated = ExpressDecorator(shipment)
        
        var observerCalled = false
        val observer = object : ShipmentObserver {
            override fun ShipmentUpdated(shipment: Shipment) {
                observerCalled = true
            }
        }
        
        decorated.addObserver(observer)
        val locationUpdate = LocationUpdate("location,OBSERVER001,2000,Boston")
        decorated.addUpdate(locationUpdate)
        
        assertTrue(observerCalled)
        
        observerCalled = false
        decorated.removeObserver(observer)
        val noteUpdate = NoteAddedUpdate("noteadded,OBSERVER001,3000,Test note")
        decorated.addUpdate(noteUpdate)
        
        assertFalse(observerCalled)
    }
    
    @Test
    fun testDecoratorWithNoDeliveryDate() {
        val createdUpdate = CreatedUpdate("created,NODATE001,1000,express")
        val shipment = Shipment(createdUpdate)
        val decorated = ExpressDecorator(shipment)
        
        val locationUpdate = LocationUpdate("location,NODATE001,2000,Boston")
        decorated.addUpdate(locationUpdate)
        
        // Should not add any notes since there's no delivery date
        assertTrue(decorated.notes.isEmpty())
    }
}
