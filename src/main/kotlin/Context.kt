/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

import net.saturnbot.plugin.protocol.Saturnbot

data class Context(
    val path: String? = null,
    val pluginData: MutableMap<String, String>,
    val pullRequest: Saturnbot.PullRequest,
    val repository: Saturnbot.Repository,
    val templateVars: MutableMap<String, String>? = null
)
