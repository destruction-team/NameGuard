package cc.dstm.nameGuard.types

import cc.dstm.nameGuard.Main
import java.net.InetAddress

data class ConnectionContext(
    val name: String,
    val ip: InetAddress?,
    val virtualHost: String,
    val plugin: Main
)