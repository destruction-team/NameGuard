package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.types.WhitelistGroup

interface WhitelistConfig {

    abstract var groups: List<WhitelistGroup>

}