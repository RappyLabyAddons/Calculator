package com.rappytv.calc;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;

@AddonMain
public class CalculatorAddon extends LabyAddon<CalculatorAddonConfig> {

    public final static String prefix = "§d§lCalculator §8» §7";

    @Override
    protected void enable() {
        registerSettingCategory();
        registerCommand(new CalculatorCommand(this));
    }

    @Override
    protected Class<? extends CalculatorAddonConfig> configurationClass() {
        return CalculatorAddonConfig.class;
    }

    public static String getTranslation(String key, Object ...args) {
        String translation = I18n.getTranslation(key, args);
        if(translation == null)
            return key;
        return translation;
    }
}
