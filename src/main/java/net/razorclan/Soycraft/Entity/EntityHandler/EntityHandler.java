package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

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
        StringBuilder builder = new StringBuilder();
        builder.append("-");
        for(String key : damage.keySet()){
            double dmg = damage.get(key);
            if(dmg <= 0)
                continue;

            switch(key){
                case "physicalDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:d60000>⚔ %.0f ", dmg)));
                case "slashDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:d66b00>⑊ %.0f ", dmg)));
                case "impalingDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:dba400>↗ %.0f ", dmg)));
                case "crushingDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:db0050>\uD83D\uDF8B %.0f ", dmg)));
                case "blastDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:db028f>⚠ %.0f ", dmg)));
                case "explosiveDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:d4db02>⚠ %.0f ", dmg)));
                case "implosionDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:d60000>⚠ %.0f ", dmg)));
                case "magicDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:02acdb>✦ %.0f ", dmg)));
                case "lightningDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:ede905>\uD83D\uDDF2 %.0f ", dmg)));
                case "darknessDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:474747>☁ %.0f ", dmg)));
                case "poisonDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:0b9401>\uD83E\uDE78 %.0f ", dmg)));
                case "witherDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:242424>\uD83C\uDFF5 %.0f ", dmg)));
                case "enderDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:6d00d4>\uD83E\uDFBF %.0f ", dmg)));
                case "phasingDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:b800d4>\uD83E\uDFBF %.0f ", dmg)));
                case "voidDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:1a1a1a>\uD83E\uDFBF %.0f ", dmg)));
                case "fireDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:f22e02>\uD83D\uDD25 %.0f ", dmg)));
                case "infernalDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:f27202>\uD83D\uDF02 %.0f ", dmg)));
                case "combustionDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:f27202>\uD83E\uDDE8 %.0f ", dmg)));
                case "waterDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:026af2>\uD83C\uDF0A %.0f ", dmg)));
                case "drowningDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:026af2>\uD83D\uDF04 %.0f ", dmg)));
                case "channelingDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:026af2>䷜ %.0f ", dmg)));
                case "frostDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:5465ff>❄ %.0f ", dmg)));
                case "erosionDamage"-> builder.append(IridiumColorAPI.process(String.format("<SOLID:5465ff>\uD83C\uDF27 %.0f ", dmg)));
            }
            health -= dmg;
        }
        spawnIndicator(((LivingEntity) victim).getEyeLocation().subtract(0.0,0.5,0.0), builder.toString(), victim);
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
            //dealDamage(e.getEntity(), damager, e.getDamage());
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
        new BukkitRunnable(){public void run(){
            hologram.remove();
        }}.runTaskLater(Main.instance, 15);

    }
}
