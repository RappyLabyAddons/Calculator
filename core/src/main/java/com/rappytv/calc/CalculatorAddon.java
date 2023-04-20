package com.rappytv.calc;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;

@AddonMain
public class CalculatorAddon extends LabyAddon<CalculatorAddonConfig> {

    public final static String prefix = "§9§lCalculator §8» §7";

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

    public void msg(String text) {
        TextComponent.Builder component = TextComponent.builder();
        String[] lines = text.split("\n");
        component.append("§8»\n");

        for(String line : lines) {
            component.append(prefix + line);
        }

        component.append("\n§8»");
        displayMessage(component.build());
    }
}
