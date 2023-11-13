package net.razorclan.Soycraft.Entity;

import org.bukkit.entity.ArmorStand;

public class MobInfo {
    public double strength;
    public double intelligence;

    public double maxHealth;
    public double health;
    public ArmorStand hologram;



    public MobInfo() {
        health = 10;
        maxHealth = 10;
        intelligence = 1;
        strength = 1;
        hologram = null;
    }
}
