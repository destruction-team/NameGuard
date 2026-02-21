package one.meza.envizar.nameGuard.config

import one.meza.envizar.nameGuard.types.AccessGroup
import one.meza.envizar.nameGuard.types.ConnectionContext

interface AccessGroupsConfig {

    var groups: List<AccessGroup>

    fun getBlockingGroup(ctx: ConnectionContext): AccessGroup? {

        for (accessGroup in groups) {

            // Если не попадает под группу - не обрабатываем
            if (accessGroup.match == null || !accessGroup.match.test(ctx.name)) continue

            // Если проходит хоть по одному правилу доступа - разрешаем подключение
            if (accessGroup.accessRules.any { it.isAllowed(ctx) }) {
                return null
            }

            // Игрок попал под группу и не был в правилах доступа - блокируем
            if (accessGroup.logToConsole) {
                ctx.plugin.logWarning("Blocked ${ctx.name}. " +
                        "(ip=${ctx.ip}; vhost=${ctx.virtualHost}, groupId=${accessGroup.id})")
            }
            return accessGroup
        }

        // Игрок не попал ни под одну группу
        return null

    }

}