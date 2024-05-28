package net.razorclan.Soycraft.Entity;

import net.razorclan.Soycraft.Item.ItemHandler;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MobInfo {
    public double strength;
    public double dexterity;
    public double vitality;
    public double endurance;
    public double intelligence;
    public double wisdom;
    public double maxHealth;
    public double health;
    public double healthRegen;
    public double mana;
    public double maxMana;
    public double stamina;
    public double maxStamina;
    //Damage Stats
    public double damagePhysical;
    public double damageBlast;
    public double damageMagic, damageWither;
    public double damageFire;
    public double damageWater;
    public ArmorStand hologram;

    public MobInfo() {
        health = 20;
        maxHealth = 20;
        mana = 20;
        maxMana = 20;
        stamina = 20;
        maxStamina = 20;
        strength = dexterity = vitality = endurance = intelligence = wisdom =
        damagePhysical = damageBlast = damageMagic = damageWither = damageFire = damageWater = 0;
        hologram = null;
    }
    public void updateMobStats(LivingEntity mob){
        strength = dexterity = vitality = endurance = intelligence = wisdom =
        damagePhysical = damageBlast = damageMagic = damageWither = damageFire = damageWater = 0;

        for (ItemStack armor : mob.getEquipment().getArmorContents()) {
            if (armor == null) continue;
            addAllStatBoostsFromItem(armor);
        }
        ItemStack itemHeld = mob.getEquipment().getItemInMainHand();
        addAllStatBoostsFromItem(itemHeld);

        damagePhysical += 5;
        maxHealth = 20 + Math.sqrt(vitality)*8.0;
        maxMana = 20 + Math.sqrt(wisdom)*8.0;
        maxStamina = 20 + Math.sqrt(endurance)*4.0;
    }
    public void addAllStatBoostsFromItem(ItemStack item){
        strength += ((Number) ItemHandler.getAttribute(item, "strengthBonus", 0)).doubleValue();
        dexterity += ((Number) ItemHandler.getAttribute(item, "dexterityBonus", 0)).doubleValue();
        vitality += ((Number) ItemHandler.getAttribute(item, "vitalityBonus", 0)).doubleValue();
        endurance += ((Number) ItemHandler.getAttribute(item, "enduranceBonus", 0)).doubleValue();
        intelligence += ((Number) ItemHandler.getAttribute(item, "intelligenceBonus", 0)).doubleValue();
        wisdom += ((Number) ItemHandler.getAttribute(item, "wisdomBonus", 0)).doubleValue();
        //Damage Stats
        damagePhysical += ((Number) ItemHandler.getAttribute(item, "physicalDamage", 0)).doubleValue();
        damageBlast += ((Number) ItemHandler.getAttribute(item, "blastDamage", 0)).doubleValue();
        damageMagic += ((Number) ItemHandler.getAttribute(item, "magicDamage", 0)).doubleValue();
        damageWither += ((Number) ItemHandler.getAttribute(item, "witherDamage", 0)).doubleValue();
        damageFire += ((Number) ItemHandler.getAttribute(item, "fireDamage", 0)).doubleValue();
        damageWater += ((Number) ItemHandler.getAttribute(item, "waterDamage", 0)).doubleValue();
    }
    public HashMap<String, Double> getDamageDealt(){
        HashMap<String, Double> damageMap = new HashMap<>();

        //Add the damage scalings later.
        if(damagePhysical > 0) damageMap.put("physicalDamage", damagePhysical);
        if(damageBlast > 0) damageMap.put("blastDamage", damageBlast);
        if(damageMagic > 0) damageMap.put("magicDamage", damageMagic);
        if(damageWither > 0) damageMap.put("witherDamage", damageWither);
        if(damageFire > 0) damageMap.put("fireDamage", damageFire);
        if(damageWater > 0) damageMap.put("waterDamage", damageWater);

        return damageMap;
    }
}
