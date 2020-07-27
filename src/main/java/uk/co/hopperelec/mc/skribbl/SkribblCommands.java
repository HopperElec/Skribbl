package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SkribblCommands {
    String pre = "§4[§cSkribbl§4]§r§7 ";

    public boolean command(Player author, String[] args) {

        if (args[0].equalsIgnoreCase("join")) {
            if (Main.getBans().contains(author)) {
                author.sendMessage(pre+"Sorry, but you're banned from joining Skribbl games at the moment. Contact "+Main.getOp()+" if you think this is a mistake!");
            } else if (Main.getParty().contains(author)) {
                author.sendMessage(pre+"You're already in the list of people wanting to take part in the next Skribbl game!");
            } else {
                Main.getParty().add(author);
                author.sendMessage(pre+"You've now been added to the list of people wanting to take part in the next Skribbl game!");}

        } else if (args[0].equalsIgnoreCase("leave")) {
            if (Main.getParty().contains(author)) {
                Main.getParty().remove(author);
                author.sendMessage(pre+"You've now been removed from the list of people wanting to take part in the next Skribbl game!");
            } else {
                author.sendMessage(pre+"You aren't even in the list of people wanting to take part in the next Skribbl game, yet!");}

        } else if (args[0].equalsIgnoreCase("kick")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    boolean kicked = false;
                    for (Player player : Main.getParty()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            kicked = true; Main.getParty().remove(player);
                            player.sendMessage(pre+"You've been kicked from the current Skribbl game!");}}
                    if (!kicked) {author.sendMessage(pre+"Cannot find this player in the current party!");
                    } else {author.sendMessage(pre+"The player has now been removed from the current game, but they may rejoin!");}
                } else {author.sendMessage(pre+"Format: /skribbl kick (player)");}
            } else {author.sendMessage(pre+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("ban")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    Player playerToBan = Bukkit.getPlayer(args[1]);
                    if (playerToBan == null) {
                        author.sendMessage(pre + "Cannot find this player online on the server!");
                    } else if (Main.getBans().contains(playerToBan)) {
                        author.sendMessage(pre+"This player has already been banned!");
                    } else {
                        Main.getBans().add(playerToBan);
                        playerToBan.sendMessage(pre+"You've been banned from future Skribbl games in this session!");
                        if (Main.getParty().contains(playerToBan)) {
                            Main.getParty().remove(playerToBan);
                            author.sendMessage(pre+"The player has now been removed from the current game and banned!");
                        } else {
                            author.sendMessage(pre+"The player has not been removed from the current game as they weren't in it but have been banned from future ones!");}}
                } else {author.sendMessage(pre+"Format: /skribbl ban (player)");}
            } else {author.sendMessage(pre+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("unban")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    Player playerUnbanned = null;
                    for (Player player : Main.getBans()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            Main.getBans().remove(player);
                            playerUnbanned = player; break;}}
                    if (playerUnbanned == null) {
                        author.sendMessage(pre+"Cannot find this player in the bans list!");
                    } else {
                        author.sendMessage(pre+"Player has now been removed from the bans list!");
                        if (playerUnbanned.isOnline()) {
                            playerUnbanned.sendMessage(pre+"You've been unbanned from joining Skribbl games!");}}
                } else {author.sendMessage(pre+"Format: /skribbl unban (player)");}
            } else {author.sendMessage(pre+"You don't have permission to use this command!");}

        }

        return true;
    }
}
