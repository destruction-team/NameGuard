package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.AccessRule
import cc.dstm.nameGuard.types.WhitelistGroup

class WhitelistConfigImpl(private val plugin: MainImpl) : AbstractConfigImpl(plugin, "whitelist.yml"), WhitelistConfig {

    override var groups = listOf<WhitelistGroup>()

    override fun loadFromDisk() {
        super.loadFromDisk()
        val groups = mutableListOf<WhitelistGroup>()

        for (groupKey in config.getKeys(false)) {
            val groupSection = config.getConfigurationSection(groupKey) ?: continue

            // Skip if "enabled: false"
            if (!groupSection.getBoolean("enabled", true)) continue

            val nicknamesSection = groupSection.getConfigurationSection("nicknames")

            fun parseAccessRules(rules: Iterable<String>): List<AccessRule> {
                return rules.mapNotNull {
                    val accessRule = AccessRule.fromString(it)
                    if (accessRule == null) plugin.logger.warning("Invalid access rule in whitelist.yml: $it")
                    accessRule
                }
            }

            val nicknamesMap = if (nicknamesSection != null) {
                nicknamesSection.getKeys(false).associate { nickname ->
                    nickname.lowercase() to parseAccessRules(nicknamesSection.getStringList(nickname))
                }
            } else mapOf()

            groups.add(WhitelistGroup(
                groupName = groupKey,
                logToConsole = groupSection.getBoolean("log-to-console", false),
                kickMessage = groupSection.getRichMessage("kick-message"),
                shared = parseAccessRules(groupSection.getStringList("shared")),
                nicknames = nicknamesMap
            ))
        }

        this.groups = groups
    }

}