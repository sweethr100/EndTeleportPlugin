package seml.endTeleportPlugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPortalEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EndTeleportPlugin : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        logger.info("EndTeleportPlugin enabled!")
    }

    @EventHandler
    fun onPlayerPortal(event: PlayerPortalEvent) {
        val player = event.player
        if (event.cause == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            // 엔드 차원으로 이동하는지 확인
            if (player.world.environment != org.bukkit.World.Environment.THE_END) {
                // 텔레포트 위치 설정: 엔드 차원의 (0, 100, 0)
                val endWorld = Bukkit.getWorld("world_the_end")  // 엔드 월드 이름 확인 필요
                if (endWorld != null) {
                    val targetLocation = Location(endWorld, 0.5, 150.0, 0.5)
                    event.to = targetLocation  // 이벤트에서 직접 위치 변경
                    player.teleport(targetLocation)  // 안전하게 텔레포트

                    val slowFalling = PotionEffect(PotionEffectType.SLOW_FALLING, 300, 0)
                    player.addPotionEffect(slowFalling)
                }
            }
        }
    }
}
