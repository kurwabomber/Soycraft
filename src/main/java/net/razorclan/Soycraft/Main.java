package net.razorclan.Soycraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.razorclan.Soycraft.Commands.GiveItem.GiveItemCompletion;
import net.razorclan.Soycraft.Commands.GiveItem.GiveItemExecutor;
import net.razorclan.Soycraft.Entity.EntityHandler.EntityHandler;
import net.razorclan.Soycraft.Entity.EntityHandler.PlayerHandler;
import net.razorclan.Soycraft.Entity.MobInfo;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.HUD.HUDTimer;
import net.razorclan.Soycraft.Item.BaseItem;
import net.razorclan.Soycraft.Item.ItemHandler;
import net.razorclan.Soycraft.Item.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Main extends JavaPlugin implements Listener {
    public static final HashMap<UUID, MobInfo> entityMap = new HashMap<>();
    public static Plugin instance;
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Map<String, BaseItem> configItemMap = new HashMap<>();
    public static final HashMap<String, ItemStack> itemMap = new HashMap<>();
    public static NamespacedKey itemIDKey;
    private File configItemFile;
    @Override
    public void onEnable() {
        instance = this;

        itemIDKey = new NamespacedKey(this, "itemID");
        configItemFile = new File(getDataFolder(), "items.json");
        if (!configItemFile.exists())
            saveResource(configItemFile.getName(), false);

        Type type = new TypeToken<Map<String, BaseItem>>(){}.getType();
        try {
            configItemMap = gson.fromJson(new FileReader(configItemFile), type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for(String key : configItemMap.keySet()){
            BaseItem item = configItemMap.get(key);
            item.id = key;
            itemMap.put(key, item.instantiate());
            Bukkit.getLogger().info("Soyblock | Loaded in " + key);
        }
        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getPluginManager().registerEvents(new MenuItem(), this);
        Bukkit.getPluginManager().registerEvents(new EntityHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
        HUDTimer.run(this);
        PlayerInfo.regen(this);

        this.getCommand("sbgive").setExecutor(new GiveItemExecutor());
        this.getCommand("sbgive").setTabCompleter(new GiveItemCompletion());

        for(World world : Bukkit.getServer().getWorlds()) {
            for (Entity ent : world.getEntities()) {
                if (ent instanceof ArmorStand) continue;
                entityMap.put(ent.getUniqueId(), new MobInfo());
                EntityHandler.addHealthHologram(ent);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) { //straight lollin'
        Player p = e.getPlayer();
        p.setHealth(p.getMaxHealth());

        for(ItemStack item : p.getInventory()){
            if(item == null) continue;

            ItemHandler.validateItem(item);
        }
    }
    public static Vector getRightVector(Location loc){Location temp = loc.clone();temp.setYaw(temp.getYaw()+90.0F); return temp.getDirection();}
    public static Vector getUpVector(Location loc){Location temp = loc.clone();temp.setPitch(temp.getPitch()-90.0F); return temp.getDirection();}
}