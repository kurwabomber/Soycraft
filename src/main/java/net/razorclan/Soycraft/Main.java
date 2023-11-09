package net.razorclan.Soycraft;

import net.kyori.adventure.text.Component;
<<<<<<< Updated upstream
=======
import net.razorclan.Soycraft.Entity.EntityHandler.EntityHandler;
import net.razorclan.Soycraft.Entity.EntityHandler.PlayerHandler;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.HUD.HUDTimer;
>>>>>>> Stashed changes
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
<<<<<<< Updated upstream
=======
        Bukkit.getPluginManager().registerEvents(new EntityHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
        HUDTimer.run(this);
        PlayerInfo.regen(this);
>>>>>>> Stashed changes
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
<<<<<<< Updated upstream
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }
=======
        Player p = event.getPlayer();
        p.sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));

        PlayerInfo info = new PlayerInfo();
        playerMap.put(p.getUniqueId(), info);
    }

>>>>>>> Stashed changes
}