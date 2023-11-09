package net.razorclan.Soycraft;

import net.kyori.adventure.text.Component;

import net.razorclan.Soycraft.Entity.EntityHandler.EntityHandler;
import net.razorclan.Soycraft.Entity.EntityHandler.PlayerHandler;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.HUD.HUDTimer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    public static HashMap<UUID, PlayerInfo> playerMap = new HashMap<>();
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getPluginManager().registerEvents(new EntityHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
        HUDTimer.run(this);
        PlayerInfo.regen(this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));

        PlayerInfo info = new PlayerInfo();
        playerMap.put(p.getUniqueId(), info);
    }
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event){
        event.setCancelled(false);
    }
}