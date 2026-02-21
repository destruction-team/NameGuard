package one.meza.envizar.nameGuard

import one.meza.envizar.nameGuard.command.ReloadCommand
import one.meza.envizar.nameGuard.config.AccessGroupsConfigImpl
import one.meza.envizar.nameGuard.listener.PreLoginListener
import one.meza.envizar.nameGuard.util.runAsync
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