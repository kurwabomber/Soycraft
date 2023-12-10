package net.razorclan.Soycraft.SpellSystem;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.razorclan.Soycraft.Entity.PlayerInfo;
import net.razorclan.Soycraft.HUD.HUDChannel;
import net.razorclan.Soycraft.HUD.HUDInfo;
import net.razorclan.Soycraft.Main;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Spell {
    public String name;
    public double manaCost;

    public void onCast(LivingEntity entity){
        UUID id = entity.getUniqueId();
        if(!Main.entityMap.containsKey(id))
            return;

        if(entity instanceof Player) {
            HUDInfo.addHUDInfo((PlayerInfo) Main.entityMap.get(id), 1.0,  HUDChannel.SPELLCAST, IridiumColorAPI.process("<SOLID:ffffff>Casted: ") +
                    IridiumColorAPI.process(String.format("<SOLID:e69602>%s ",name)) +
                    IridiumColorAPI.process(String.format("<SOLID:095fb0>-%.0f✦",manaCost)));
        }
    }
}
