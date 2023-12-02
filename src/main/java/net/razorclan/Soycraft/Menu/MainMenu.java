package net.razorclan.Soycraft.Menu;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.razorclan.Soycraft.Item.ItemHandler;
import net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.CraftingMenuItem;
import net.razorclan.Soycraft.Menu.MainSubmenus.Skills.SkillsMenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class MainMenu {
    public static void initialize(Player player) {
        String[] guiSetup = {
                "bbbbbbbbb",
                "b...v...b",
                "b..tsr..b",
                "b..aie..b",
                "b..cp...b",
                "bbbbbbbbb",
        };
        Gui gui = Gui.normal()
                .setStructure(guiSetup)
                .addIngredient('b', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))

                .addIngredient('s', new SimpleItem(new ItemBuilder(Material.PLAYER_HEAD).setDisplayName(IridiumColorAPI.process("<SOLID:6699ff>Soyblock Stats"))
                        .addLoreLines("placeholder") ))

                .addIngredient('p', new SimpleItem(new ItemBuilder(Material.COMMAND_BLOCK).setDisplayName(IridiumColorAPI.process("<SOLID:9999ff>Client Preferences"))
                        .addLoreLines(IridiumColorAPI.process("<SOLID:cc66ff>Change your settings here."))) )

                .addIngredient('v', new SimpleItem(new ItemBuilder(Material.ENDER_CHEST).setDisplayName(IridiumColorAPI.process("<SOLID:6666ff>Vault"))
                        .addLoreLines(IridiumColorAPI.process("<SOLID:4d4dff>Store & access items."))) )

                .addIngredient('r', new SimpleItem(new ItemBuilder(ItemHandler.getItemForDisplay(Material.IRON_CHESTPLATE)).setDisplayName(IridiumColorAPI.process("<SOLID:33ccff>Armory"))
                        .addLoreLines(IridiumColorAPI.process("<SOLID:33cccc>See your collection of items."))) )

                .addIngredient('t', new SimpleItem(new ItemBuilder(Material.ENDER_PEARL).setDisplayName(IridiumColorAPI.process("<SOLID:00cc66>Teleport"))
                        .addLoreLines(IridiumColorAPI.process("<SOLID:00994d>Teleport to previously visited places."))) )

                .addIngredient('a', new SimpleItem(new ItemBuilder(Material.RED_BANNER).setDisplayName(IridiumColorAPI.process("<SOLID:ff3399>Achievements"))
                        .addLoreLines(IridiumColorAPI.process("<SOLID:e60073>Collect rewards from milestones."))) )

                .addIngredient('i', new SkillsMenuItem())

                .addIngredient('e', new SimpleItem(new ItemBuilder(ItemHandler.getItemForDisplay(Material.IRON_SWORD)).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Equipment"))
                        .addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Change out your currently worn gear."))) )

                .addIngredient('i', new CraftingMenuItem())
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setTitle("Soyblock Main Menu")
                .setGui(gui)
                .build();

        window.open();
    }
}
