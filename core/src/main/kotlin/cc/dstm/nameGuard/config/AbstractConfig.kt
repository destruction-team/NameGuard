package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.Main
import java.io.File

abstract class AbstractConfig(plugin: Main, configName: String) {

    val configFile = File(plugin.getDataFolder(), configName)

    abstract fun loadFromDisk()

}