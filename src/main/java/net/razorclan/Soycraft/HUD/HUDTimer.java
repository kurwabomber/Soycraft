package net.razorclan.Soycraft.HUD;

import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class HUDTimer {
    public static void run(final Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()){
                    UUID id = p.getUniqueId();

                    if(!Main.entityMap.containsKey(id))
                        continue;

                    p.sendActionBar(Component.text( "Health: " + Main.entityMap.get(id).health + "/" + Main.entityMap.get(id).maxHealth  + " | Mana: " + ((PlayerInfo)Main.entityMap.get(id)).mana + "/" + ((PlayerInfo)Main.entityMap.get(id)).maxMana));
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 1);
    }
}
