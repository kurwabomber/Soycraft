package net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.Anvil;

import net.razorclan.Soycraft.Item.ItemHandler;
import net.razorclan.Soycraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.SlotElement;
import xyz.xenondevs.invui.inventory.Inventory;
import xyz.xenondevs.invui.inventory.VirtualInventory;
import xyz.xenondevs.invui.inventory.event.ItemPostUpdateEvent;
import xyz.xenondevs.invui.inventory.event.UpdateReason;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AnvilMenu {
    public static void initialize(Player p){
        String[] guiSetup = {
                "bbbbbbbbb",
                "bb1b2b3bb",
                "bbbbbbbbb"
        };
        VirtualInventory inv = new VirtualInventory(3);
        Gui gui = Gui.normal()
                .setStructure(guiSetup)
                .addIngredient('b', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("")))
                .addIngredient('1', new SlotElement.InventorySlotElement(inv, 0))
                .addIngredient('2', new SlotElement.InventorySlotElement(inv, 1))
                .addIngredient('3', new SlotElement.InventorySlotElement(inv, 2))
                .build();

        Window window = Window.single()
                .setViewer(p)
                .setTitle("Anvil")
                .setGui(gui)
                .build();

        window.open();

        inv.setPreUpdateHandler(event -> {
            if(event.getSlot() == 2){
                ItemStack output = inv.getItem(2);
                if(output == null) return;
                for(int i = 0; i < 3; ++i) {
                    inv.setItem(UpdateReason.SUPPRESSED, i, null);
                }
            }
        });
        inv.setPostUpdateHandler(event -> {
            Bukkit.getLogger().info("inv edited");
            OnCraftingMenuUpdate(event);
        });
        window.addCloseHandler(() -> {
            for(int i = 0; i<3; ++i){
                ItemStack item = inv.getItem(i);
                if(item == null) continue;
                p.getInventory().addItem(item);
            }
        });
    }

    public static void OnCraftingMenuUpdate(ItemPostUpdateEvent event){
        Inventory inv = event.getInventory();
        //Make a recipe caching system later, this is for now.
        HashMap<List<String>, String> recipesMap = new HashMap<>();
        recipesMap.put(Arrays.asList("APPLE", "APPLE"), "TESTITEM");

        List<String> key = Arrays.asList("", "");
        for(int i = 0; i<3; ++i){
            ItemStack item = inv.getItem(i);
            if(item == null)
                continue;
            ItemMeta meta = item.getItemMeta();
            if(meta == null)
                continue;

            PersistentDataContainer container = meta.getPersistentDataContainer();
            key.set(i, container.get(Main.itemIDKey, PersistentDataType.STRING));
        }

        if(recipesMap.containsKey(key)){
            Bukkit.getLogger().info("recipe found");
            ItemStack output = Main.itemMap.get(recipesMap.getOrDefault(key, "")).clone();
            ItemHandler.validateItem(output);
            inv.setItem(UpdateReason.SUPPRESSED, 2, output);
        }
    }
}