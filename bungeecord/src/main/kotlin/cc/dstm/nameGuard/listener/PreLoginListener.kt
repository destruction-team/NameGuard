package cc.dstm.nameGuard.listener

import net.md_5.bungee.api.event.PreLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.ConnectionContext
import cc.dstm.nameGuard.util.toBaseComponent
import cc.dstm.nameGuard.util.toInetAddress

class PreLoginListener(val plugin: MainImpl) : Listener {

    @EventHandler
    fun onPreLogin(event: PreLoginEvent) {

        val connectionContext = ConnectionContext(
            event.connection.name,
            event.connection.socketAddress.toInetAddress(),
            event.connection.virtualHost.hostName,
            plugin
        )

        val checkResult = connectionContext.isAllowed()

        if (!checkResult.isAllowed) {
            checkResult.kickMessage?.let {
                event.reason = it.toBaseComponent()
            }
            event.isCancelled = true
        }

    }
}
