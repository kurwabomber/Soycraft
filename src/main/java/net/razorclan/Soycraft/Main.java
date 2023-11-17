package net.razorclan.Soycraft;

import net.razorclan.Soycraft.Entity.EntityHandler.EntityHandler;
import net.razorclan.Soycraft.Entity.EntityHandler.PlayerHandler;
import net.razorclan.Soycraft.Entity.MobInfo;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.HUD.HUDTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;


public class Main extends JavaPlugin implements Listener {
    public static final HashMap<UUID, MobInfo> entityMap = new HashMap<>();
    public static Plugin instance;
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getPluginManager().registerEvents(new EntityHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
        HUDTimer.run(this);
        PlayerInfo.regen(this);


        for(World world : Bukkit.getServer().getWorlds()) {
            for (Entity ent : world.getEntities()) {
                if (ent instanceof ArmorStand) continue;
                entityMap.put(ent.getUniqueId(), new MobInfo());
                EntityHandler.addHealthHologram(ent);
            }
        }
    }
    public static Vector getRightVector(Location loc){Location temp = loc.clone();temp.setYaw(temp.getYaw()+90.0F); return temp.getDirection();}
    public static Vector getUpVector(Location loc){Location temp = loc.clone();temp.setPitch(temp.getPitch()-90.0F); return temp.getDirection();}
}