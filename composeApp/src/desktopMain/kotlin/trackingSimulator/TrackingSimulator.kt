package trackingSimulator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import trackingSimulator.shippingUpdates.CanceledUpdate
import trackingSimulator.shippingUpdates.CreatedUpdate
import trackingSimulator.shippingUpdates.DelayedUpdate
import trackingSimulator.shippingUpdates.DeliveredUpdate
import trackingSimulator.shippingUpdates.LocationUpdate
import trackingSimulator.shippingUpdates.LostUpdate
import trackingSimulator.shippingUpdates.NoteAddedUpdate
import trackingSimulator.shippingUpdates.ShippedUpdate
import trackingSimulator.shippingUpdates.ShippingUpdate
import userInterface.App
import java.io.File


val simulator: TrackingSimulator = TrackingSimulator()

class TrackingSimulator {
    private val _shipments: MutableMap<String, Shipment> = mutableMapOf()
    val shipments: Map<String, Shipment> get() = _shipments

    private val scope = CoroutineScope(Dispatchers.Default)

    fun runSimulation() {
        val lines: List<String> = File("data/test.txt").readLines()
        scope.launch {
            for (line: String in lines) {
                val splitLine: List<String> = line.split(",")
                val id: String = splitLine[1]
                val shippingUpdate = updateFactory(line)
                if (shippingUpdate == null) {
                    println("invalid update status string")
                } else {
                    if (shippingUpdate is CreatedUpdate) {
                        addShipment(shippingUpdate)
                        println("created shipment with id $id")
                    } else {
                        val shipment = findShipment(id)
                        if (shipment != null) {
                            _shipments[id]?.addUpdate(shippingUpdate)
                            println("added update for id: $id")
                        } else {
                            println("no shipment with id $id")
                        }
                    }
                }
                delay(1000)
            }
        }
    }

    fun updateFactory(line: String): ShippingUpdate? {
        val status = line.split(",")[0]
        return when (status.lowercase()) {
            "canceled" -> CanceledUpdate(line)
            "created" -> CreatedUpdate(line)
            "delayed" -> DelayedUpdate(line)
            "delivered" -> DeliveredUpdate(line)
            "location" -> LocationUpdate(line)
            "lost" -> LostUpdate(line)
            "noteadded" -> NoteAddedUpdate(line)
            "shipped" -> ShippedUpdate(line)
            else -> null
        }
    }

    fun addShipment(creationUpdate: CreatedUpdate) {
        val shipment = Shipment(creationUpdate)
        this._shipments[shipment.id] = shipment
    }

    fun findShipment(id: String): Shipment? {
        if (_shipments.containsKey(id)) {
            return _shipments[id]
        } else return null
    }
}

fun main() {
    simulator.runSimulation()

    application {
        Window(onCloseRequest = ::exitApplication, title = "Shipment Tracker") {
            App()
        }
    }
}
