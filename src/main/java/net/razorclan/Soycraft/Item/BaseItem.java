package net.razorclan.Soycraft.Item;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseItem {
    public String id;
    public String displayName;
    public String itemLore;
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
    public List<String> getItemDescription(){
        List<String> description = new ArrayList<String>();

        addLoreItem("physicalDamage", "<SOLID:3366cc>⚔ Physical Damage: §6%.0f", description);
        addLoreItem("implosionDamage", "<SOLID:3366cc>⚠ Implosion Damage: §6%.0f", description);
        addLoreItem("attackSpeed", "<SOLID:3366cc>⚔ Attack Speed: §6%.1f attacks/s", description);
        addLoreItem("attackRange", "<SOLID:3366cc>⚔ Attack Range: §6%.1f blocks", description);
        addLoreItem("strengthBonus", "<SOLID:3366cc>\uD83D\uDCAA Strength: §6%.0f", description);
        addLoreItem("vitalityBonus", "<SOLID:3366cc>❤ Vitality: §6%.0f", description);

        description.add(IridiumColorAPI.process(itemLore));
        return description;
    }
    private void addLoreItem(String key, String format, List<String> description){
        if(attributes.containsKey(key))
            description.add(IridiumColorAPI.process(String.format(format, attributes.get(key))));
    }
}
