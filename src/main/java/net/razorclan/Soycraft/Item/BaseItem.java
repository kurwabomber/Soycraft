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

        for(String key : attributes.keySet()){
            if(key.contains("Damage")){
                description.add(IridiumColorAPI.process("<SOLID:eb8023>Damage Stats:"));
                addLoreItem("physicalDamage", "<SOLID:2380eb>↳ ⚔ Physical Damage: §6%.0f", description);
                addLoreItem("blastDamage", "<SOLID:2380eb>↳ ⚠ Blast Damage: §6%.0f", description);
                addLoreItem("magicDamage", "<SOLID:2380eb>↳ ✦ Magic Damage: §6%.0f", description);
                addLoreItem("witherDamage", "<SOLID:2380eb>↳ \uD83C\uDFF5 Wither Damage: §6%.0f", description);
                addLoreItem("fireDamage", "<SOLID:2380eb>↳ \uD83D\uDD25 Fire Damage: §6%.0f", description);
                addLoreItem("waterDamage", "<SOLID:2380eb>↳ \uD83C\uDF0A Water Damage: §6%.0f", description);
                break;
            }
        }
        for(String key : attributes.keySet()){
            if(key.contains("Bonus")){
                description.add(IridiumColorAPI.process("<SOLID:eb8023>Stat Modifiers:"));
                addLoreItem("strengthBonus", "<SOLID:2380eb>↳ \uD83D\uDCAA Strength: §6%.0f", description);
                addLoreItem("dexterityBonus", "<SOLID:2380eb>↳ ⑊ Dexterity: §6%.0f", description);
                addLoreItem("vitalityBonus", "<SOLID:2380eb>↳ ❤ Vitality: §6%.0f", description);
                addLoreItem("enduranceBonus", "<SOLID:2380eb>↳ \uD83D\uDEE1 Endurance: §6%.0f", description);
                addLoreItem("intelligenceBonus", "<SOLID:2380eb>↳ ✦ Intelligence: §6%.0f", description);
                addLoreItem("wisdomBonus", "<SOLID:2380eb>↳ ✦ Wisdom: §6%.0f", description);
                break;
            }
        }
        addLoreItem("attackSpeed", "<SOLID:2380eb>⚔ Attack Speed: §6%.1f attacks/s", description);
        addLoreItem("attackRange", "<SOLID:2380eb>⚔ Attack Range: §6%.1f blocks", description);

        description.add(IridiumColorAPI.process(itemLore));
        return description;
    }
    private void addLoreItem(String key, String format, List<String> description){
        if(attributes.containsKey(key))
            description.add(IridiumColorAPI.process(String.format(format, attributes.get(key))));
    }
}
