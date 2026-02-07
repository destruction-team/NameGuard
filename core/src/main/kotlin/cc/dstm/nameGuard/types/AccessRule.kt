package cc.dstm.nameGuard.types

import cc.dstm.nameGuard.util.SubnetMatcher
import java.net.InetAddress

interface PatternParser<T> {
    val pattern: Regex
    fun parse(rawString: String): T?
}

sealed interface AccessRule {

    fun isAllowed(connection: ConnectionContext): Boolean

    data class IP(val address: InetAddress) : AccessRule {

        override fun isAllowed(connection: ConnectionContext) =
            connection.ip == address

        object Parser : PatternParser<IP> {
            override val pattern = Regex("""(?:ip:)?(\d+.\d+.\d+.\d+)""")
            override fun parse(rawString: String): IP? {
                val match = pattern.matchEntire(rawString) ?: return null
                return SubnetMatcher.parseAddress(match.groupValues[1])?.let { IP(it) }
            }
        }
    }

    data class Subnet(val subnet: SubnetMatcher) : AccessRule {

        override fun isAllowed(connection: ConnectionContext) =
            connection.ip?.let { ip -> subnet.matches(ip) } ?: false

        object Parser : PatternParser<Subnet> {
            override val pattern = Regex("""(?:subnet:)?(\d+.\d+.\d+.\d+)/(\d+)""")
            override fun parse(rawString: String): Subnet? {
                val match = pattern.matchEntire(rawString) ?: return null
                val ip = SubnetMatcher.parseAddress(match.groupValues[1]) ?: return null
                val nBitsMask = match.groupValues[2].toIntOrNull() ?: return null
                return Subnet(SubnetMatcher(ip, nBitsMask))
            }
        }
    }

    data class VirtualHost(val virtualHost: String) : AccessRule {

        override fun isAllowed(connection: ConnectionContext) =
            connection.virtualHost == virtualHost

        object Parser : PatternParser<VirtualHost> {
            override val pattern = Regex("""v?host:(.+)""")
            override fun parse(rawString: String): VirtualHost? {
                val match = pattern.matchEntire(rawString) ?: return null
                return VirtualHost(match.groupValues[1])
            }
        }
    }

    companion object {
        fun fromString(rawString: String): AccessRule? {
            val parsers = listOf(
                IP.Parser,
                Subnet.Parser,
                VirtualHost.Parser
            )
            return parsers.firstNotNullOfOrNull { it.parse(rawString) }
        }
    }

}