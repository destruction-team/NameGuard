package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.AccessRule
import cc.dstm.nameGuard.types.WhitelistGroup
import cc.dstm.nameGuard.util.mm

class WhitelistConfigImpl(private val plugin: MainImpl) : AbstractConfigImpl(plugin, "whitelist.yml"), WhitelistConfig {

    override var groups = listOf<WhitelistGroup>()

    override fun loadFromDisk() {
        super.loadFromDisk()
        val groups = mutableListOf<WhitelistGroup>()

        for (groupKey in config.keys) {
            val groupSection = config.getSection(groupKey)

            // Skip if "enabled: false"
            if (!groupSection.getBoolean("enabled", true)) continue

            val nicknamesSection = groupSection.getSection("nicknames")

            fun parseAccessRules(rules: Iterable<String>): List<AccessRule> {
                return rules.mapNotNull {
                    val accessRule = AccessRule.fromString(it)
                    if (accessRule == null) plugin.logger.warning("Invalid access rule in whitelist.yml: $it")
                    accessRule
                }
            }

            val nicknamesMap = nicknamesSection.keys.associate { nickname ->
                nickname.lowercase() to (nicknamesSection.getStringList(nickname)
                            ?.let { parseAccessRules(it) } ?: emptyList())
            }

            groups.add(WhitelistGroup(
                groupName = groupKey,
                logToConsole = groupSection.getBoolean("log-to-console", false),
                kickMessage = groupSection.getString("kick-message")?.let { mm(it) },
                shared = groupSection.getStringList("shared")?.let { parseAccessRules(it) } ?: emptyList(),
                nicknames = nicknamesMap
            ))
        }

        this.groups = groups
    }

}