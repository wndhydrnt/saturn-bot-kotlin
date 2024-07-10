/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

open class Plugin {
    open fun apply(ctx: Context) {}

    open fun init(config: Map<String, String>) {}

    open fun filter(ctx: Context): Boolean = true

    open fun onPrClosed(ctx: Context) {}

    open fun onPrCreated(ctx: Context) {}

    open fun onPrMerged(ctx: Context) {}
}
