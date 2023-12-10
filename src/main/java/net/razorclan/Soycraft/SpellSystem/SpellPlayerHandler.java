package net.razorclan.Soycraft.SpellSystem;

import org.bukkit.entity.LivingEntity;

public class SpellPlayerHandler {
    public static void castSpell(LivingEntity entity, int slot){
        Spell tempSpell = new Spell();
        tempSpell.manaCost = 0;
        tempSpell.name = "SPLOODGE";
        tempSpell.onCast(entity);
    }
}
