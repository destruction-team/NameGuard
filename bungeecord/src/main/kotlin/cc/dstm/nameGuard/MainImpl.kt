package cc.dstm.nameGuard

import cc.dstm.nameGuard.command.ReloadCommand
import cc.dstm.nameGuard.config.AccessGroupsConfigImpl
import cc.dstm.nameGuard.listener.PreLoginListener
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.md_5.bungee.api.plugin.Plugin

class MainImpl : Plugin(), Main {

    private var adventure: BungeeAudiences? = null

    override val accessGroupsConfig = AccessGroupsConfigImpl(this)

    override fun logWarning(msg: String) {
        logger.warning(msg)
    }

    override fun onEnable() {
        adventure = BungeeAudiences.create(this)

        accessGroupsConfig.loadFromDisk()

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
