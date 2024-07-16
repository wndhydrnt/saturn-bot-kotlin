package net.saturnbot.example

import net.saturnbot.plugin.Context
import net.saturnbot.plugin.Plugin
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
