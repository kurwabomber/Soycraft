package net.razorclan.Soycraft.Menu.MainSubmenus.Skills;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SkillsMenuItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(IridiumColorAPI.process("<SOLID:ff9999>Skills"))
                .addLoreLines(IridiumColorAPI.process("<SOLID:ff9966>See your skill progression."));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        SkillsMenu.initialize(player);
        notifyWindows(); // this will update the ItemStack that is displayed to the player
    }

}