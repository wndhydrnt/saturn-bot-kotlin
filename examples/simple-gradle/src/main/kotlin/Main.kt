/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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
