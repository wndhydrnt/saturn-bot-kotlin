/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

/**
 * Plugin is the base class every plugin must extend.
 */
open class Plugin {
    /**
     * Change files in the checkout of a repository.
     *
     * [ctx] contains the path to the checkout of a repository.
     *
     * This function is called for each repository that matches a Task.
     *
     * @param[ctx] Current repository context.
     */
    open fun apply(ctx: Context) {}

    /**
     * Initialize the plugin.
     *
     * [config] stores arbitrary configuration values.
     *
     * This method is called by saturn-bot when it starts the plugin.
     *
     * @param[config] Key/value configuration of the plugin.
     */
    open fun init(config: Map<String, String>) {}

    /**
     * Detect if saturn-bot should process a repository or not.
     *
     * If this function returns true and all other filters defined in the saturn-bot Task file
     * match as well, saturn-bot clones the repository and calls the apply method.
     *
     * @param[ctx] Current repository context.
     * @return true if the current repository matches, otherwise return false.
     */
    open fun filter(ctx: Context): Boolean = true

    /**
     * saturn-bot calls this method after it closed a pull request.
     *
     * Use this method to execute arbitrary tasks, like sending messages or HTTP requests.
     *
     * @param[ctx] Current repository context.
     */
    open fun onPrClosed(ctx: Context) {}

    /**
     * saturn-bot calls this method after it created a pull request.
     *
     * Use this method to execute arbitrary tasks, like sending messages or HTTP requests.
     *
     * @param[ctx] Current repository context.
     */
    open fun onPrCreated(ctx: Context) {}

    /**
     * saturn-bot calls this method after it merged a pull request.
     *
     * Use this method to execute arbitrary tasks, like sending messages or HTTP requests.
     *
     * @param[ctx] Current repository context.
     */
    open fun onPrMerged(ctx: Context) {}
}
