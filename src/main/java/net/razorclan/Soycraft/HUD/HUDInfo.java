package net.razorclan.Soycraft.HUD;

import net.razorclan.Soycraft.Entity.PlayerInfo;

public class HUDInfo {
    public long time; //Use System.currentTimeMillis();
    public HUDChannel channel;
    public String displayText;

    public static void addHUDInfo(PlayerInfo pInfo, double duration, HUDChannel displayChannel, String text){
        pInfo.extraHudText.removeIf(hInfo -> displayChannel == hInfo.channel);
        HUDInfo input = new HUDInfo();
        input.time = (long)(duration*1000) + System.currentTimeMillis();
        input.displayText = text;
        input.channel = displayChannel;

        pInfo.extraHudText.add(input);
    }
}
