package cc.dstm.nameGuard.listener

import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.ConnectionContext

class PreLoginListener(private val plugin: MainImpl) : Listener {

    @EventHandler
    fun onAsyncPlayerPreLogin(event: AsyncPlayerPreLoginEvent) {

        val connectionContext = ConnectionContext(
            event.name,
            event.address,
            event.hostname.substringBefore(':'),
            plugin
        )

        val checkResult = connectionContext.isAllowed()

        if (!checkResult.isAllowed) {
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                checkResult.kickMessage ?: Component.empty()
            )
        }
    }

}