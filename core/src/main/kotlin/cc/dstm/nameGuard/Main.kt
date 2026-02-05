package cc.dstm.nameGuard

import cc.dstm.nameGuard.config.BlockedConfig
import cc.dstm.nameGuard.config.WhitelistConfig
import java.io.File

interface Main {

    val blockedConfig: BlockedConfig
    val whitelistConfig: WhitelistConfig

    fun getDataFolder(): File

    fun logWarning(msg: String)

}