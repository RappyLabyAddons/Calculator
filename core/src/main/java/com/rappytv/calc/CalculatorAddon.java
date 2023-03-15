package com.rappytv.calc;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class CalculatorAddon extends LabyAddon<CalculatorAddonConfig> {

    @Override
    protected void enable() {

    }

    @Override
    protected Class<? extends CalculatorAddonConfig> configurationClass() {
        return CalculatorAddonConfig.class;
    }
}
