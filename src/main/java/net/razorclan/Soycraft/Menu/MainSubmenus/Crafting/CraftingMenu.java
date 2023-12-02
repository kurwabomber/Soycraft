package net.razorclan.Soycraft.Menu.MainSubmenus.Crafting;

import net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.Anvil.AnvilMenuItem;
import net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.CraftingTable.CraftingTableItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class CraftingMenu {
    public static void initialize(Player p){
        String[] guiSetup = {
                "bbbbbbbbb",
                "bca.....b",
                "b.......b",
                "b.......b",
                "bbbbbbbbb"
        };
        Gui gui = Gui.normal()
                .setStructure(guiSetup)
                .addIngredient('b', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))
                .addIngredient('c', new CraftingTableItem())
                .addIngredient('a', new AnvilMenuItem())
                .build();

        Window window = Window.single()
                .setViewer(p)
                .setTitle("Crafting Main Menu")
                .setGui(gui)
                .build();

        window.open();
    }
}