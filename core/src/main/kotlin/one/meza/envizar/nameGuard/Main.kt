package one.meza.envizar.nameGuard

import one.meza.envizar.nameGuard.config.AccessGroupsConfig
import java.io.File

interface Main {

    val accessGroupsConfig: AccessGroupsConfig

    fun getDataFolder(): File

    fun logWarning(msg: String)

}