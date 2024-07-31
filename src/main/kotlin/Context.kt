/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

import net.saturnbot.plugin.protocol.Saturnbot

/**
 * Contextual information for each repository call made by saturn-bot.
 */
data class Context(
    /**
     * Path to the checkout of the current repository.
     */
    val path: String? = null,
    /**
     * Information on the current pull request, if available.
     */
    val pullRequest: Saturnbot.PullRequest,
    /**
     * Information on the current repository, if available.
     */
    val repository: Saturnbot.Repository,
    /**
     * Data of a run.
     *
     * Plugins can use this map to exchange data between each other.
     * It is also used in templating.
     */
    val runData: MutableMap<String, String>,
)
