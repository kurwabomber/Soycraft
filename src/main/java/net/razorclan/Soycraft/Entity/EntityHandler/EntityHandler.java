package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.MobInfo;
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

import java.util.Objects;
import java.util.UUID;

public class EntityHandler implements Listener {
    public static void dealDamage(UUID uuid, double damage) {
        if(Main.entityMap.get(uuid) == null) return;
        double health = Main.entityMap.get(uuid).health;
        health -= damage;
        if(health <= 0 && Bukkit.getEntity(uuid) instanceof Damageable)
            ((Damageable) Objects.requireNonNull(Bukkit.getEntity(uuid))).setHealth(0);
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
    public void onEntityDeath(EntityDeathEvent e) {
        if(!(e.getEntity() instanceof Player))
            for(Entity ent : e.getEntity().getPassengers())
                if(ent instanceof ArmorStand)
                    ent.remove();
    }
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if(!Main.entityMap.containsKey(e.getEntity().getUniqueId()))
            Main.entityMap.put(e.getEntity().getUniqueId(), new MobInfo());
        addHealthHologram(e.getEntity());
    }

    @EventHandler
    public void onEntityRemove(EntityRemoveFromWorldEvent e) {
        if(e.getEntity() instanceof Player) return;
        Main.entityMap.remove(e.getEntity().getUniqueId());
    }
    public static void addHealthHologram(Entity e) {
        if(!(e instanceof LivingEntity) || e instanceof Player || e instanceof ArmorStand) return;
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

    private static void updateHealthHologram(Entity e) {
        if(e == null) return;
        for(Entity ent : e.getPassengers())
            if(ent instanceof ArmorStand)
                ent.customName(Component.text("Health: " + Math.round(Main.entityMap.get(e.getUniqueId()).health) + " / " + Math.round(Main.entityMap.get(e.getUniqueId()).maxHealth)));
    }
}
