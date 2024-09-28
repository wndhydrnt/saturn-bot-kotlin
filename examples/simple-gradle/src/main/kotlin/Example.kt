/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.example

import net.saturnbot.plugin.Context
import net.saturnbot.plugin.Plugin
import kotlin.io.path.Path

class Example : Plugin("simple-maven") {
    private var content: String = "Hello Kotlin\n"

    override fun init(config: Map<String, String>) {
        content = config["message"] ?: ""
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
