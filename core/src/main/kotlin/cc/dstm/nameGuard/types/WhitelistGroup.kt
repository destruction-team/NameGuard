package cc.dstm.nameGuard.types

import net.kyori.adventure.text.Component

data class WhitelistGroup(
    val groupName: String,
    val logToConsole: Boolean,
    val kickMessage: Component?,
    val shared: List<AccessRule>,
    val nicknames: Map<String, List<AccessRule>>
)