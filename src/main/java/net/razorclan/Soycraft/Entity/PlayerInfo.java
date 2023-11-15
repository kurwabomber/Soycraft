package net.razorclan.Soycraft.Entity;

import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerInfo extends MobInfo {

    public double healthRegen;
    public double mana;
    public double maxMana;
    public boolean isSwitchingItem;
    public PlayerInfo() {
        healthRegen = 1;
        health = 20;
    }


    public static void regen(final Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()){
                    UUID id = p.getUniqueId();
                    Main.entityMap.get(id).health =  Math.min(Main.entityMap.get(id).health + ((PlayerInfo)Main.entityMap.get(id)).healthRegen, Main.entityMap.get(id).maxHealth);
                    ((PlayerInfo) Main.entityMap.get(id)).mana =  Math.min(((PlayerInfo) Main.entityMap.get(id)).maxMana/20 + ((PlayerInfo) Main.entityMap.get(id)).mana, ((PlayerInfo) Main.entityMap.get(id)).maxMana);
                   }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20);
    }

    public void updatePlayerStats() {
        maxHealth = 19 + strength;
        maxMana = 19 + intelligence;
    }
}
