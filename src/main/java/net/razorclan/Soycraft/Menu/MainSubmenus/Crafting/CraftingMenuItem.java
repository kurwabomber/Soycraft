package net.razorclan.Soycraft.Menu.MainSubmenus.Crafting;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class CraftingMenuItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.CRAFTING_TABLE).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Crafting"))
                .addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Craft using any unlocked station."));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        CraftingMenu.initialize(player);
        notifyWindows(); // this will update the ItemStack that is displayed to the player
    }

}
