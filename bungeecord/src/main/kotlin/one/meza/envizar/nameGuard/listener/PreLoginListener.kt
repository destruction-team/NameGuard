package one.meza.envizar.nameGuard.listener

import one.meza.envizar.nameGuard.MainImpl
import one.meza.envizar.nameGuard.types.ConnectionContext
import one.meza.envizar.nameGuard.util.toBaseComponent
import one.meza.envizar.nameGuard.util.toInetAddress
import net.md_5.bungee.api.event.PreLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class PreLoginListener(val plugin: MainImpl) : Listener {

    @EventHandler
    fun onPreLogin(event: PreLoginEvent) {

        val connectionContext = ConnectionContext(
            event.connection.name,
            event.connection.socketAddress.toInetAddress(),
            event.connection.virtualHost.hostName,
            plugin
        )

        val blockingAccessGroup = plugin.accessGroupsConfig.getBlockingGroup(connectionContext)

        if (blockingAccessGroup != null) {
            event.reason = blockingAccessGroup.kickMessage.toBaseComponent()
            event.isCancelled = true
        }

    }
}
