package trackingSimulator

import trackingSimulator.shippingUpdates.*
import trackingSimulator.shipmentDecorators.*
import kotlin.test.*

class TrackingSimulatorAdvancedTest {
    
    private lateinit var simulator: TrackingSimulator
    
    @BeforeTest
    fun setup() {
        simulator = TrackingSimulator()
    }
    
    @Test
    fun testSimpleShipmentFactoryStandard() {
        val createdUpdate = CreatedUpdate("created,STANDARD001,1000,standard")
        val baseShipment = Shipment(createdUpdate)
        val decoratedShipment = simulator.simpleShipmentFactory("standard", baseShipment)
        
        // Standard shipments should not be decorated
        assertSame(baseShipment, decoratedShipment)
    }
    
    @Test
    fun testSimpleShipmentFactoryExpress() {
        val createdUpdate = CreatedUpdate("created,EXPRESS001,1000,express")
        val baseShipment = Shipment(createdUpdate)
        val decoratedShipment = simulator.simpleShipmentFactory("express", baseShipment)
        
        assertTrue(decoratedShipment is ExpressDecorator)
    }
    
    @Test
    fun testSimpleShipmentFactoryBulk() {
        val createdUpdate = CreatedUpdate("created,BULK001,1000,bulk")
        val baseShipment = Shipment(createdUpdate)
        val decoratedShipment = simulator.simpleShipmentFactory("bulk", baseShipment)
        
        assertTrue(decoratedShipment is BulkDecorator)
    }
    
    @Test
    fun testSimpleShipmentFactoryOvernight() {
        val createdUpdate = CreatedUpdate("created,OVERNIGHT001,1000,overnight")
        val baseShipment = Shipment(createdUpdate)
        val decoratedShipment = simulator.simpleShipmentFactory("overnight", baseShipment)
        
        assertTrue(decoratedShipment is OvernightDecorator)
    }
    
    @Test
    fun testAddShipmentWithDecoration() {
        val createdUpdate = CreatedUpdate("created,DECORATED001,1000,express")
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("DECORATED001")
        assertNotNull(shipment)
        assertTrue(shipment is ExpressDecorator)
        assertEquals("DECORATED001", shipment.id)
    }
    
    @Test
    fun testUpdateFactoryEdgeCases() {
        // Test with extra whitespace
        val update1 = simulator.updateFactory(" created , TEST123 , 1000 , standard ")
        assertNull(update1) // Should fail due to whitespace
        
        // Test with empty string
        val update3 = simulator.updateFactory("")
        assertNull(update3)
    }
    
    @Test
    fun testCaseInsensitiveUpdateFactory() {
        val updates = listOf(
            "CREATED,TEST123,1000,standard",
            "shipped,TEST123,2000,1700000000",
            "Location,TEST123,3000,Boston",
            "NOTEADDED,TEST123,4000,Test note"
        )
        
        updates.forEach { updateString ->
            val update = simulator.updateFactory(updateString)
            assertNotNull(update, "Failed to parse: $updateString")
        }
    }
    
    @Test
    fun testShipmentMapIntegrity() {
        val shipment1 = CreatedUpdate("created,SHIP001,1000,standard")
        val shipment2 = CreatedUpdate("created,SHIP002,2000,express")
        
        simulator.addShipment(shipment1)
        simulator.addShipment(shipment2)
        
        assertEquals(2, simulator.shipments.size)
        assertTrue(simulator.shipments.containsKey("SHIP001"))
        assertTrue(simulator.shipments.containsKey("SHIP002"))
        
    }
    
    @Test
    fun testComplexUpdateSequence() {
        // Create express shipment
        val createdUpdate = simulator.updateFactory("created,COMPLEX001,1000,express") as CreatedUpdate
        simulator.addShipment(createdUpdate)
        
        val shipment = simulator.findShipment("COMPLEX001")!!
        
        // Add updates in sequence
        val updates = listOf(
            "shipped,COMPLEX001,2000,1700000000",
            "location,COMPLEX001,3000,Origin Hub",
            "noteadded,COMPLEX001,4000,Package scanned",
            "location,COMPLEX001,5000,Transit Hub",
            "delayed,COMPLEX001,6000,1700086400",
            "noteadded,COMPLEX001,7000,Weather delay",
            "location,COMPLEX001,8000,Destination Hub",
            "delivered,COMPLEX001,9000"
        )
        
        updates.forEach { updateString ->
            val update = simulator.updateFactory(updateString)!!
            shipment.addUpdate(update)
        }
        
        assertEquals("delivered", shipment.status)
        assertEquals("Destination Hub", shipment.current_location)
        assertEquals(1700086400L, shipment.expected_delivery_date_timestamp)
        assertEquals(9, shipment.update_history.size) // 1 created + 8 updates
    }
}
