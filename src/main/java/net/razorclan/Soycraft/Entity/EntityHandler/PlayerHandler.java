package net.razorclan.Soycraft.Entity.EntityHandler;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import net.kyori.adventure.text.Component;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.Item.ItemHandler;
import net.razorclan.Soycraft.Item.WeaponCombos.BaseSwordCombo;
import net.razorclan.Soycraft.Main;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

import java.util.UUID;
import java.util.function.Predicate;

public class PlayerHandler implements Listener  {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));

        PlayerInfo info = new PlayerInfo();
        Main.entityMap.put(p.getUniqueId(), info);
        ((PlayerInfo)Main.entityMap.get(p.getUniqueId())).updatePlayerStats(p);
    }
    @EventHandler
    public void onplayerRespawn(PlayerRespawnEvent e) {
        Main.entityMap.get(e.getPlayer().getUniqueId()).health = Main.entityMap.get(e.getPlayer().getUniqueId()).maxHealth;
        ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).mana = 0;
    }

    @EventHandler
    public void onInventoryChange(InventoryClickEvent e) {
        ((PlayerInfo)Main.entityMap.get(e.getWhoClicked().getUniqueId())).updatePlayerStats((Player) e.getWhoClicked());
    }
    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent e) {
        ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
    }
    @EventHandler
    public void onPlayerSwapItem(PlayerSwapHandItemsEvent e) {
        ((PlayerInfo)Main.entityMap.get(e.getPlayer().getUniqueId())).updatePlayerStats(e.getPlayer());
    }
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event){
        event.setCancelled(false);
    }

    @EventHandler
    public void onPlayerAttack(PlayerAttackEntityCooldownResetEvent e) {
        leftClickAttack(e.getPlayer());
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction().isLeftClick())
            leftClickAttack(e.getPlayer());
    }

    public static void leftClickAttack(Player p) {
        ItemStack item = p.getInventory().getItemInMainHand();
        if(p.hasCooldown(item.getType()))
            return;

        UUID id = p.getUniqueId();
        RayTraceResult trace = hitRayTrace(p);
        double damageDealt = 2 * 1+(Math.sqrt(Main.entityMap.get(id).strength + Main.entityMap.get(id).dexterity)/10.0);

        if(trace != null && trace.getHitEntity() != null) {
            EntityHandler.dealDamage(trace.getHitEntity(), p, damageDealt);
        }

        switch(ItemHandler.getAttribute(item, "moveset", "").toString()){
            case "basicSword" -> {
                BaseSwordCombo.onUse(p);
            }
        }
        //set after attributes are made
        p.setCooldown(p.getInventory().getItemInMainHand().getType(), 5);
        ((PlayerInfo)Main.entityMap.get(id)).currentCombo++;
    }

    public static RayTraceResult hitRayTrace(LivingEntity ent) {
        Location loc = ent.getEyeLocation();
        Predicate<Entity> filter = e -> (!(e instanceof Player) && e instanceof LivingEntity && !e.isDead() && !(e instanceof ArmorStand));
        return ent.getWorld().rayTrace(loc, loc.getDirection(), 4.0, FluidCollisionMode.NEVER, true, 0.5, filter);
    }
}
