package trackingSimulator

import trackingSimulator.shippingUpdates.*
import kotlin.test.*

class ShipmentIntegrationTest {
    
    private lateinit var simulator: TrackingSimulator
    
    @BeforeTest
    fun setup() {
        simulator = TrackingSimulator()
    }
    
    @Test
    fun testCompleteShipmentLifecycle() {
        // Create shipment
        val createdUpdate = simulator.updateFactory("created,LIFECYCLE001,1000,standard") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("LIFECYCLE001")!!
        
        // Track observer calls
        var observerCallCount = 0
        val observer = object : ShipmentObserver {
            override fun ShipmentUpdated(shipment: Shipment) {
                observerCallCount++
            }
        }
        shipment.addObserver(observer)
        
        // Ship the package
        val shippedUpdate = simulator.updateFactory("shipped,LIFECYCLE001,2000,1700000000")!!
        shipment.addUpdate(shippedUpdate)
        
        assertEquals("shipped", shipment.status)
        assertEquals(1700000000L, shipment.expected_delivery_date_timestamp)
        assertEquals(1, observerCallCount)
        
        // Add location update
        val locationUpdate = simulator.updateFactory("location,LIFECYCLE001,3000,Boston Hub")!!
        shipment.addUpdate(locationUpdate)
        
        assertEquals("location", shipment.status)
        assertEquals("Boston Hub", shipment.current_location)
        assertEquals(2, observerCallCount)
        
        // Add note
        val noteUpdate = simulator.updateFactory("noteadded,LIFECYCLE001,4000,Out for delivery")!!
        shipment.addUpdate(noteUpdate)
        
        assertEquals("noteadded", shipment.status)
        assertEquals(1, shipment.notes.size)
        assertEquals("Out for delivery", shipment.notes[0])
        assertEquals(3, observerCallCount)
        
        // Deliver
        val deliveredUpdate = simulator.updateFactory("delivered,LIFECYCLE001,5000")!!
        shipment.addUpdate(deliveredUpdate)
        
        assertEquals("delivered", shipment.status)
        assertEquals(4, observerCallCount)
        assertEquals(5, shipment.update_history.size)
    }
    
    @Test
    fun testDelayedShipment() {
        val createdUpdate = simulator.updateFactory("created,DELAYED001,1000,standard") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("DELAYED001")!!
        
        // Ship with initial delivery date
        val shippedUpdate = simulator.updateFactory("shipped,DELAYED001,2000,1700000000")!!
        shipment.addUpdate(shippedUpdate)
        assertEquals(1700000000L, shipment.expected_delivery_date_timestamp)
        
        // Delay shipment with new delivery date
        val delayedUpdate = simulator.updateFactory("delayed,DELAYED001,3000,1700086400")!!
        shipment.addUpdate(delayedUpdate)
        
        assertEquals("delayed", shipment.status)
        assertEquals(1700086400L, shipment.expected_delivery_date_timestamp)
    }
    
    @Test
    fun testMultipleNotes() {
        val createdUpdate = simulator.updateFactory("created,NOTES001,1000,standard") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("NOTES001")!!
        
        val note1 = simulator.updateFactory("noteadded,NOTES001,2000,First note")!!
        val note2 = simulator.updateFactory("noteadded,NOTES001,3000,Second note")!!
        val note3 = simulator.updateFactory("noteadded,NOTES001,4000,Third note")!!
        
        shipment.addUpdate(note1)
        shipment.addUpdate(note2)
        shipment.addUpdate(note3)
        
        assertEquals(3, shipment.notes.size)
        assertEquals("First note", shipment.notes[0])
        assertEquals("Second note", shipment.notes[1])
        assertEquals("Third note", shipment.notes[2])
    }
    
    @Test
    fun testLocationUpdates() {
        val createdUpdate = simulator.updateFactory("created,LOCATION001,1000,standard") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("LOCATION001")!!
        
        val location1 = simulator.updateFactory("location,LOCATION001,2000,Origin Hub")!!
        val location2 = simulator.updateFactory("location,LOCATION001,3000,Transit Hub")!!
        val location3 = simulator.updateFactory("location,LOCATION001,4000,Destination Hub")!!
        
        shipment.addUpdate(location1)
        assertEquals("Origin Hub", shipment.current_location)
        
        shipment.addUpdate(location2)
        assertEquals("Transit Hub", shipment.current_location)
        
        shipment.addUpdate(location3)
        assertEquals("Destination Hub", shipment.current_location)
    }
    
    @Test
    fun testShipmentNotFound() {
        val foundShipment = simulator.findShipment("NONEXISTENT")
        assertNull(foundShipment)
    }
    
    @Test
    fun testCanceledShipment() {
        val createdUpdate = simulator.updateFactory("created,CANCELED001,1000,standard") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("CANCELED001")!!
        
        val canceledUpdate = simulator.updateFactory("canceled,CANCELED001,2000")!!
        shipment.addUpdate(canceledUpdate)
        
        assertEquals("canceled", shipment.status)
        assertEquals(2, shipment.update_history.size)
    }
    
    @Test
    fun testLostShipment() {
        val createdUpdate = simulator.updateFactory("created,LOST001,1000,standard") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("LOST001")!!
        
        val lostUpdate = simulator.updateFactory("lost,LOST001,2000")!!
        shipment.addUpdate(lostUpdate)
        
        assertEquals("lost", shipment.status)
        assertEquals(2, shipment.update_history.size)
    }
}
