package com.rappytv.calc;

import net.labymod.api.client.chat.command.Command;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

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
            addon.displayMessage(CalculatorAddon.prefix + problem + " = " + formatNumber(solveProblem(problem)));
        } catch (NumberFormatException e) {
            addon.displayMessage(CalculatorAddon.prefix + "§c" + CalculatorAddon.getTranslation("calc.messages.error"));
            e.printStackTrace();
        }
        return true;
    }

    private Number solveProblem(String problem) {
        return 69;
    }

    private String formatNumber(Number number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setMaximumFractionDigits(((float) number) % 1 == 0 ? 0 : 4);

        return formatter.format(number);
    }
}
