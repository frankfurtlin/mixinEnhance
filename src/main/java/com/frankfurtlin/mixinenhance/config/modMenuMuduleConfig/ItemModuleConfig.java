package com.frankfurtlin.mixinenhance.config.modMenuMuduleConfig;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 7:10
 */
public class ItemModuleConfig {
    public static class TntConfig {
        @ConfigEntry.Gui.Tooltip
        public boolean enableRandomExplode = false;         // 是否启用TNT随机爆炸强度
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 4)      // TNT最小爆炸强度
        public int minExplodeRadius = 4;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 5, max = 128)    // TNT最大爆炸强度
        public int maxExplodeRadius = 8;
    }

    public static class EndCrystalConfig {
        @ConfigEntry.Gui.Tooltip
        public boolean enableRandomExplode = false;         // 是否启用末影水晶随机爆炸强度
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 6)      // 末影水晶最小爆炸强度
        public int minExplodeRadius = 6;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 7, max = 128)    // 末影水晶最大爆炸强度
        public int maxExplodeRadius = 8;
    }

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public TntConfig tntConfig = new TntConfig();
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public EndCrystalConfig endCrystalConfig = new EndCrystalConfig();
    @ConfigEntry.Gui.Tooltip
    public boolean inventoryTotemEnabled = false;           // 是否启用不死图腾在背包中生效
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = -1, max = 3600)      // 光灵箭的荧光持续时间(秒)
    public int spectralArrowDuration = 10;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int experienceBottleValue = 5;                   // 经验瓶经验值
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 15)         // 小火球伤害（火焰弹、烈焰人）
    public int smallFireballDamage = 5;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)         // 火球伤害（恶魂）
    public int fireballDamage = 6;
}
