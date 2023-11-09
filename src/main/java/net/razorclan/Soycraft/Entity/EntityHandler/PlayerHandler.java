package net.razorclan.Soycraft.Entity.EntityHandler;

import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class PlayerHandler extends EntityHandler implements Listener  {


    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent e) {
        Main.playerMap.get(e.getPlayer().getUniqueId()).health = Main.playerMap.get(e.getPlayer().getUniqueId()).maxHealth;
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event){
        event.setCancelled(false);
    }
}
