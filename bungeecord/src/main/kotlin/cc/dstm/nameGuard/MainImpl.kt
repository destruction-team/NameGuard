package cc.dstm.nameGuard

import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.md_5.bungee.api.plugin.Plugin
import cc.dstm.nameGuard.command.ReloadCommand
import cc.dstm.nameGuard.config.BlockedConfigImpl
import cc.dstm.nameGuard.config.WhitelistConfigImpl
import cc.dstm.nameGuard.listener.PreLoginListener

class MainImpl : Plugin(), Main {

    private var adventure: BungeeAudiences? = null

    override val whitelistConfig = WhitelistConfigImpl(this)
    override val blockedConfig = BlockedConfigImpl(this)

    override fun logWarning(msg: String) {
        logger.warning(msg)
    }

    override fun onEnable() {
        adventure = BungeeAudiences.create(this)

        whitelistConfig.loadFromDisk()
        blockedConfig.loadFromDisk()

        proxy.pluginManager.registerListener(this, PreLoginListener(this))
        proxy.pluginManager.registerCommand(this, ReloadCommand(this))
    }

    override fun onDisable() {
        adventure?.let {
            it.close()
            adventure = null
        }
    }
}
