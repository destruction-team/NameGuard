package cc.dstm.nameGuard.types

import net.kyori.adventure.text.Component

data class AccessGroup(
    val id: String,
    val logToConsole: Boolean,
    val kickMessage: Component,
    val accessRules: List<AccessRule>,
    val match: Match?
) {

    data class Match(
        val everything: Boolean,
        val nicknames: List<String>,
        val patterns: List<Regex>
    ) {
        fun test(string: String) =
            everything ||
            string.lowercase() in nicknames ||
            patterns.any { it.containsMatchIn(string) }
    }

}

