/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.integrationtest

import net.saturnbot.plugin.Context
import net.saturnbot.plugin.Plugin
import net.saturnbot.plugin.Server
import kotlin.io.path.Path

class Example : Plugin() {
    private var content: String = ""

    override fun init(config: Map<String, String>) {
        content = config["message"] ?: "Hello Kotlin"
    }

    override fun filter(ctx: Context): Boolean {
        // Match a single repository.
        // Implement more complex matching logic here by calling APIs.
        return ctx.repository.fullName == "github.com/wndhydrnt/saturn-bot-example"
    }

    override fun apply(ctx: Context) {
        val path = ctx.path ?: ""
        if (path == "") {
            return
        }
        // Create a file in the root of the repository.
        val file = Path(path, "hello-kotlin.txt").toFile()
        file.writeText(content)
    }
}

fun main() {
    // Initialize the plugin
    val example = Example()
    // Initialize the server and register the plugin
    val server = Server(example)
    // Serve the plugin
    server.servePlugin()
}
