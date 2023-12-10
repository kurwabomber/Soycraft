package net.razorclan.Soycraft.Entity;

import net.razorclan.Soycraft.HUD.HUDInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerInfo extends MobInfo {
    public int currentCombo;
    public ArrayList<HUDInfo> extraHudText;
    public PlayerInfo() {
        healthRegen = 1;
        health = 20;
        currentCombo = 0;
        extraHudText = new ArrayList<>();
    }

    public static void regen(final Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()){
                    UUID id = p.getUniqueId();
                    Main.entityMap.get(id).health =  Math.min(Main.entityMap.get(id).health + Main.entityMap.get(id).healthRegen, Main.entityMap.get(id).maxHealth);
                     Main.entityMap.get(id).mana =  Math.min(Main.entityMap.get(id).maxMana/20 +  Main.entityMap.get(id).mana,  Main.entityMap.get(id).maxMana);
                   }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20);
    }

    public void updatePlayerStats(Player p) {
        this.updateMobStats(p);
    }
}
