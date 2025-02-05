package plutoproject.feature.paper.warp.commands

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.text
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.future.await
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.exception.ExceptionHandler
import org.incendo.cloud.context.CommandContext
import org.incendo.cloud.context.CommandInput
import org.incendo.cloud.parser.ArgumentParseResult
import org.incendo.cloud.parser.ArgumentParser.FutureArgumentParser
import org.incendo.cloud.suggestion.Suggestion
import org.incendo.cloud.suggestion.SuggestionProvider
import plutoproject.feature.paper.api.warp.Warp
import plutoproject.feature.paper.api.warp.WarpManager
import plutoproject.feature.paper.warp.COMMAND_PREFERRED_SPAWN_FAILED_ALREADY
import plutoproject.feature.paper.warp.COMMAND_PREFERRED_SPAWN_SUCCEED
import plutoproject.feature.paper.warp.COMMAND_PREFERRED_SPAWN_WARP_IS_NOT_SPAWN
import plutoproject.feature.paper.warp.screens.DefaultSpawnPickerScreen
import plutoproject.framework.common.util.chat.component.replace
import plutoproject.framework.common.util.chat.palettes.mochaSubtext0
import plutoproject.framework.common.util.chat.palettes.mochaText
import plutoproject.framework.common.util.coroutine.runAsync
import plutoproject.framework.paper.api.interactive.startScreen
import plutoproject.framework.paper.util.command.ensurePlayer
import java.util.concurrent.CompletableFuture
import kotlin.jvm.optionals.getOrNull

@Suppress("UNUSED", "UNUSED_PARAMETER", "UnusedReceiverParameter")
object PreferredSpawnCommand {
    @Command("preferredspawn [spawn]")
    @Permission("essentials.defaultspawn")
    suspend fun CommandSender.preferredSpawn(@Argument("spawn", parserName = "spawn") spawn: Warp?) = ensurePlayer {
        if (spawn == null) {
            startScreen(DefaultSpawnPickerScreen())
            return@ensurePlayer
        }
        val current = WarpManager.getPreferredSpawn(this)
        if (current?.name == spawn.name) {
            sendMessage(
                COMMAND_PREFERRED_SPAWN_FAILED_ALREADY.replace("<name>", if (spawn.alias != null) component {
                    text("${spawn.alias} ") with mochaText
                    text("(${spawn.name})") with mochaSubtext0
                } else Component.text(spawn.name)))
            return
        }
        WarpManager.setPreferredSpawn(this, spawn)
        sendMessage(
            COMMAND_PREFERRED_SPAWN_SUCCEED.replace("<name>", if (spawn.alias != null) component {
                text("${spawn.alias} ") with mochaText
                text("(${spawn.name})") with mochaSubtext0
            } else Component.text(spawn.name)))
    }

    @ExceptionHandler(WarpIsNotSpawnException::class)
    fun CommandSender.warpIsNotSpawn(exception: WarpIsNotSpawnException) {
        sendMessage(COMMAND_PREFERRED_SPAWN_WARP_IS_NOT_SPAWN.replace("<name>", exception.name))
    }
}

class SpawnParser : FutureArgumentParser<CommandSender, Warp>, SuggestionProvider<CommandSender> {
    private val warpParser = WarpParser(false)

    override fun parseFuture(
        commandContext: CommandContext<CommandSender>,
        commandInput: CommandInput
    ): CompletableFuture<ArgumentParseResult<Warp>> = runAsync {
        val warp = warpParser.parseFuture(commandContext, commandInput).await()
            .also {
                it.failure().getOrNull()?.also { e -> throw e }
            }.parsedValue().getOrNull() ?: error("Error while parsing spawn")
        if (warp.isSpawn) {
            ArgumentParseResult.success(warp)
        } else {
            throw WarpIsNotSpawnException(warp.name)
        }
    }.asCompletableFuture()

    override fun suggestionsFuture(
        context: CommandContext<CommandSender>,
        input: CommandInput
    ): CompletableFuture<List<Suggestion>> = runAsync {
        WarpManager.listSpawns().map {
            val name = it.name
            val alias = it.alias
            Suggestion.suggestion(if (alias == null) name else "$name-$alias")
        }
    }.asCompletableFuture()

    override fun suggestionProvider(): SuggestionProvider<CommandSender> {
        return this
    }
}

class WarpIsNotSpawnException(val name: String) : Exception()
