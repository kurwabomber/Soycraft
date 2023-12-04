package net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.CraftingTable;

import xyz.xenondevs.invui.item.impl.AbstractItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class CraftingTableItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.CRAFTING_TABLE).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Crafting Table"))
                .addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Standard 3x3 Crafting Table"));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        CraftingTableMenu.initialize(player);
    }
}