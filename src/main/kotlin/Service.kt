/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

import net.saturnbot.plugin.protocol.PluginServiceGrpcKt
import net.saturnbot.plugin.protocol.Saturnbot

/**
 * Service implements the GRPC service to serve the plugin.
 */
class Service(
    val plugin: Plugin,
) : PluginServiceGrpcKt.PluginServiceCoroutineImplBase() {
    override suspend fun executeActions(request: Saturnbot.ExecuteActionsRequest): Saturnbot.ExecuteActionsResponse {
        // Copy run data because request.context.runDataMap is read-only
        val runData = HashMap<String, String>()
        runData.putAll(request.context.runDataMap)
        val ctx =
            Context(
                path = request.path,
                pullRequest = request.context.pullRequest,
                repository = request.context.repository,
                runData = runData,
            )
        try {
            plugin.apply(ctx)
            return Saturnbot.ExecuteActionsResponse
                .newBuilder()
                .putAllRunData(runData)
                .build()
        } catch (e: Exception) {
            return Saturnbot.ExecuteActionsResponse
                .newBuilder()
                .setError(e.message)
                .build()
        }
    }

    override suspend fun executeFilters(request: Saturnbot.ExecuteFiltersRequest): Saturnbot.ExecuteFiltersResponse {
        // Copy run data because request.context.runDataMap is read-only
        val runData = HashMap<String, String>()
        runData.putAll(request.context.runDataMap)
        val ctx =
            Context(
                pullRequest = request.context.pullRequest,
                repository = request.context.repository,
                runData = runData,
            )
        try {
            val result = plugin.filter(ctx)
            return Saturnbot.ExecuteFiltersResponse
                .newBuilder()
                .putAllRunData(runData)
                .setMatch(result)
                .build()
        } catch (e: Exception) {
            return Saturnbot.ExecuteFiltersResponse
                .newBuilder()
                .setError(e.message)
                .build()
        }
    }

    override suspend fun getPlugin(request: Saturnbot.GetPluginRequest): Saturnbot.GetPluginResponse {
        try {
            plugin.init(request.configMap)
            return Saturnbot.GetPluginResponse.newBuilder().build()
        } catch (e: Exception) {
            return Saturnbot.GetPluginResponse
                .newBuilder()
                .setError(e.message)
                .build()
        }
    }

    override suspend fun onPrClosed(request: Saturnbot.OnPrClosedRequest): Saturnbot.OnPrClosedResponse {
        val ctx =
            Context(
                pullRequest = request.context.pullRequest,
                repository = request.context.repository,
                runData = request.context.runDataMap,
            )
        try {
            plugin.onPrClosed(ctx)
            return Saturnbot.OnPrClosedResponse.newBuilder().build()
        } catch (e: Exception) {
            return Saturnbot.OnPrClosedResponse
                .newBuilder()
                .setError(e.message)
                .build()
        }
    }

    override suspend fun onPrCreated(request: Saturnbot.OnPrCreatedRequest): Saturnbot.OnPrCreatedResponse {
        val ctx =
            Context(
                pullRequest = request.context.pullRequest,
                repository = request.context.repository,
                runData = request.context.runDataMap,
            )
        try {
            plugin.onPrCreated(ctx)
            return Saturnbot.OnPrCreatedResponse.newBuilder().build()
        } catch (e: Exception) {
            return Saturnbot.OnPrCreatedResponse
                .newBuilder()
                .setError(e.message)
                .build()
        }
    }

    override suspend fun onPrMerged(request: Saturnbot.OnPrMergedRequest): Saturnbot.OnPrMergedResponse {
        val ctx =
            Context(
                pullRequest = request.context.pullRequest,
                repository = request.context.repository,
                runData = request.context.runDataMap,
            )
        try {
            plugin.onPrMerged(ctx)
            return Saturnbot
                .OnPrMergedResponse
                .newBuilder()
                .build()
        } catch (e: Exception) {
            return Saturnbot
                .OnPrMergedResponse
                .newBuilder()
                .setError(e.message)
                .build()
        }
    }
}
