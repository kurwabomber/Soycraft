package net.razorclan.Soycraft.Entity.Projectile;

import net.razorclan.Soycraft.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class ProjectileEntity {
    public Location location;
    public Vector velocity;
    public double baseSpeed;
    public double projectileSize;
    public double gravityMult;
    public boolean isActive;
    public Entity owner;
    public int lifetime; //measured in ticks


    public ProjectileEntity(Entity owner, boolean hostile, double x, double y, double z, float yaw, float pitch, double speed,
                            double gravityMult, double projectileSize, int lifetime){
        this.owner = owner;
        this.location = new Location(owner.getWorld(), x, y, z , yaw, pitch);
        this.velocity = this.location.getDirection().multiply(speed/20.0);
        this.baseSpeed = speed;
        this.projectileSize = projectileSize;
        this.gravityMult = gravityMult;
        this.isActive = true;
        this.lifetime = lifetime;
        tick(Main.instance);
    }
    public ProjectileEntity(Entity owner, boolean hostile, Location location, double speed, double gravityMult, double projectileSize, int lifetime){
        this.owner = owner;
        this.location = location.clone();
        this.velocity = this.location.getDirection().multiply(speed/20.0);
        this.baseSpeed = speed;
        this.projectileSize = projectileSize;
        this.gravityMult = gravityMult;
        this.isActive = true;
        this.lifetime = lifetime;
        tick(Main.instance);
    }

    private void tick(final Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!isActive || lifetime <= 0) {
                    this.cancel();
                    return;
                }
                Vector direction = location.getDirection();
                onDraw();

                Predicate<Entity> ignoreList = i -> (i != owner && i instanceof LivingEntity && !i.isDead()
                        && i.getType() != EntityType.ARMOR_STAND && !(owner instanceof Player && i instanceof Player) );
                RayTraceResult traceResult = location.getWorld().rayTrace(location, direction, baseSpeed/20.0, FluidCollisionMode.NEVER, true, projectileSize, ignoreList);
                if(traceResult != null) {
                    onCollision(traceResult);
                }

                //add gravity soon.
                location.add(velocity);
                lifetime--;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public void onCollision(RayTraceResult traceResult){
        //Bukkit.getLogger().info("Projectile collided.");
        isActive = false;
    }
    public void onDraw(){
        location.getWorld().spawnParticle(Particle.CRIT, location, 0);
    }
}
