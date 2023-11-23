package net.razorclan.Soycraft.Entity;

import net.razorclan.Soycraft.Item.ItemHandler;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerInfo extends MobInfo {

    public double healthRegen;
    public double mana;
    public double maxMana;
    public int currentCombo;
    public PlayerInfo() {
        healthRegen = 1;
        health = 20;
        currentCombo = 0;
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

    public void updatePlayerStats(Player p) {
        strength = 0;
        dexterity = 0;
        vitality = 0;
        endurance = 0;
        intelligence = 0;
        wisdom = 0;

        for(ItemStack armor : p.getInventory().getArmorContents()){
            if(armor == null) continue;

            vitality += ((Number)ItemHandler.getAttribute(armor, "vitalityBonus", 0)).doubleValue();
        }

        maxHealth = 20 + Math.sqrt(vitality)*8.0;
        maxMana = 20 + Math.sqrt(wisdom)*8.0;
    }
}
