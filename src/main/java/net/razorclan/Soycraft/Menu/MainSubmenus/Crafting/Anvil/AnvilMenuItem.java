package net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.Anvil;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class AnvilMenuItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.ANVIL).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Anvil"))
                .addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Standard Anvil"));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        AnvilMenu.initialize(player);
    }
}