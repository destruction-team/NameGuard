package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.AccessGroup
import cc.dstm.nameGuard.types.AccessRule
import net.kyori.adventure.text.Component

class AccessGroupsConfigImpl(plugin: MainImpl) :
    AbstractConfigImpl(plugin, "access-groups.yml"), AccessGroupsConfig
{
    override var groups: List<AccessGroup> = listOf()

    override fun loadFromDisk() {
        super.loadFromDisk()
        val groups = mutableListOf<AccessGroup>()

        for (groupId in config.getKeys(false)) {

            val groupSection = config.getConfigurationSection(groupId)!!

            if (!groupSection.getBoolean("enabled", true)) continue

            groups.add(AccessGroup(
                id = groupId,

                logToConsole = groupSection.getBoolean("log-to-console", false),
                kickMessage = groupSection.getRichMessage("kick-message") ?: Component.empty(),

                accessRules = groupSection.getStringList("access-rules")
                    .map { AccessRule.fromString(it) ?: error("Invalid access rule: $it") },

                match = groupSection.getConfigurationSection("match")?.let { AccessGroup.Match(
                    everything = it.getBoolean("everything", false),
                    nicknames = it.getStringList("nicknames"),
                    patterns = it.getStringList("patterns").map { pattern -> Regex(pattern, RegexOption.IGNORE_CASE) }
                )}
            ))

        }

        this.groups = groups
    }

}