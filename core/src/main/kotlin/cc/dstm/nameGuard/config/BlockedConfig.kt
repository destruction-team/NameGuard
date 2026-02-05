package cc.dstm.nameGuard.config

import cc.dstm.nameGuard.types.BlockedGroup

interface BlockedConfig {

    var groups: List<BlockedGroup>

}