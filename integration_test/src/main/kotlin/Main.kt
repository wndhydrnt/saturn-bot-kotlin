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

class IntegrationTest : Plugin() {
    private var eventOutTmpFilePath: String = ""
    private var staticContent: String = ""

    override fun init(config: Map<String, String>) {
        eventOutTmpFilePath = config["event_out_tmp_file_path"] ?: ""
        staticContent = config["content"] ?: ""
    }

    override fun filter(ctx: Context): Boolean {
        if (ctx.repository.fullName == "git.localhost/integration/test") {
            return true
        }

        if (ctx.repository.fullName == "git.localhost/integration/rundata") {
            ctx.runData["plugin"] = "set by plugin"
            return true
        }

        return false
    }

    override fun apply(ctx: Context) {
        val file = Path(ctx.path ?: "", "integration-test.txt").toFile()
        file.writeText("$staticContent\n${ctx.runData["dynamic"]}")
    }

    override fun onPrClosed(ctx: Context) {
        Path(System.getProperty("java.io.tmpdir"), eventOutTmpFilePath)
            .toFile()
            .writeText("Integration Test OnPrClosed")
    }

    override fun onPrCreated(ctx: Context) {
        Path(System.getProperty("java.io.tmpdir"), eventOutTmpFilePath)
            .toFile()
            .writeText("Integration Test OnPrCreated")
    }

    override fun onPrMerged(ctx: Context) {
        Path(System.getProperty("java.io.tmpdir"), eventOutTmpFilePath)
            .toFile()
            .writeText("Integration Test OnPrMerged")
    }
}

fun main() {
    val plugin = IntegrationTest()
    val server = Server(plugin)
    server.servePlugin()
}
