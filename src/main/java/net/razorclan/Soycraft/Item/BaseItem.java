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
        addLoreItem("slashDamage", "<SOLID:3366cc>⑊ Slash Damage: §6%.0f", description);
        addLoreItem("impalingDamage", "<SOLID:3366cc>↗ Impaling Damage: §6%.0f", description);
        addLoreItem("crushingDamage", "<SOLID:3366cc>\uD83D\uDF8B Crushing Damage: §6%.0f", description);
        addLoreItem("blastDamage", "<SOLID:3366cc>⚠ Blast Damage: §6%.0f", description);
        addLoreItem("explosiveDamage", "<SOLID:3366cc>⚠ Explosive Damage: §6%.0f", description);
        addLoreItem("implosionDamage", "<SOLID:3366cc>⚠ Implosion Damage: §6%.0f", description);
        addLoreItem("magicDamage", "<SOLID:3366cc>✦ Magic Damage: §6%.0f", description);
        addLoreItem("lightningDamage", "<SOLID:3366cc>\uD83D\uDDF2 Lightning Damage: §6%.0f", description);
        addLoreItem("darknessDamage", "<SOLID:3366cc>☁ Darkness Damage: §6%.0f", description);
        addLoreItem("poisonDamage", "<SOLID:3366cc>\uD83E\uDE78 Poison Damage: §6%.0f", description);
        addLoreItem("witherDamage", "<SOLID:3366cc>\uD83C\uDFF5 Wither Damage: §6%.0f", description);
        addLoreItem("enderDamage", "<SOLID:3366cc>\uD83E\uDFBF Ender Damage: §6%.0f", description);
        addLoreItem("phasingDamage", "<SOLID:3366cc>\uD83E\uDFBF Phasing Damage: §6%.0f", description);
        addLoreItem("voidDamage", "<SOLID:3366cc>\uD83E\uDFBF Void Damage: §6%.0f", description);
        addLoreItem("fireDamage", "<SOLID:3366cc>\uD83D\uDD25 Fire Damage: §6%.0f", description);
        addLoreItem("infernalDamage", "<SOLID:3366cc>\uD83D\uDF02 Infernal Damage: §6%.0f", description);
        addLoreItem("combustionDamage", "<SOLID:3366cc>\uD83E\uDDE8 Combustion Damage: §6%.0f", description);
        addLoreItem("waterDamage", "<SOLID:3366cc>\uD83C\uDF0A Water Damage: §6%.0f", description);
        addLoreItem("drowningDamage", "<SOLID:3366cc>\uD83D\uDF04 Drowning Damage: §6%.0f", description);
        addLoreItem("channelingDamage", "<SOLID:3366cc>䷜ Channeling Damage: §6%.0f", description);
        addLoreItem("frostDamage", "<SOLID:3366cc>❄ Frost Damage: §6%.0f", description);
        addLoreItem("erosionDamage", "<SOLID:3366cc>\uD83C\uDF27 Erosion Damage: §6%.0f", description);
        addLoreItem("attackSpeed", "<SOLID:3366cc>⚔ Attack Speed: §6%.1f attacks/s", description);
        addLoreItem("attackRange", "<SOLID:3366cc>⚔ Attack Range: §6%.1f blocks", description);
        addLoreItem("strengthBonus", "<SOLID:3366cc>\uD83D\uDCAA Strength: §6%.0f", description);
        addLoreItem("dexterityBonus", "<SOLID:3366cc>⑊ Dexterity: §6%.0f", description);
        addLoreItem("vitalityBonus", "<SOLID:3366cc>❤ Vitality: §6%.0f", description);
        addLoreItem("enduranceBonus", "<SOLID:3366cc>\uD83D\uDEE1 Endurance: §6%.0f", description);
        addLoreItem("intelligenceBonus", "<SOLID:3366cc>✦ Intelligence: §6%.0f", description);
        addLoreItem("wisdomBonus", "<SOLID:3366cc>✦ Wisdom: §6%.0f", description);


        description.add(IridiumColorAPI.process(itemLore));
        return description;
    }
    private void addLoreItem(String key, String format, List<String> description){
        if(attributes.containsKey(key))
            description.add(IridiumColorAPI.process(String.format(format, attributes.get(key))));
    }
}
