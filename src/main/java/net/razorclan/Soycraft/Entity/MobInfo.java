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
    //Damage Stats
    //Physical Damage
    public double damagePhysical, damageSlash, damageImpaling, damageCrushing;
    //Blast Damage
    public double damageBlast, damageExplosive, damageImplosion;
    //Magic Damage
    public double damageMagic, damageLightning, damageDarkness, damagePoison, damageWither;
    //Ender Damage
    public double damageEnder, damagePhasing, damageVoid;
    //Fire Damage
    public double damageFire, damageInfernal, damageCombustion;
    //Water Damage
    public double damageWater, damageDrowning, damageChanneling, damageFrost, damageErosion;
    public ArmorStand hologram;

    public MobInfo() {
        health = 20;
        maxHealth = 20;
        strength = dexterity = vitality = endurance = intelligence = wisdom =
        damagePhysical = damageSlash = damageImpaling = damageCrushing =
        damageBlast = damageExplosive = damageImplosion =
        damageMagic = damageLightning = damageDarkness = damagePoison = damageWither =
        damageEnder = damagePhasing = damageVoid =
        damageFire = damageInfernal = damageCombustion =
        damageWater = damageDrowning = damageChanneling = damageFrost = damageErosion = 0;
        hologram = null;
    }
    public void updateMobStats(LivingEntity mob){
        strength = dexterity = vitality = endurance = intelligence = wisdom =
        damagePhysical = damageSlash = damageImpaling = damageCrushing =
        damageBlast = damageExplosive = damageImplosion =
        damageMagic = damageLightning = damageDarkness = damagePoison = damageWither =
        damageEnder = damagePhasing = damageVoid =
        damageFire = damageInfernal = damageCombustion =
        damageWater = damageDrowning = damageChanneling = damageFrost = damageErosion = 0;

        for (ItemStack armor : mob.getEquipment().getArmorContents()) {
            if (armor == null) continue;
            addAllStatBoostsFromItem(armor);
        }
        ItemStack itemHeld = mob.getEquipment().getItemInMainHand();
        addAllStatBoostsFromItem(itemHeld);

        maxHealth = 20 + Math.sqrt(vitality)*8.0;
        maxMana = 20 + Math.sqrt(wisdom)*8.0;
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
        damageSlash += ((Number) ItemHandler.getAttribute(item, "slashDamage", 0)).doubleValue();
        damageImpaling += ((Number) ItemHandler.getAttribute(item, "impalingDamage", 0)).doubleValue();
        damageCrushing += ((Number) ItemHandler.getAttribute(item, "crushingDamage", 0)).doubleValue();
        damageBlast += ((Number) ItemHandler.getAttribute(item, "blastDamage", 0)).doubleValue();
        damageExplosive += ((Number) ItemHandler.getAttribute(item, "explosiveDamage", 0)).doubleValue();
        damageImplosion += ((Number) ItemHandler.getAttribute(item, "implosionDamage", 0)).doubleValue();
        damageMagic += ((Number) ItemHandler.getAttribute(item, "magicDamage", 0)).doubleValue();
        damageLightning += ((Number) ItemHandler.getAttribute(item, "lightningDamage", 0)).doubleValue();
        damageDarkness += ((Number) ItemHandler.getAttribute(item, "darknessDamage", 0)).doubleValue();
        damagePoison += ((Number) ItemHandler.getAttribute(item, "poisonDamage", 0)).doubleValue();
        damageWither += ((Number) ItemHandler.getAttribute(item, "witherDamage", 0)).doubleValue();
        damageEnder += ((Number) ItemHandler.getAttribute(item, "enderDamage", 0)).doubleValue();
        damagePhasing += ((Number) ItemHandler.getAttribute(item, "phasingDamage", 0)).doubleValue();
        damageVoid += ((Number) ItemHandler.getAttribute(item, "voidDamage", 0)).doubleValue();
        damageFire += ((Number) ItemHandler.getAttribute(item, "fireDamage", 0)).doubleValue();
        damageInfernal += ((Number) ItemHandler.getAttribute(item, "infernalDamage", 0)).doubleValue();
        damageCombustion += ((Number) ItemHandler.getAttribute(item, "combustionDamage", 0)).doubleValue();
        damageWater += ((Number) ItemHandler.getAttribute(item, "waterDamage", 0)).doubleValue();
        damageDrowning += ((Number) ItemHandler.getAttribute(item, "drowningDamage", 0)).doubleValue();
        damageChanneling += ((Number) ItemHandler.getAttribute(item, "channelingDamage", 0)).doubleValue();
        damageFrost += ((Number) ItemHandler.getAttribute(item, "frostDamage", 0)).doubleValue();
        damageErosion += ((Number) ItemHandler.getAttribute(item, "erosionDamage", 0)).doubleValue();
    }
    public HashMap<String, Double> getDamageDealt(){
        HashMap<String, Double> damageMap = new HashMap<>();

        //Add the damage scalings later.
        if(damagePhysical > 0) damageMap.put("physicalDamage", damagePhysical);
        if(damageSlash > 0) damageMap.put("slashDamage", damageSlash);
        if(damageImpaling > 0) damageMap.put("impalingDamage", damageImpaling);
        if(damageCrushing > 0) damageMap.put("crushingDamage", damageCrushing);
        if(damageBlast > 0) damageMap.put("blastDamage", damageBlast);
        if(damageExplosive > 0) damageMap.put("explosiveDamage", damageExplosive);
        if(damageImplosion > 0) damageMap.put("implosionDamage", damageImplosion);
        if(damageMagic > 0) damageMap.put("magicDamage", damageMagic);
        if(damageLightning > 0) damageMap.put("lightningDamage", damageLightning);
        if(damageDarkness > 0) damageMap.put("darknessDamage", damageDarkness);
        if(damagePoison > 0) damageMap.put("poisonDamage", damagePoison);
        if(damageWither > 0) damageMap.put("witherDamage", damageWither);
        if(damageEnder > 0) damageMap.put("enderDamage", damageEnder);
        if(damagePhasing > 0) damageMap.put("phasingDamage", damagePhasing);
        if(damageVoid > 0) damageMap.put("voidDamage", damageVoid);
        if(damageFire > 0) damageMap.put("fireDamage", damageFire);
        if(damageInfernal > 0) damageMap.put("infernalDamage", damageInfernal);
        if(damageCombustion > 0) damageMap.put("combustionDamage", damageCombustion);
        if(damageWater > 0) damageMap.put("waterDamage", damageWater);
        if(damageDrowning > 0) damageMap.put("drowningDamage", damageDrowning);
        if(damageChanneling > 0) damageMap.put("channelingDamage", damageChanneling);
        if(damageFrost > 0) damageMap.put("frostDamage", damageFrost);
        if(damageErosion > 0) damageMap.put("erosionDamage", damageErosion);

        return damageMap;
    }
}
