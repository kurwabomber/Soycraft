package net.razorclan.Soycraft.HUD;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
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

                    StringBuilder hudText = new StringBuilder();

                    hudText.append(IridiumColorAPI.process(String.format("<SOLID:db0049>❤ %.0f/%.0f ", Main.entityMap.get(id).health, Main.entityMap.get(id).maxHealth)));
                    hudText.append(IridiumColorAPI.process(String.format("<SOLID:095fb0>✦ %.0f/%.0f ", Main.entityMap.get(id).mana,Main.entityMap.get(id).maxMana)));
                    hudText.append(IridiumColorAPI.process(String.format("<SOLID:00cc00>❤ %.0f/%.0f ", Main.entityMap.get(id).stamina,Main.entityMap.get(id).maxStamina)));

                    ((PlayerInfo)Main.entityMap.get(id)).extraHudText.removeIf(i -> i.time < System.currentTimeMillis());

                    for(HUDInfo info : ((PlayerInfo)Main.entityMap.get(id)).extraHudText){
                        hudText.append(info.displayText).append(" ");
                    }

                    p.sendActionBar(String.valueOf(hudText));
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 1);
    }
}
