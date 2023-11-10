package net.razorclan.Soycraft.Entity.EntityHandler;

import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.*;

public class PlayerHandler extends EntityHandler implements Listener  {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));

        PlayerInfo info = new PlayerInfo();
        Main.entityMap.put(p.getUniqueId(), info);
        ((PlayerInfo)Main.entityMap.get(p.getUniqueId())).updatePlayerStats();
    }
    @EventHandler
    public void onplayerRespawn(PlayerRespawnEvent e) {
        Main.entityMap.get(e.getPlayer().getUniqueId()).health = Main.entityMap.get(e.getPlayer().getUniqueId()).maxHealth;
        ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).mana = 0;
    }

    @EventHandler
    public void onInventoryChange(InventoryClickEvent e) {
        ((PlayerInfo)Main.entityMap.get(e.getWhoClicked().getUniqueId())).updatePlayerStats();
    }
    @EventHandler
    public void onPlayerSwapItem(PlayerSwapHandItemsEvent e) {
        ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats();
    }
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event){
        event.setCancelled(false);
    }

    @EventHandler
    public void onAnimationEvent(PlayerAnimationEvent e) {
        if(e.getAnimationType() == PlayerAnimationType.ARM_SWING)
            leftClick(e.getPlayer());
    }

    public void leftClick(Player p) {
        p.sendMessage("NOOOOBBB");
    }

}
