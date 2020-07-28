package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SkribblCommands {
    String pre = "§4[§cSkribbl§4]§r§7 ";

    public boolean command(Player author, String[] args) {

        if (args[0].equalsIgnoreCase("join")) {
            if (Main.getReady()) {
                if (Main.getBans().contains(author)) {
                    for (Player player : Main.getParty()) {
                        player.sendMessage(pre+author.getDisplayName()+" tried to join the game but is banned!");}
                    author.sendMessage(pre+"Sorry, but you're banned from joining Skribbl games at the moment. Contact "+Main.getOp()+" if you think this is a mistake!");
                } else if (Main.getParty().contains(author)) {
                    author.sendMessage(pre+"You're already in the list of people wanting to take part in the next Skribbl game!");
                } else {
                    Main.getParty().add(author);
                    for (Player player : Main.getParty()) {
                        player.sendMessage(pre+author.getDisplayName()+" has joined the game!");}
                    author.sendMessage(pre+"You've now been added to the list of people wanting to take part in the next Skribbl game!");}
            } else {author.sendMessage("A game is not ready yet!");}

        } else if (args[0].equalsIgnoreCase("leave")) {
            if (Main.getParty().contains(author)) {
                Main.getParty().remove(author);
                for (Player player : Main.getParty()) {
                    player.sendMessage(pre+author.getDisplayName()+" has left the game!");}
                author.sendMessage(pre+"You've now been removed from the list of people wanting to take part in the next Skribbl game!");
            } else {
                author.sendMessage(pre+"You aren't even in the list of people wanting to take part in the next Skribbl game, yet!");}

        } else if (args[0].equalsIgnoreCase("kick")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    Player playerToKick = null;
                    for (Player player : Main.getParty()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            playerToKick = player;
                            player.sendMessage(pre+"You've been kicked from the current Skribbl game!");}}
                    if (playerToKick == null) {author.sendMessage(pre+"Cannot find this player in the current party!");
                    } else {
                        Main.getParty().remove(playerToKick);
                        for (Player player : Main.getParty()) {
                            player.sendMessage(pre+author.getDisplayName()+" has been kicked from the game!");}
                        author.sendMessage(pre+"The player has now been removed from the current game, but they may rejoin!");}
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
                            for (Player player : Main.getParty()) {
                                player.sendMessage(pre+playerToBan.getDisplayName()+" has been kicked from the game and banned from joining future ones!");}
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
                        for (Player player : Main.getParty()) {
                            player.sendMessage(pre+playerUnbanned.getDisplayName()+" has been unbanned from joining Skribbl games!");}
                        author.sendMessage(pre+"Player has now been removed from the bans list!");
                        if (playerUnbanned.isOnline()) {
                            playerUnbanned.sendMessage(pre+"You've been unbanned from joining Skribbl games!");}}
                } else {author.sendMessage(pre+"Format: /skribbl unban (player)");}
            } else {author.sendMessage(pre+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("ready")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    author.sendMessage(pre+"Building map; this may take a while!");
                    for (int x = 4029; x <= 4096; x++) for (int y = 126; y <= 193; y++) for (int z = -33; z <= 34; z++) {
                        Main.getWorld().getBlockAt(x,y,z).setType(Material.AIR);}
                    for (int a = 1; a <= 64; a++) for (int b = 1; b <= 64; b++) {
                        Main.getWorld().getBlockAt(4095,127+a,b-32).setType(Material.SEA_LANTERN);
                        Main.getWorld().getBlockAt(4095,127+a,b-32).setType(Material.WHITE_WOOL);
                        Main.getWorld().getBlockAt(4030,127+a,b-32).setType(Material.SEA_LANTERN);
                        Main.getWorld().getBlockAt(4030+a,127,b-32).setType(Material.SEA_LANTERN);
                        Main.getWorld().getBlockAt(4030+a,192,b-32).setType(Material.WHITE_STAINED_GLASS);
                        Main.getWorld().getBlockAt(4030+a,127+b,-32).setType(Material.SEA_LANTERN);
                        Main.getWorld().getBlockAt(4030+a,127+b,33).setType(Material.SEA_LANTERN);
                        Main.getWorld().getBlockAt(4031,160,b-32).setType(Material.BARRIER);}
                    for (int y = 161; y <= 191; y++) for (int z = -31; z <= 32; z++) {
                        Main.getWorld().getBlockAt(4032,y,z).setType(Material.BARRIER);}
                    author.sendMessage(pre+"Done!");});
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(new Location(Main.getWorld(),4064,160,0));
                    if (Main.getBans().contains(player)) {
                        player.sendMessage(pre+"A Skribbl game is about to start, but you're currently banned from joining Skribbl games!");
                    } else {
                        player.sendMessage(pre+"A Skribbl game is about to start! Use §c/skribbl join §7to join the party ready to start!");}}
                Main.setReady(true);
            } else {author.sendMessage(pre+"You don't have permission to use this command!");}
            
        } else if (args[0].equalsIgnoreCase("cancel")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                Main.setReady(false);
                Main.getParty().clear();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(new Location(Main.getWorld(),0,Main.getWorld().getHighestBlockYAt(0,0)+1,0));
                    player.sendMessage(pre+"The Skribbl game you were due to join has now been cancelled by the host. Apologies!");}
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    author.sendMessage(pre+"Removing map; this may take a while!");
                    for (int x = 4029; x <= 4096; x++) for (int y = 126; y <= 193; y++) for (int z = -33; z <= 34; z++) {
                        Main.getWorld().getBlockAt(x,y,z).setType(Material.AIR);}
                    author.sendMessage(pre+"Done!");});
            } else {author.sendMessage(pre+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("start")) {

        } else if (args[0].equalsIgnoreCase("end")) {

        } else {author.sendMessage("I- I am confused... (contact the developer of this plugin HopperElecYT)");}

        return true;
    }
}