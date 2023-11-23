package net.razorclan.Soycraft.HUD;

import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

public class HUDTimer {
    public static void run(final Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()){
                    UUID id = p.getUniqueId();

                    if(!Main.entityMap.containsKey(id))
                        continue;

                    p.sendActionBar(text()
                            .append(text(String.format("❤ %.0f/%.0f",+ Main.entityMap.get(id).health, Main.entityMap.get(id).maxHealth)).color(color(219, 0, 73)))
                            .append(text(String.format(" ✦ %.0f/%.0f", ((PlayerInfo)Main.entityMap.get(id)).mana, ((PlayerInfo)Main.entityMap.get(id)).maxMana)).color(color(9, 95, 176)))
                            .build());
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 1);
    }
}
