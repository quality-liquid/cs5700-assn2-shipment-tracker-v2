package trackingSimulator

import trackingSimulator.shippingUpdates.*
import kotlin.test.*

class ShipmentObserverTest {
    
    private lateinit var shipment: Shipment
    
    @BeforeTest
    fun setup() {
        val createdUpdate = CreatedUpdate("created,OBSERVER_TEST,1000,standard")
        shipment = Shipment(createdUpdate)
    }
    
    @Test
    fun testSingleObserver() {
        val observer = MockObserver()
        shipment.addObserver(observer)
        
        assertEquals(0, observer.callCount)
        
        val update = ShippedUpdate("shipped,OBSERVER_TEST,2000,1700000000")
        shipment.addUpdate(update)
        
        assertEquals(1, observer.callCount)
        assertEquals(shipment, observer.lastShipment)
    }
    
    @Test
    fun testMultipleObservers() {
        val observer1 = MockObserver()
        val observer2 = MockObserver()
        val observer3 = MockObserver()
        
        shipment.addObserver(observer1)
        shipment.addObserver(observer2)
        shipment.addObserver(observer3)
        
        val update = LocationUpdate("location,OBSERVER_TEST,2000,Boston")
        shipment.addUpdate(update)
        
        assertEquals(1, observer1.callCount)
        assertEquals(1, observer2.callCount)
        assertEquals(1, observer3.callCount)
    }
    
    @Test
    fun testObserverRemoval() {
        val observer1 = MockObserver()
        val observer2 = MockObserver()
        
        shipment.addObserver(observer1)
        shipment.addObserver(observer2)
        
        shipment.removeObserver(observer1)
        
        val update = NoteAddedUpdate("noteadded,OBSERVER_TEST,2000,Test note")
        shipment.addUpdate(update)
        
        assertEquals(0, observer1.callCount)
        assertEquals(1, observer2.callCount)
    }
    
    @Test
    fun testObserverNotCalledOnNonUpdate() {
        val observer = MockObserver()
        shipment.addObserver(observer)
        
        // Directly calling notifyObservers should still work
        shipment.notifyObservers()
        assertEquals(1, observer.callCount)
        
        // But just adding a note shouldn't trigger notification
        shipment.addNote("Manual note")
        assertEquals(1, observer.callCount) // Should still be 1
    }
    
    @Test
    fun testObserverCalledMultipleTimes() {
        val observer = MockObserver()
        shipment.addObserver(observer)
        
        val updates = listOf(
            ShippedUpdate("shipped,OBSERVER_TEST,2000,1700000000"),
            LocationUpdate("location,OBSERVER_TEST,3000,Boston"),
            NoteAddedUpdate("noteadded,OBSERVER_TEST,4000,Note"),
            DeliveredUpdate("delivered,OBSERVER_TEST,5000")
        )
        
        updates.forEach { update ->
            shipment.addUpdate(update)
        }
        
        assertEquals(4, observer.callCount)
        assertEquals(shipment, observer.lastShipment)
    }
    
    @Test
    fun testObserverRemovedMultipleTimes() {
        val observer = MockObserver()
        shipment.addObserver(observer)
        
        // Removing multiple times should not cause issues
        shipment.removeObserver(observer)
        shipment.removeObserver(observer)
        shipment.removeObserver(observer)
        
        val update = LocationUpdate("location,OBSERVER_TEST,2000,Boston")
        shipment.addUpdate(update)
        
        assertEquals(0, observer.callCount)
    }
    
    @Test
    fun testObserverAddedMultipleTimes() {
        val observer = MockObserver()
        
        // Adding same observer multiple times
        shipment.addObserver(observer)
        shipment.addObserver(observer)
        shipment.addObserver(observer)
        
        val update = LocationUpdate("location,OBSERVER_TEST,2000,Boston")
        shipment.addUpdate(update)
        
        // Should be called once for each time it was added
        assertEquals(3, observer.callCount)
    }
    
    private class MockObserver : ShipmentObserver {
        var callCount = 0
        var lastShipment: Shipment? = null
        
        override fun ShipmentUpdated(shipment: Shipment) {
            callCount++
            lastShipment = shipment
        }
    }
}
