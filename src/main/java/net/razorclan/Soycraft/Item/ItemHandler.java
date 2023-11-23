package net.razorclan.Soycraft.Item;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.razorclan.Soycraft.Main;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemHandler {
    public static void validateItem(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(!container.has(Main.itemIDKey, PersistentDataType.STRING)) return;

        String itemID = container.get(Main.itemIDKey, PersistentDataType.STRING);
        if(!Main.configItemMap.containsKey(itemID)) return;

        BaseItem data = Main.configItemMap.get(itemID);
        meta.setDisplayName(IridiumColorAPI.process(data.displayName));
        meta.setLore(data.getItemDescription());

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        item.setItemMeta(meta);
    }
    public static Object getAttribute(ItemStack item, String attribute, Object defaultValue){
        if(item == null)
            return defaultValue;

        ItemMeta meta = item.getItemMeta();
        if(meta == null)
            return defaultValue;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container == null || !container.has(Main.itemIDKey))
            return defaultValue;

        String itemID = container.get(Main.itemIDKey, PersistentDataType.STRING);
        BaseItem itemInfo = Main.configItemMap.get(itemID);

        return itemInfo.attributes.getOrDefault(attribute, defaultValue);
    }
}
