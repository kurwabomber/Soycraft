package net.razorclan.Soycraft.Item;

import net.razorclan.Soycraft.Menu.MainMenu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem implements Listener {
    @EventHandler
    public void PlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            ItemStack item = event.getItem();
            if(item == null)
                return;

            String itemType = ItemHandler.getAttribute(event.getItem(), "itemAction", "").toString();
            if(menuRedirect(p, itemType)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerInventoryInteract(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (!(p instanceof Player))
            return;
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null)
            return;

        String itemType = ItemHandler.getAttribute(itemStack, "itemAction", "").toString();
        if (!itemType.isEmpty()){
            e.setCancelled(true);
            p.closeInventory();
        }
        menuRedirect((Player) p, itemType);
    }

    @EventHandler
    public void onPlayerSwapHands(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        ItemStack itemStack = e.getOffHandItem();

        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null)
            return;

        String itemType = ItemHandler.getAttribute(itemStack, "itemAction", "").toString();
        if(menuRedirect(p, itemType)) {
            e.setCancelled(true);
        }
    }
    public boolean menuRedirect(Player p, String input){
        switch (input) {
            case "mainMenu" -> MainMenu.initialize(p);
            default -> {return false;}
        }
        return true;
    }
}
