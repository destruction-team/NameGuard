package cc.dstm.nameGuard.command

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.util.runAsync
import com.mojang.brigadier.Command
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

@Suppress("UnstableApiUsage")
object ReloadCommand {

    fun register(plugin: MainImpl) {

        val reloadSubcommand = Commands.literal("reload").executes { ctx ->

            if (!ctx.source.sender.hasPermission("nameguard.reload")) {
                ctx.source.sender.sendRichMessage("<red>Permission denied")
                return@executes Command.SINGLE_SUCCESS
            }

            plugin.runAsync {
                plugin.accessGroupsConfig.loadFromDisk()
                ctx.source.sender.sendRichMessage("<green>Reloaded NameGuard")
            }
            return@executes Command.SINGLE_SUCCESS
        }

        val command = Commands.literal("nameguard").then(reloadSubcommand).build()
        val alias = Commands.literal("ng").then(reloadSubcommand).build()

        plugin.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            commands.registrar().register(command)
            commands.registrar().register(alias)
        }
    }

}