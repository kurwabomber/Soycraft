package net.razorclan.Soycraft.Menu.MainSubmenus.Crafting.CraftingTable;

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

public class CraftingTableMenu {
    public static void initialize(Player p){
        String[] guiSetup = {
                "bbbbbbbbb",
                "b123bbbbb",
                "b456bobbb",
                "b789bbbbb",
                "bbbbbbbbb"
        };
        VirtualInventory inv = new VirtualInventory(10);
        Gui gui = Gui.normal()
                .setStructure(guiSetup)
                .addIngredient('b', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("")))
                .addIngredient('1', new SlotElement.InventorySlotElement(inv, 0))
                .addIngredient('2', new SlotElement.InventorySlotElement(inv, 1))
                .addIngredient('3', new SlotElement.InventorySlotElement(inv, 2))
                .addIngredient('4', new SlotElement.InventorySlotElement(inv, 3))
                .addIngredient('5', new SlotElement.InventorySlotElement(inv, 4))
                .addIngredient('6', new SlotElement.InventorySlotElement(inv, 5))
                .addIngredient('7', new SlotElement.InventorySlotElement(inv, 6))
                .addIngredient('8', new SlotElement.InventorySlotElement(inv, 7))
                .addIngredient('9', new SlotElement.InventorySlotElement(inv, 8))
                .addIngredient('o', new SlotElement.InventorySlotElement(inv, 9))
                .build();

        Window window = Window.single()
                .setViewer(p)
                .setTitle("3x3 Crafting Table")
                .setGui(gui)
                .build();

        window.open();

        inv.setPreUpdateHandler(event -> {
            if(event.getSlot() == 9){
                if(event.isAdd()){
                    event.setCancelled(true);
                }
                else {
                    ItemStack output = inv.getItem(9);
                    if (output == null) return;
                    for (int i = 0; i < 9; ++i) {
                        inv.setItem(UpdateReason.SUPPRESSED, i, null);
                    }
                }
            }
        });
        inv.setPostUpdateHandler(event -> {
            Bukkit.getLogger().info("inv edited");
            OnCraftingMenuUpdate(event);
        });
        window.addCloseHandler(() -> {
            for(int i = 0; i<9; ++i){
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
        recipesMap.put(Arrays.asList(
                "APPLE", "", "",
                "", "APPLE", "",
                "", "", "APPLE"),
                "TESTITEM");

        List<String> key = Arrays.asList("", "", "", "", "", "", "", "", "");
        for(int i = 0; i<9; ++i){
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
            inv.setItem(UpdateReason.SUPPRESSED, 9, output);
        }else{
            inv.setItem(UpdateReason.SUPPRESSED, 9, null);
        }
    }
}