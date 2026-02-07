package cc.dstm.nameGuard.listener

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.ConnectionContext
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class PreLoginListener(private val plugin: MainImpl) : Listener {

    @EventHandler
    fun onAsyncPlayerPreLogin(event: AsyncPlayerPreLoginEvent) {

        val connectionContext = ConnectionContext(
            event.name,
            event.address,
            event.hostname.substringBefore(':'),
            plugin
        )

        val blockingAccessGroup = plugin.accessGroupsConfig.getBlockingGroup(connectionContext)

        if (blockingAccessGroup != null) {
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                blockingAccessGroup.kickMessage
            )
        }
    }

}