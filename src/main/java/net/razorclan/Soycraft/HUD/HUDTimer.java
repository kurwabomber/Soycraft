package net.razorclan.Soycraft.HUD;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
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
                    if(!Main.playerMap.containsKey(id))
                        continue;

                    p.sendActionBar(Component.text("Yeah it works. " + Main.playerMap.get(id).health + " Health."));
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 1);
    }
}
