package one.meza.envizar.nameGuard.config

import one.meza.envizar.nameGuard.MainImpl
import org.bukkit.configuration.file.YamlConfiguration

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