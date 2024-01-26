package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Item.ItemHandler;
import net.razorclan.Soycraft.Item.WeaponCombos.BaseFistCombo;
import net.razorclan.Soycraft.Item.WeaponCombos.BaseSwordCombo;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerHandler implements Listener  {
    public static void initializePlayer(Player p){
        Main.entityMap.put(p.getUniqueId(), new PlayerInfo());
        ((PlayerInfo)Main.entityMap.get(p.getUniqueId())).updatePlayerStats(p);
    }

    @EventHandler
    public void onplayerRespawn(PlayerRespawnEvent e) {
        new BukkitRunnable(){public void run() {
            Main.entityMap.get(e.getPlayer().getUniqueId()).health = Main.entityMap.get(e.getPlayer().getUniqueId()).maxHealth;
            Main.entityMap.get(e.getPlayer().getUniqueId()).mana = 0;
        }}.runTaskLater(Main.instance, 1);
    }

    @EventHandler
    public void onInventoryChange(InventoryClickEvent e) {
        new BukkitRunnable(){public void run() {
            ((PlayerInfo)Main.entityMap.get(e.getWhoClicked().getUniqueId())).updatePlayerStats((Player) e.getWhoClicked());
        }}.runTaskLater(Main.instance, 1);
    }
    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent e) {
        new BukkitRunnable(){public void run() {
            ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
        }}.runTaskLater(Main.instance, 1);
    }
    @EventHandler
    public void onPlayerSwapItem(PlayerItemHeldEvent e) {
        new BukkitRunnable(){public void run() {
            ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
        }}.runTaskLater(Main.instance, 1);
    }
    @EventHandler
    public void onPlayerSwapHand(PlayerSwapHandItemsEvent e) {
        new BukkitRunnable(){public void run() {
            ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
        }}.runTaskLater(Main.instance, 1);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        new BukkitRunnable(){public void run() {
            ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
        }}.runTaskLater(Main.instance, 1);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        new BukkitRunnable(){public void run() {
            ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
        }}.runTaskLater(Main.instance, 1);
    }
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event){
        event.setCancelled(false);
    }

    @EventHandler
    public void onPlayerAttack(PrePlayerAttackEntityEvent e) {
        leftClickAttack(e.getPlayer());
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction().isLeftClick())
            leftClickAttack(e.getPlayer());
    }

    public static void leftClickAttack(Player p) {
        ItemStack item = p.getInventory().getItemInMainHand();
        UUID id = p.getUniqueId();

        if(item.isEmpty()){
            if(p.getAttackCooldown() == 1.0)
                BaseFistCombo.onUse(p);
        }
        else{
            if (p.hasCooldown(item.getType()))
                return;

            switch (ItemHandler.getAttribute(item, "moveset", "").toString()) {
                case "basicSword" -> {
                    BaseSwordCombo.onUse(p);
                }
            }
            p.setCooldown(p.getInventory().getItemInMainHand().getType(), (int)(20.0/(double)ItemHandler.getAttribute(item, "attackSpeed", 4.0)) );
        }
        ((PlayerInfo) Main.entityMap.get(id)).currentCombo++;
    }
}
