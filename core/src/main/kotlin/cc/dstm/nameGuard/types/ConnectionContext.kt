package cc.dstm.nameGuard.types

import net.kyori.adventure.text.Component
import cc.dstm.nameGuard.Main
import java.net.InetAddress

data class ConnectionContext(
    val name: String,
    val ip: InetAddress?,
    val virtualHost: String,
    val plugin: Main
) {

    data class CheckResult(
        val isAllowed: Boolean,
        val kickMessage: Component?
    )

    fun isAllowed(): CheckResult {

        for (blockedGroup in plugin.blockedConfig.groups) {

            val blockedByNickname = blockedGroup.nicknames.contains(name.lowercase())
            val blockedByPattern = blockedGroup.patterns.any { it.matchEntire(name) != null }
            if (!blockedByNickname && !blockedByPattern) continue

            if (blockedGroup.logToConsole) {
                plugin.logWarning("Blocked connection: $name tried to join from $ip. " +
                        "Blocked by: group \"${blockedGroup.groupName}\" in blocked.yml.")
            }
            return CheckResult(false, blockedGroup.kickMessage)
        }

        for (whitelistGroup in plugin.whitelistConfig.groups) {
            val accessRules = whitelistGroup.nicknames[name.lowercase()] ?:   continue

            val passed = accessRules.any { it.isAllowed(this) } ||
                    whitelistGroup.shared.any { it.isAllowed(this) }
            if (passed) continue

            if (whitelistGroup.logToConsole) {
                plugin.logWarning("Blocked connection: $name tried to join from $ip to " +
                        "virtual host $virtualHost. " +
                        "Blocked by: group \"${whitelistGroup.groupName}\" in whitelist.yml.")
            }
            return CheckResult(false, whitelistGroup.kickMessage)
        }

        return CheckResult(true, null)
    }

}