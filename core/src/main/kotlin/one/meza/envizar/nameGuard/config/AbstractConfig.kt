package one.meza.envizar.nameGuard.config

import one.meza.envizar.nameGuard.Main
import java.io.File

abstract class AbstractConfig(plugin: Main, configName: String) {

    val configFile = File(plugin.getDataFolder(), configName)

    abstract fun loadFromDisk()

}