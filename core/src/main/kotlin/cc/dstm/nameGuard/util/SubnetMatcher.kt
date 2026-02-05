package cc.dstm.nameGuard.util

import java.net.InetAddress
import java.net.UnknownHostException

// Stolen from https://stackoverflow.com/questions/577363/how-to-check-if-an-ip-address-is-from-a-particular-network-netmask-in-java
class SubnetMatcher(val requiredAddress: InetAddress, val nMaskBits: Int) {

    companion object {

        fun create(address: String): SubnetMatcher? {
            var nMaskBits: Int
            var ipAddress = address
            if (ipAddress.indexOf('/') > 0) {
                val addressAndMask: Array<String?> =
                    ipAddress.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                ipAddress = addressAndMask[0]!!
                nMaskBits = addressAndMask[1]!!.toInt()
            } else {
                nMaskBits = -1
            }
            val requiredAddress = parseAddress(ipAddress) ?: return null

            assert(requiredAddress.address.size * 8 >= nMaskBits) {
                "IP address $ipAddress is too short for bitmask of length $nMaskBits"
            }

            return SubnetMatcher(requiredAddress, nMaskBits)
        }

        fun parseAddress(address: String): InetAddress? {
            return try {
                InetAddress.getByName(address)
            } catch (_: UnknownHostException) {
                null
            }
        }

    }

//    fun matches(address: String) = parseAddress(address)?.let { matches(it) } ?: false

    fun matches(remoteAddress: InetAddress): Boolean {

        if (requiredAddress.javaClass != remoteAddress.javaClass) return false

        if (nMaskBits < 0) return remoteAddress == requiredAddress

        val remAddr = remoteAddress.address
        val reqAddr = requiredAddress.address

        val nMaskFullBytes = nMaskBits / 8
        val finalByte = (0xFF00 shr (nMaskBits and 0x07)).toByte()

        for (i in 0..<nMaskFullBytes) {
            if (remAddr[i] != reqAddr[i]) {
                return false
            }
        }

        if (finalByte.toInt() != 0) {
            return (remAddr[nMaskFullBytes].toInt() and finalByte.toInt()) == (reqAddr[nMaskFullBytes].toInt() and finalByte.toInt())
        }

        return true
    }
}