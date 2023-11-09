package net.razorclan.Soycraft.Entity.EntityHandler;

import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class EntityHandler implements Listener {

    public static void dealDamage(UUID uuid, double damage) {
        double health = Main.playerMap.get(uuid).health;
        health -= damage;
        if(health <= 0 && Bukkit.getEntity(uuid) instanceof Damageable)
            ((Damageable) Bukkit.getEntity(uuid)).setHealth(0);
        Main.playerMap.get(uuid).health = health;
    }
    @EventHandler
    public void entityHitEvent(EntityDamageByEntityEvent e) {

        if(!(e.getEntity() instanceof Player)) return;
        dealDamage(e.getEntity().getUniqueId(), e.getDamage());

        e.setDamage(0);
    }

    @EventHandler
    public void entityDamagedEvent(EntityDamageEvent e) {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) e.setCancelled(true); //cancel fall damage
    }
}
