package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.MobInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

public class EntityHandler implements Listener {
    public static void dealDamage(Entity victim, Entity attacker, double damage) {
        if(!(victim instanceof LivingEntity) )
            return;

        UUID uuid = victim.getUniqueId();
        if(Main.entityMap.get(uuid) == null)
            return;

        double health = Main.entityMap.get(uuid).health;
        health -= damage;
        Main.entityMap.get(uuid).health = health;

        ((LivingEntity)victim).damage(0);
        if(health <= 0 && Bukkit.getEntity(uuid) instanceof Damageable)
            ((LivingEntity)victim).setHealth(0);

        updateHealthHologram(victim);
    }

    @EventHandler
    public void entityDamagedEvent(EntityDamageEvent e) {
        if(e.getDamage() <= 0.1) return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) e.setCancelled(true); //cancel fall damage
        if(e instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) e).getDamager();
            if(damager instanceof Player && e.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
                e.setCancelled(true);
                return;
            }
            dealDamage(e.getEntity(), damager, e.getDamage());
            e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation(), 15);
            e.setDamage(0);
            ((LivingEntity)e.getEntity()).setNoDamageTicks(0);
        }
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
        if(Main.entityMap.containsKey(e.getUniqueId()))
            Main.entityMap.get(e.getUniqueId()).hologram = hologram;
        updateHealthHologram(e);
    }

    private static void updateHealthHologram(Entity e) {
        if(e == null) return;
        MobInfo tmpInfo = Main.entityMap.get(e.getUniqueId());
        if(tmpInfo == null || tmpInfo.hologram == null ) return;
        final Component text = text()
                .append(e.name().color(color(247, 140, 0)))
                .append(text(" | "))
                .append(text(String.format(" %.0f/%.0f",Main.entityMap.get(e.getUniqueId()).health,Main.entityMap.get(e.getUniqueId()).maxHealth)).color(color(255, 58, 47) ) )
                .build();
        tmpInfo.hologram.customName(text);
    }
}
