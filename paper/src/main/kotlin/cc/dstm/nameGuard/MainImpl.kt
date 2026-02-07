package cc.dstm.nameGuard

import cc.dstm.nameGuard.command.ReloadCommand
import cc.dstm.nameGuard.config.AccessGroupsConfigImpl
import cc.dstm.nameGuard.listener.PreLoginListener
import cc.dstm.nameGuard.util.runAsync
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class MainImpl : JavaPlugin(), Main {

    fun registerBukkitListener(listener: Listener) = server.pluginManager.registerEvents(listener, this)

    override val accessGroupsConfig = AccessGroupsConfigImpl(this)

    override fun logWarning(msg: String) {
        logger.warning(msg)
    }

    override fun onEnable() {

        this.runAsync {
            accessGroupsConfig.loadFromDisk()
        }

        registerBukkitListener(PreLoginListener(this))

        ReloadCommand.register(this)
    }

}