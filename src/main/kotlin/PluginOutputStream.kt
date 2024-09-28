/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

import java.io.ByteArrayOutputStream

/**
 * PluginOutputStream acts as the bridge between stdout/stderr streams, which require an OutputStream,
 * and StdioController.
 *
 * StdioController sets the callback function. The callback is executed whenever the stream gets flushed.
 *
 * @see net.saturnbot.plugin.StdioController
 */
class PluginOutputStream : ByteArrayOutputStream() {
    var callback: (String) -> Unit = {}

    override fun flush() {
        callback(this.toString("UTF-8"))
        // Call reset to clear the buffer and allow new input.
        super.reset()
    }
}
