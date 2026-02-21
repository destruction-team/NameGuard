package one.meza.envizar.nameGuard.util

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress

fun SocketAddress.toInetAddress(): InetAddress? =
    (this as? InetSocketAddress)?.address