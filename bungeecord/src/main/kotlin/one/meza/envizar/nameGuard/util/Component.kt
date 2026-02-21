package one.meza.envizar.nameGuard.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent

val miniMessage = MiniMessage.miniMessage()
val bungeeComponentSerializer: BungeeComponentSerializer = BungeeComponentSerializer.get()

fun mm(string: String) = miniMessage.deserialize(string)

fun Component.toBaseComponents() = bungeeComponentSerializer.serialize(this)
fun Component.toBaseComponent() = toBaseComponents().merge()

fun Array<BaseComponent>.merge(): BaseComponent {
    val res = TextComponent()
    for (comp in this) {
        res.addExtra(comp)
    }
    return res
}