package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.RayTraceResult;

import java.util.UUID;
import java.util.function.Predicate;

public class PlayerHandler implements Listener  {

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
    public void onPlayerAttack(PlayerAttackEntityCooldownResetEvent e) {
        leftClickAttack(e.getPlayer());
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction().isLeftClick())
            leftClickAttack(e.getPlayer());
    }

    public static void leftClickAttack(Player p) {
        UUID id = p.getUniqueId();
        RayTraceResult trace = hitRayTrace(p);
        if(trace != null && trace.getHitEntity() != null) {
            Bukkit.getServer().getPluginManager().callEvent(new EntityDamageByEntityEvent(p, trace.getHitEntity(), EntityDamageEvent.DamageCause.CUSTOM, 2));
        }
    }

    public static RayTraceResult hitRayTrace(LivingEntity ent) {
        Location loc = ent.getEyeLocation();
        Predicate<Entity> filter = e -> (!(e instanceof Player) && e instanceof LivingEntity && !e.isDead() && !(e instanceof ArmorStand));
        return ent.getWorld().rayTrace(loc, loc.getDirection(), 15.0, FluidCollisionMode.NEVER, true, 0.5, filter);
    }
}
