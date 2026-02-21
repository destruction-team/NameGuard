package one.meza.envizar.nameGuard.config

import one.meza.envizar.nameGuard.MainImpl
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration

abstract class AbstractConfigImpl(private val plugin: MainImpl, configName: String) : AbstractConfig(plugin, configName) {

    val yamlConfigProvider: ConfigurationProvider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    lateinit var config: Configuration
        private set

    override fun loadFromDisk() {
        if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()
        if (!configFile.exists()) {
            plugin.getResourceAsStream(configFile.name).use { inputStream ->
                configFile.outputStream().use { outputStream -> inputStream.copyTo(outputStream) }
            }
        }
        config = yamlConfigProvider.load(configFile)
    }

}