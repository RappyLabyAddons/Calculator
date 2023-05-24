package com.rappytv.calc;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class CalculatorAddonConfig extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @SliderSetting(min = 0, max = 8)
    private final ConfigProperty<Integer> fractionDigits = new ConfigProperty<>(4);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public final ConfigProperty<Integer> fractionDigits() {
        return fractionDigits;
    }
}
