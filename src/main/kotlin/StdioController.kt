package net.saturnbot.plugin

import com.google.protobuf.ByteString
import com.google.protobuf.Empty
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import net.saturnbot.plugin.protocol.GRPCStdioGrpcKt
import net.saturnbot.plugin.protocol.GrpcStdio

/**
 * StdioController implements the stdio service of go-plugin.
 *
 * @see <a href="https://github.com/hashicorp/go-plugin/blob/main/internal/plugin/grpc_stdio.proto">https://github.com/hashicorp/go-plugin/blob/main/internal/plugin/grpc_stdio.proto</a>
 */
class StdioController(
    val stdout: PluginOutputStream,
    val stderr: PluginOutputStream,
) : GRPCStdioGrpcKt.GRPCStdioCoroutineImplBase() {
    /**
     * streamStdio returns a Flow that streams stdout/stderr to saturn-bot.
     *
     * The implementation registers callbacks with the two <code>PluginOutputStream</code>
     * objects passed to the constructor.
     */
    override fun streamStdio(request: Empty): Flow<GrpcStdio.StdioData> =
        callbackFlow {
            this@StdioController.stdout.callback = { msg ->
                trySend(
                    GrpcStdio.StdioData
                        .newBuilder()
                        .setChannel(GrpcStdio.StdioData.Channel.STDOUT)
                        .setData(ByteString.copyFrom(msg, Charsets.UTF_8))
                        .build(),
                )
            }

            this@StdioController.stderr.callback = { msg ->
                trySend(
                    GrpcStdio.StdioData
                        .newBuilder()
                        .setChannel(GrpcStdio.StdioData.Channel.STDERR)
                        .setData(ByteString.copyFrom(msg, Charsets.UTF_8))
                        .build(),
                )
            }

            awaitClose { }
        }
}
