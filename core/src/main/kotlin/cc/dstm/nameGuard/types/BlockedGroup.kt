package cc.dstm.nameGuard.types

import net.kyori.adventure.text.Component

data class BlockedGroup(
    val groupName: String,
    val logToConsole: Boolean,
    val kickMessage: Component?,
    val nicknames: List<String>,
    val patterns: List<Regex>
)