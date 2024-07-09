/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.saturnbot.plugin

import net.saturnbot.plugin.protocol.GRPCControllerGrpcKt
import net.saturnbot.plugin.protocol.GrpcController

class ShutdownController : GRPCControllerGrpcKt.GRPCControllerCoroutineImplBase() {
    private val hooks: MutableList<Thread> = mutableListOf()

    fun addShutdownHook(hook: Thread) {
        hooks.add(hook)
    }

    override suspend fun shutdown(request: GrpcController.Empty): GrpcController.Empty {
        hooks.forEach { t -> t.start() }
        return GrpcController.Empty.newBuilder().build()
    }
}
