package com.rappytv.calc;

import net.labymod.api.client.chat.command.Command;
import net.labymod.api.configuration.loader.annotation.ConfigName;

@ConfigName("settings")
public class CalculatorCommand extends Command {

    private final CalculatorAddon addon;

    protected CalculatorCommand(CalculatorAddon addon) {
        super("calculator", "calc");
        this.addon = addon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(arguments.length < 1) {
            addon.displayMessage(CalculatorAddon.prefix + "§c" + CalculatorAddon.getTranslation("calc.messages.missingProblem"));
            return true;
        }
        try {
            String problem = String.join(" ", arguments);
            addon.displayMessage(CalculatorAddon.prefix + problem + " = " + "<solution>");
        } catch (Exception e) {
            addon.displayMessage(CalculatorAddon.prefix + "§c" + CalculatorAddon.getTranslation("calc.messages.error"));
            e.printStackTrace();
        }
        return true;
    }
}
