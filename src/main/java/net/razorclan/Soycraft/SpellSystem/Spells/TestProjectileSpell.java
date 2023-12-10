package net.razorclan.Soycraft.SpellSystem.Spells;

import net.razorclan.Soycraft.Entity.Projectile.ProjectileClasses.AdminBeam;
import net.razorclan.Soycraft.Entity.Projectile.ProjectileEntity;
import net.razorclan.Soycraft.SpellSystem.Spell;
import org.bukkit.entity.LivingEntity;

public class TestProjectileSpell extends Spell {
    public void onCast(LivingEntity entity){
        super.onCast(entity);
        ProjectileEntity newProj = new AdminBeam(entity, false, entity.getEyeLocation(), 8.0, 0.0, 0.7, 40);
    }
}
