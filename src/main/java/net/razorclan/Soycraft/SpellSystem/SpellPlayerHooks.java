package net.razorclan.Soycraft.SpellSystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SpellPlayerHooks implements Listener {
    @EventHandler
    public void onSpellReady(PlayerToggleSneakEvent e) {

    }
    @EventHandler
    public void onHotkeyPressed(PlayerItemHeldEvent e) {
        if(e.getPlayer().isSneaking()) {
            e.setCancelled(true);
            SpellPlayerHandler.castSpell(e.getPlayer(), e.getNewSlot());
        }
    }
}
