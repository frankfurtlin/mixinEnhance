package com.frankfurtlin.mixinenhance.config;

import com.frankfurtlin.mixinenhance.config.modMenuMuduleConfig.DefaultModuleConfig;
import com.frankfurtlin.mixinenhance.config.modMenuMuduleConfig.EntityModuleConfig;
import com.frankfurtlin.mixinenhance.config.modMenuMuduleConfig.ItemModuleConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

/**
 * 配置参考链接
 * <a href="https://shedaniel.gitbook.io/cloth-config/auto-config/annotations">...</a>
 */
@Config(name = "mixinenhance")
public class ModMenuConfig implements ConfigData {
    static {
        AutoConfig.register(ModMenuConfig.class, JanksonConfigSerializer::new);
    }
    @ConfigEntry.Gui.Excluded
    public static final ModMenuConfig INSTANCE = AutoConfig.getConfigHolder(ModMenuConfig.class).getConfig();

    // 特性
    @ConfigEntry.Category("default")
    @ConfigEntry.Gui.TransitiveObject
    public DefaultModuleConfig defaultModuleConfig = new DefaultModuleConfig();

    // 物品
    @ConfigEntry.Category("item")
    @ConfigEntry.Gui.TransitiveObject
    public ItemModuleConfig itemModuleConfig = new ItemModuleConfig();

    // 实体
    @ConfigEntry.Category("entity")
    @ConfigEntry.Gui.TransitiveObject
    public EntityModuleConfig entityModuleConfig = new EntityModuleConfig();

}