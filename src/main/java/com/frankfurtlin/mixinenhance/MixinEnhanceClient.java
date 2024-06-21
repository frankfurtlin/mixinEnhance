package com.frankfurtlin.mixinenhance;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/10 13:44
 */
public class MixinEnhanceClient implements ClientModInitializer {
    private static ConfigHolder<ModMenuConfig> configHolder;

    public static ModMenuConfig getConfig() {
        return configHolder.getConfig();
    }
    @Override
    public void onInitializeClient() {
        configHolder = AutoConfig.register(ModMenuConfig.class, JanksonConfigSerializer::new);
    }
}
