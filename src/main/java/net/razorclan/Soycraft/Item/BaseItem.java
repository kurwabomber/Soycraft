package net.razorclan.Soycraft.Item;

import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class BaseItem {
    public String id;
    public String displayName;
    public String itemBase;
    public HashMap<String, Object> attributes;

    public ItemStack instantiate(){
        ItemStack newItem = new ItemStack(Material.valueOf(itemBase));
        ItemMeta meta = newItem.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Main.itemIDKey, PersistentDataType.STRING, id);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        newItem.setItemMeta(meta);

        Bukkit.getLogger().info("Display Name: " + displayName + " | Item Base: " + itemBase);
        return newItem;
    }
}
