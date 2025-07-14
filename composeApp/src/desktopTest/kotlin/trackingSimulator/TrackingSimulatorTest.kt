package trackingSimulator

import trackingSimulator.shippingUpdates.*
import kotlin.test.*

class TrackingSimulatorTest {
    
    private lateinit var simulator: TrackingSimulator
    
    @BeforeTest
    fun setup() {
        simulator = TrackingSimulator()
    }
    
    @Test
    fun testUpdateFactory() {
        val createdUpdate = simulator.updateFactory("created,TEST123,1000")
        assertTrue(createdUpdate is CreatedUpdate)
        assertEquals("created", createdUpdate?.status)
        assertEquals("TEST123", createdUpdate?.shipmentId)
        assertEquals(1000L, createdUpdate?.timestamp)
    }
    
    @Test
    fun testUpdateFactoryAllTypes() {
        assertTrue(simulator.updateFactory("canceled,TEST123,1000") is CanceledUpdate)
        assertTrue(simulator.updateFactory("created,TEST123,1000") is CreatedUpdate)
        assertTrue(simulator.updateFactory("delayed,TEST123,1000,1700000000") is DelayedUpdate)
        assertTrue(simulator.updateFactory("delivered,TEST123,1000") is DeliveredUpdate)
        assertTrue(simulator.updateFactory("location,TEST123,1000,Boston") is LocationUpdate)
        assertTrue(simulator.updateFactory("lost,TEST123,1000") is LostUpdate)
        assertTrue(simulator.updateFactory("noteadded,TEST123,1000,Test note") is NoteAddedUpdate)
        assertTrue(simulator.updateFactory("shipped,TEST123,1000,1700000000") is ShippedUpdate)
    }
    
    @Test
    fun testUpdateFactoryInvalidStatus() {
        val invalidUpdate = simulator.updateFactory("invalid,TEST123,1000")
        assertNull(invalidUpdate)
    }
    
    @Test
    fun testUpdateFactoryCaseInsensitive() {
        assertTrue(simulator.updateFactory("CREATED,TEST123,1000") is CreatedUpdate)
        assertTrue(simulator.updateFactory("Shipped,TEST123,1000,1700000000") is ShippedUpdate)
        assertTrue(simulator.updateFactory("LOCATION,TEST123,1000,Boston") is LocationUpdate)
    }
    
    @Test
    fun testAddShipment() {
        val createdUpdate = CreatedUpdate("created,TEST123,1000")
        simulator.addShipment(createdUpdate)
        
        assertEquals(1, simulator.shipments.size)
        assertTrue(simulator.shipments.containsKey("TEST123"))
        assertEquals("TEST123", simulator.shipments["TEST123"]?.id)
    }
    
    @Test
    fun testFindShipment() {
        val createdUpdate = CreatedUpdate("created,TEST123,1000")
        simulator.addShipment(createdUpdate)
        
        val foundShipment = simulator.findShipment("TEST123")
        assertNotNull(foundShipment)
        assertEquals("TEST123", foundShipment.id)
        
        val notFoundShipment = simulator.findShipment("NONEXISTENT")
        assertNull(notFoundShipment)
    }
    
    @Test
    fun testMultipleShipments() {
        val shipment1 = CreatedUpdate("created,SHIP001,1000")
        val shipment2 = CreatedUpdate("created,SHIP002,2000")
        
        simulator.addShipment(shipment1)
        simulator.addShipment(shipment2)
        
        assertEquals(2, simulator.shipments.size)
        assertNotNull(simulator.findShipment("SHIP001"))
        assertNotNull(simulator.findShipment("SHIP002"))
    }
}
