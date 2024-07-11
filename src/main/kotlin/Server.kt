/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.protobuf.services.HealthStatusManager

/**
 * Server serves the plugin.
 */
class Server(
    private val plugin: Plugin,
) {
    /**
     * Start serving the plugin via GRPC.
     *
     * A random port is chosen for the GRPC server.
     * Connection information is written to standard output as required by go-plugin.
     */
    fun servePlugin() {
        val shutdownController = ShutdownController()
        val healthStatusManager = HealthStatusManager()
        val port = (30000..35000).random()
        val server: Server =
            ServerBuilder
                .forPort(port)
                .addService(Service(plugin))
                .addService(shutdownController)
                .addService(healthStatusManager.healthService)
                .build()
        shutdownController.addShutdownHook(
            Thread {
                server.shutdown()
            },
        )
        healthStatusManager.setStatus("plugin", HealthCheckResponse.ServingStatus.SERVING)
        server.start()
        println("1|1|tcp|127.0.0.1:$port|grpc")
        server.awaitTermination()
    }
}
