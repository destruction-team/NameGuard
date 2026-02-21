package one.meza.envizar.nameGuard.types

import one.meza.envizar.nameGuard.Main
import java.net.InetAddress

data class ConnectionContext(
    val name: String,
    val ip: InetAddress?,
    val virtualHost: String,
    val plugin: Main
)