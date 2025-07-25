package trackingSimulator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.server.application.call
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
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.receiveText
import userInterface.Client

val simulator: TrackingSimulator = TrackingSimulator()

class TrackingSimulator {
    private val _shipments: MutableMap<String, Shipment> = mutableMapOf()
    val shipments: Map<String, Shipment> get() = _shipments

    private val scope = CoroutineScope(Dispatchers.Default)

    // This is a simulator that you can optionally run instead of the server
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


    fun runServer() {
        println("Starting server on port 8888...")
        try {
            embeddedServer(Netty, port=8888) {
                routing {
                    post("/") {
                        try {
                            val updateString = call.receiveText()
                            println("Received update: $updateString")
                            
                            val splitLine: List<String> = updateString.split(",")
                            if (splitLine.size < 3) {
                                println("Invalid input format: $updateString")
                                call.respondText("Invalid input format - expected at least 3 comma-separated values")
                                return@post
                            }
                            
                            val id: String = splitLine[1]
                            val shippingUpdate = updateFactory(updateString)
                            
                            if (shippingUpdate == null) {
                                println("Invalid update status: ${splitLine[0]}")
                                call.respondText("Invalid update status: ${splitLine[0]}")
                            } else {
                                if (shippingUpdate is CreatedUpdate) {
                                    addShipment(shippingUpdate)
                                    val responseMessage = "Created shipment with id $id"
                                    println(responseMessage)
                                    call.respondText(responseMessage)
                                } else {
                                    val shipment = findShipment(id)
                                    if (shipment != null) {
                                        _shipments[id]?.addUpdate(shippingUpdate)
                                        val responseMessage = "Added update for id: $id"
                                        println(responseMessage)
                                        call.respondText(responseMessage)
                                    } else {
                                        val responseMessage = "No shipment with id $id"
                                        println(responseMessage)
                                        call.respondText(responseMessage)
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            val errorMessage = "Server error: ${e.message}"
                            println("Error processing request: ${e.message}")
                            e.printStackTrace()
                            call.respondText(errorMessage)
                        }
                    }
                }
            }.start(wait=false)
            println("Server started successfully on port 8888")
        } catch (e: Exception) {
            println("Failed to start server: ${e.message}")
            e.printStackTrace()
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
        var shipment = Shipment(creationUpdate)
        if (creationUpdate.getShipmentType() == "express") {
            shipment = ExpressShipment(shipment)
        }
        this._shipments[shipment.id] = shipment
    }

    fun findShipment(id: String): Shipment? {
        if (_shipments.containsKey(id)) {
            return _shipments[id]
        } else return null
    }
}

fun main() {
    // choose between running simulation and server (parameterize this?)
//    simulator.runSimulation()
    simulator.runServer()

    application {
        Window(onCloseRequest = ::exitApplication, title = "Shipment Tracker") {
            App()
        }
        Window(onCloseRequest = ::exitApplication, title = "Shipment Client") {
            Client()
        }
    }
}
