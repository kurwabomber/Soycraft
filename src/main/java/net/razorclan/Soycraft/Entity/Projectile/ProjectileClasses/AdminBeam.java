package net.razorclan.Soycraft.Entity.Projectile.ProjectileClasses;

import net.razorclan.Soycraft.Entity.Projectile.ProjectileEntity;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class AdminBeam extends ProjectileEntity {
    public int bounces;

    public AdminBeam(Entity owner, boolean hostile, double x, double y, double z, float yaw, float pitch, double speed, double gravityMult, double projectileSize, int lifetime) {
        super(owner, hostile, x, y, z, yaw, pitch, speed, gravityMult, projectileSize, lifetime);
    }
    public AdminBeam(Entity owner, boolean hostile, Location location, double speed, double gravityMult, double projectileSize, int lifetime) {
        super(owner, hostile, location, speed, gravityMult, projectileSize, lifetime);
    }

    public void onCollision(RayTraceResult traceResult){
        location.getWorld().createExplosion(location, 1.0f, false, false, owner);

        if(bounces <= 0) {
            isActive = false;
            return;
        }

        Vector normal = new Vector(traceResult.getHitBlockFace().getModX(), traceResult.getHitBlockFace().getModY(), traceResult.getHitBlockFace().getModZ());
        double dotProduct = velocity.dot(normal);
        normal = normal.multiply(dotProduct * 2.0);
        velocity = velocity.subtract(normal).normalize().multiply(baseSpeed/20.0);

        bounces--;
    }

    public void onDraw(){
        location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 0);
    }
}
