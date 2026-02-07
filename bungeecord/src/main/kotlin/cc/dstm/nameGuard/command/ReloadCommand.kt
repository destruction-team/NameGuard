package cc.dstm.nameGuard.command

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.util.mm
import cc.dstm.nameGuard.util.toBaseComponent
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command

class ReloadCommand(private val plugin: MainImpl) : Command("nameguard", "nameguard.admin", "ng") {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        when (args[0].lowercase()) {
            "reload" -> {
                plugin.accessGroupsConfig.loadFromDisk()
                sender.sendMessage(mm("<gold>[NameGuard]:</gold> Configurations reloaded.").toBaseComponent())
            }
        }
    }
}
