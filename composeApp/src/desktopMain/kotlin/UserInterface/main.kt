package UserInterface

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "assn2-shipment-tracker-v2",
    ) {
        App()
    }
}