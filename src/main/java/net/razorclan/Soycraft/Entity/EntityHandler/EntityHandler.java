package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.MobInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class EntityHandler implements Listener {
    public static void dealDamage(Entity damagee, Entity damager, double damage) {
        UUID uuid = damagee.getUniqueId();
        if(Main.entityMap.get(uuid) == null) { return; }
        double health = Main.entityMap.get(uuid).health;
        health -= damage;
        Bukkit.getLogger().info("Hit entity for: " + damage);
        if(health <= 0 && Bukkit.getEntity(uuid) instanceof Damageable)
            ((Damageable)(Objects.requireNonNull(Bukkit.getEntity(uuid)))).setHealth(0);
        else
            Main.entityMap.get(uuid).health = health;
        updateHealthHologram(damagee);
    }
    @EventHandler
    public void entityHitEvent(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            e.setDamage(0);
            return;
        }
        dealDamage(e.getEntity(), e.getDamager(), e.getDamage());
        e.setDamage(0);
    }

    @EventHandler
    public void entityDamagedEvent(EntityDamageEvent e) {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) e.setCancelled(true); //cancel fall damage
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if(e.getEntityType() != EntityType.ARMOR_STAND) {
            if (!Main.entityMap.containsKey(e.getEntity().getUniqueId()))
                Main.entityMap.put(e.getEntity().getUniqueId(), new MobInfo());
            addHealthHologram(e.getEntity());
        }
    }

    @EventHandler
    public void onEntityRemove(EntityRemoveFromWorldEvent e) {
        Entity ent = e.getEntity();
        if(ent instanceof Player) return;

        UUID uuid = ent.getUniqueId();

        if(Main.entityMap.containsKey(ent.getUniqueId())) {
            if(Main.entityMap.get(uuid).hologram != null)
                Main.entityMap.get(uuid).hologram.remove();
            Main.entityMap.remove(uuid);
        }
    }
    public static void addHealthHologram(Entity e) {
        if(!(e instanceof LivingEntity) || e instanceof Player) return;
        ArmorStand hologram = (ArmorStand) (e.getWorld().spawnEntity(new Location(e.getWorld(), 0, 0, 0), EntityType.ARMOR_STAND));
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
        hologram.setMarker(true);
        e.addPassenger(hologram);
        if(Main.entityMap.containsKey(e.getUniqueId())) {
            MobInfo tmp = Main.entityMap.get(e.getUniqueId());
            tmp.hologram = hologram;
            Main.entityMap.replace(e.getUniqueId(), tmp);
        }
        updateHealthHologram(e);
    }

    private static void updateHealthHologram(Entity e) {
        if(e == null) return;
        MobInfo tmpInfo = Main.entityMap.get(e.getUniqueId());
        if(tmpInfo == null || tmpInfo.hologram == null ) return;
        tmpInfo.hologram.customName(Component.text("Health: " + Math.round(Main.entityMap.get(e.getUniqueId()).health) + " / " + Math.round(Main.entityMap.get(e.getUniqueId()).maxHealth)));
    }


}
