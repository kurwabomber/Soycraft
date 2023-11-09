package net.razorclan.Soycraft.Entity;

import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerInfo extends MobInfo {

    public double healthRegen;
    public PlayerInfo() {
        updatePlayerInfo();
    }
    public void updatePlayerInfo() { // give player stats based off of item bonuses
        health = 20;
        healthRegen = 1;
        maxHealth = 20;
        dexterity = 1;
        agility = 1;
        intelligence = 1;
        strength = 1;
        mana = 20;
        maxMana = 20;
    }


    public static void regen(final Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()){
                    UUID id = p.getUniqueId();
                    Main.playerMap.get(id).health =  Math.min(Main.playerMap.get(id).health + Main.playerMap.get(id).healthRegen, Main.playerMap.get(id).maxHealth);
                    Main.playerMap.get(id).mana =  Math.min(Main.playerMap.get(id).mana*1.1, Main.playerMap.get(id).maxMana);
                   }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20);
    }
}
