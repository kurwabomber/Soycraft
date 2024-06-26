package net.razorclan.Soycraft.Entity.EntityHandler;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

public class EntityHandler implements Listener {
    public static void dealDamage(Entity victim, Entity attacker, HashMap<String,Double> damage) {
        if(!(victim instanceof LivingEntity) )
            return;

        UUID uuid = victim.getUniqueId();
        if(Main.entityMap.get(uuid) == null)
            return;

        if(damage.isEmpty())
            return;

        double health = Main.entityMap.get(uuid).health;
        double damageDealt = 0;
        for(String key : damage.keySet()){
            double dmg = damage.get(key);
            if(dmg <= 0)
                continue;

            health -= dmg;
            damageDealt += dmg;
        }

        spawnIndicator(((LivingEntity) victim).getEyeLocation().subtract(0.0,0.5,0.0), IridiumColorAPI.process(String.format("<SOLID:d60000>⚔ %.0f ", damageDealt)), victim);
        Main.entityMap.get(uuid).health = Math.max(health, 0);

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
            if (Main.entityMap.containsKey(damager.getUniqueId()))
                dealDamage(e.getEntity(), damager, Main.entityMap.get(damager.getUniqueId()).getDamageDealt());
            e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation().add(0.0,1.5,0.0), 9);
            e.setDamage(0);
            ((LivingEntity)e.getEntity()).setNoDamageTicks(0);
        }
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if(e.getEntityType() != EntityType.ARMOR_STAND ) {
            if (!Main.entityMap.containsKey(e.getEntity().getUniqueId()))
                Main.entityMap.put(e.getEntity().getUniqueId(), new MobInfo());
            addHealthHologram(e.getEntity());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        if(ent instanceof Player) return;

        UUID uuid = ent.getUniqueId();
        if(Main.entityMap.containsKey(ent.getUniqueId())) {
            if(Main.entityMap.get(uuid).hologram != null && Main.entityMap.get(uuid).hologram.isValid()) {
                Main.entityMap.get(uuid).hologram.remove();
            }
            Main.entityMap.remove(uuid);
        }
    }

    public static void entityMapGarbageCollection(){
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<UUID> IDList = new ArrayList<>();
                Bukkit.getWorlds().forEach(world ->  world.getEntities().forEach(entity -> IDList.add(entity.getUniqueId()) ) );
                Main.entityMap.entrySet().removeIf(e -> !IDList.contains(e.getKey()));
            }
        }.runTaskTimer(Main.instance, 20L, 20L);
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
        hologram.setPersistent(true);
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
    public static void spawnIndicator(Location loc, String format, Entity en) {
        loc.add(Math.random()-0.5,Math.random()-0.5,Math.random()-0.5);
        //armorStand :D
        ArmorStand hologram = (ArmorStand) en.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
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
        hologram.setMarker(true);
        hologram.setCustomNameVisible(true);
        hologram.setCustomName(format);
        hologram.setGravity(false);
        hologram.setPersistent(true);
        new BukkitRunnable(){public void run(){
            if(hologram.canTick()) hologram.remove();
        }}.runTaskLater(Main.instance, 15);

    }
}
