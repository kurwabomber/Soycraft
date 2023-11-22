package net.razorclan.Soycraft.Item.WeaponCombos;

import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BaseSwordCombo {
    public static void onUse(Player p){
        p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_HIT, 1.0F, 1.0F);
        Location loc = p.getEyeLocation();
        Vector forward = loc.getDirection();
        Vector right = Main.getRightVector(loc);
        Vector up = Main.getUpVector(loc);

        if( ( ((PlayerInfo)Main.entityMap.get(p.getUniqueId())).currentCombo & 1 ) == 0 )
            right.multiply(-1);

        up.multiply( (Math.random()*2.0)-1.0 );

        for (int i = -19; i < 19; i++) {
            final int finalI = i;
            new BukkitRunnable(){
                public void run(){
                    double fwdFactor = Math.pow(Math.abs(finalI * 0.1), 2.1);
                    for(int j = 0; j < 15;j++) {
                        Vector tmpForward = forward.clone().multiply(4.0 - fwdFactor - j*0.12);
                        Vector tmpRight = right.clone().multiply(finalI * 0.15);
                        Vector tmpUp = up.clone().multiply(finalI * 0.07);
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255 - j*15, Math.max(70 + finalI*3 - j*10,0), 0), 1F);
                        Vector finalVec = new Vector();
                        finalVec.add(loc.clone().toVector());
                        finalVec.add(tmpForward);
                        finalVec.add(tmpRight);
                        finalVec.add(tmpUp);
                        p.spawnParticle(Particle.REDSTONE, finalVec.toLocation(p.getWorld()), 0, dustOptions);
                    }
                }
            }.runTaskLaterAsynchronously(Main.instance, (i + 15) / 10);
        }
    }
}
