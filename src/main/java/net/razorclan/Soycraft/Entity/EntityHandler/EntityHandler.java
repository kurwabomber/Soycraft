package net.razorclan.Soycraft.Entity.EntityHandler;

import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.util.EulerAngle;

import java.util.List;
import java.util.UUID;

public class EntityHandler implements Listener {
    // TO-DO: Find stupid undeprecated method that replaces 'setCustomName()'
    public static void dealDamage(UUID uuid, double damage) {
        if(Main.entityMap.get(uuid) == null) return;
        double health = Main.entityMap.get(uuid).health;
        health -= damage;
        if(health <= 0 && Bukkit.getEntity(uuid) instanceof Damageable)
            ((Damageable) Bukkit.getEntity(uuid)).setHealth(0);
        else
            Main.entityMap.get(uuid).health = health;
    }
    @EventHandler
    public void entityHitEvent(EntityDamageByEntityEvent e) {
        dealDamage(e.getEntity().getUniqueId(), e.getDamage());
        updateHealthHologram(e.getEntity());
        e.setDamage(0);
    }

    @EventHandler
    public void entityDamagedEvent(EntityDamageEvent e) {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) e.setCancelled(true); //cancel fall damage
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if(!(e.getEntity() instanceof Player || e.getEntity() instanceof ArmorStand)) // currently every entity not one of these. lol.
            addHealthHologram(e.getEntity());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e ) {
        if(!(e.getEntity() instanceof Player)) {
            List<Entity> passengers = e.getEntity().getPassengers();
            if(!passengers.isEmpty()) //remove health hologram
                for(Entity ent : passengers)
                    if(ent instanceof ArmorStand)
                        ent.remove();
        }

    }
    private void addHealthHologram(Entity e) {
        ArmorStand hologram = (ArmorStand) (e.getWorld().spawnEntity(e.getLocation(), EntityType.ARMOR_STAND));
        hologram.setVisible(false);
        hologram.setBasePlate(false);
        hologram.setCollidable(false);
        hologram.setArms(false);
        hologram.setSmall(true);
        hologram.setSilent(true);
        hologram.setCanPickupItems(false);
        hologram.setGliding(true);
        hologram.setLeftLegPose(EulerAngle.ZERO.add(180, 0, 0));
        hologram.setRightLegPose(EulerAngle.ZERO.add(180, 0, 0));
        hologram.setInvulnerable(true);
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        e.addPassenger(hologram);
        updateHealthHologram(e);
    }

    private void updateHealthHologram(Entity e) {
        List<Entity> passengers = e.getPassengers();
        if(!passengers.isEmpty())
            for(Entity ent : passengers)
                if(ent instanceof ArmorStand)
                    ent.setCustomName("Health: " + Math.round(Main.entityMap.get(e.getUniqueId()).health) + " / " + Math.round(Main.entityMap.get(e.getUniqueId()).maxHealth));

    }
}
