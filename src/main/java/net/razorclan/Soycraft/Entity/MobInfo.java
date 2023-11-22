package net.razorclan.Soycraft.Entity;

import org.bukkit.entity.ArmorStand;

public class MobInfo {
    public double strength;
    public double dexterity;
    public double vitality;
    public double endurance;
    public double intelligence;
    public double wisdom;
    public double maxHealth;
    public double health;
    public ArmorStand hologram;



    public MobInfo() {
        health = 20;
        maxHealth = 20;
        strength = 0;
        dexterity = 0;
        vitality = 0;
        endurance = 0;
        intelligence = 0;
        wisdom = 0;
        hologram = null;
    }
}
