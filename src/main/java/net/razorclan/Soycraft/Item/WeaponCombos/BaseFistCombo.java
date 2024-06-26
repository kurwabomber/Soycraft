package net.razorclan.Soycraft.Item.WeaponCombos;

import net.razorclan.Soycraft.Entity.EntityHandler.EntityHandler;
import net.razorclan.Soycraft.Entity.EntityHandler.PlayerHandler;
import net.razorclan.Soycraft.Main;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class BaseFistCombo {
    public static void onUse(Player p){
        p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_HIT, 1.0F, 1.0F);
        Location loc = p.getEyeLocation();
        Vector forward = loc.getDirection();
        Vector right = Main.getRightVector(loc);
        Vector up = Main.getUpVector(loc);

        for (int i = -9; i < 13; ++i) {
            final int finalI = i;
            new BukkitRunnable(){
                public void run(){
                    double fwdFactor = finalI < 0 ? (finalI*0.2)*(finalI*0.2) : (finalI*0.1)*(finalI*0.1);
                    for(int j = 0; j < 9;j++) {
                        Vector tmpForward = forward.clone().multiply(fwdFactor + j*0.2 - 3.5).multiply(-1);
                        Vector tmpRight = right.clone().multiply( 0.3 - (finalI * 0.06) );
                        Vector tmpUp = up.clone().multiply(-0.2);

                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(80+15*j, 150+8*j, 240+j), 1.0F);
                        Vector finalVec = new Vector();
                        finalVec.add(loc.clone().toVector());

                        finalVec.add(tmpForward);
                        finalVec.add(tmpRight);
                        finalVec.add(tmpUp);
                        p.spawnParticle(Particle.REDSTONE, finalVec.toLocation(p.getWorld()), 0, dustOptions);
                    }
                }
            }.run();//.runTaskLaterAsynchronously(Main.instance, (19 - i) / 4);
        }

        RayTraceResult trace = PlayerHandler.hitRayTrace(p, 3.0);
        if(trace != null && trace.getHitEntity() != null) {
            EntityHandler.dealDamage(trace.getHitEntity(), p, Main.entityMap.get(p.getUniqueId()).getDamageDealt());
        }
    }
}
