package com.frankfurtlin.mixinenhance.config.modMenuMuduleConfig;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 7:09
 */
public class DefaultModuleConfig {
    @ConfigEntry.Gui.Tooltip
    public boolean enableAutoFishing = false;        // 是否启用自动钓鱼
}
