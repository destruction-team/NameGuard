package cc.dstm.nameGuard.config

import org.bukkit.configuration.file.YamlConfiguration
import cc.dstm.nameGuard.MainImpl

abstract class AbstractConfigImpl(private val plugin: MainImpl, val configName: String) : AbstractConfig(plugin, configName) {

    lateinit var config: YamlConfiguration
        private set

    override fun loadFromDisk() {
        if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()
        if (!configFile.exists()) {
            plugin.saveResource(configName, false)
        }
        config = YamlConfiguration.loadConfiguration(configFile)
    }

}