package cc.dstm.nameGuard

import cc.dstm.nameGuard.config.AccessGroupsConfig
import java.io.File

interface Main {

    val accessGroupsConfig: AccessGroupsConfig

    fun getDataFolder(): File

    fun logWarning(msg: String)

}