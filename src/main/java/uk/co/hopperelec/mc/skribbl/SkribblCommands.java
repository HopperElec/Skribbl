package uk.co.hopperelec.mc.skribbl;

import org.bukkit.entity.Player;

public class SkribblCommands {
    String pre = "§4[§cSkribbl§4]§r§7 ";

    public boolean command(Player author, String[] args) {
        if (args[0].equalsIgnoreCase("join")) {
            if (Main.getBans().contains(author)) {
                author.sendMessage("Sorry, bt");}}
        return true;
    }
}
