package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.MainImpl
import cc.dstm.nameGuard.types.BlockedGroup
import cc.dstm.nameGuard.util.mm

class BlockedConfigImpl(plugin: MainImpl) : AbstractConfigImpl(plugin, "blocked.yml"), BlockedConfig {

    override var groups = listOf<BlockedGroup>()

    override fun loadFromDisk() {
        super.loadFromDisk()
        val groups = mutableListOf<BlockedGroup>()

        for (groupKey in config.keys) {
            val groupSection = config.getSection(groupKey)

            // Skip if "enabled: false"
            if (!groupSection.getBoolean("enabled", true)) continue

            val nicknamesList = groupSection.getStringList("nicknames").map { it.lowercase() }
            val patternsList = groupSection.getStringList("patterns").map { Regex(it, RegexOption.IGNORE_CASE) }

            groups.add(BlockedGroup(
                groupName = groupKey,
                logToConsole = groupSection.getBoolean("log-to-console", false),
                kickMessage = groupSection.getString("kick-message")?.let { mm(it) },
                nicknames = nicknamesList,
                patterns = patternsList
            ))
        }

        this.groups = groups
    }

}