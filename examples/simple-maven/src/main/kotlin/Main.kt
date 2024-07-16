package net.saturnbot.example

import net.saturnbot.plugin.Server

fun main() {
    // Initialize the plugin
    val example = Example()
    // Initialize the server and register the plugin
    val server = Server(example)
    // Serve the plugin
    server.servePlugin()
}
