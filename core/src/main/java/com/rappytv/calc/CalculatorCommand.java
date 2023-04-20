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
            addon.msg(CalculatorAddon.prefix + "§c" + CalculatorAddon.getTranslation("calc.messages.missingProblem"));
            return true;
        }
        try {
            String problem = String.join(" ", arguments);
            addon.msg(CalculatorAddon.prefix + problem + " §a= §n" + formatNumber(eval(problem)));
        } catch (NumberFormatException e) {
            addon.msg(CalculatorAddon.prefix + "§c" + CalculatorAddon.getTranslation("calc.messages.error"));
            e.printStackTrace();
        }
        return true;
    }

    private String formatNumber(double number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setMaximumFractionDigits(number % 1 == 0 ? 0 : 4);

        return formatter.format(number);
    }

    /**
     *
     * Function by <a href="https://stackoverflow.com/users/964243/boann">Boann</a> on <a href="https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form">StackOverflow</a>
     */
    public static double eval(final String str) throws NumberFormatException {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while(ch == ' ') nextChar();
                if(ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if(pos < str.length()) throw new NumberFormatException();
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for(;;) {
                    if(eat('+')) x += parseTerm(); // addition
                    else if(eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for(;;) {
                    if(eat('*')) x *= parseFactor(); // multiplication
                    else if(eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if(eat('+')) return +parseFactor(); // unary plus
                if(eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new NumberFormatException();
                } else if((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if(ch >= 'a' && ch <= 'z') { // functions
                    while(ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if(eat('(')) {
                        x = parseExpression();
                        if(!eat(')')) throw new NumberFormatException();
                    } else {
                        x = parseFactor();
                    }
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new NumberFormatException();
                    }
                } else {
                    throw new NumberFormatException();
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
