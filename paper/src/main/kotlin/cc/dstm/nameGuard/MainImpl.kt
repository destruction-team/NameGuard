package cc.dstm.nameGuard

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import cc.dstm.nameGuard.command.ReloadCommand
import cc.dstm.nameGuard.config.BlockedConfigImpl
import cc.dstm.nameGuard.config.WhitelistConfigImpl
import cc.dstm.nameGuard.listener.PreLoginListener
import cc.dstm.nameGuard.util.runAsync

class MainImpl : JavaPlugin(), Main {

    fun registerBukkitListener(listener: Listener) = server.pluginManager.registerEvents(listener, this)

    override val blockedConfig = BlockedConfigImpl(this)
    override val whitelistConfig = WhitelistConfigImpl(this)

    override fun logWarning(msg: String) {
        logger.warning(msg)
    }

    override fun onEnable() {

        this.runAsync {
            blockedConfig.loadFromDisk()
            whitelistConfig.loadFromDisk()
        }

        registerBukkitListener(PreLoginListener(this))

        ReloadCommand.register(this)
    }

}