package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SkribblCommands {
    public boolean command(Player author, String[] args) {

        if (args[0].equalsIgnoreCase("join")) {
            if (Main.getReady()) {
                if (Main.getBans().contains(author)) {
                    for (Player player : Main.getParty()) {
                        player.sendMessage(Main.getPre()+author.getDisplayName()+" tried to join the game but is banned!");}
                    author.sendMessage(Main.getPre()+"Sorry, but you're banned from joining Skribbl games at the moment. Contact "+Main.getOp()+" if you think this is a mistake!");
                } else if (Main.getParty().contains(author)) {
                    author.sendMessage(Main.getPre()+"You're already in the list of people wanting to take part in the next Skribbl game!");
                } else {
                    Main.getParty().add(author);
                    for (Player player : Main.getParty()) {
                        player.sendMessage(Main.getPre()+author.getDisplayName()+" has joined the game!");}
                    author.sendMessage(Main.getPre()+"You've now been added to the list of people wanting to take part in the next Skribbl game!");}
            } else {author.sendMessage(Main.getPre()+"A game is not ready yet!");}

        } else if (args[0].equalsIgnoreCase("leave")) {
            if (Main.getParty().contains(author)) {
                Main.getParty().remove(author);
                for (Player player : Main.getParty()) {
                    player.sendMessage(Main.getPre()+author.getDisplayName()+" has left the game!");}
                author.sendMessage(Main.getPre()+"You've now been removed from the list of people wanting to take part in the next Skribbl game!");
                if (Main.getStarted()) {Game.playerLeave(author);}
            } else {author.sendMessage(Main.getPre()+"You aren't even in the list of people wanting to take part in the next Skribbl game, yet!");}

        } else if (args[0].equalsIgnoreCase("kick")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    Player playerToKick = null;
                    for (Player player : Main.getParty()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            playerToKick = player;
                            player.sendMessage(Main.getPre()+"You've been kicked from the current Skribbl game!");}}
                    if (playerToKick == null) {author.sendMessage(Main.getPre()+"Cannot find this player in the current party!");
                    } else {
                        Main.getParty().remove(playerToKick);
                        for (Player player : Main.getParty()) {
                            player.sendMessage(Main.getPre()+playerToKick.getDisplayName()+" has been kicked from the game!");}
                        author.sendMessage(Main.getPre()+"The player has now been removed from the current game, but they may rejoin!");
                        Game.playerLeave(playerToKick);}
                } else {author.sendMessage(Main.getPre()+"Format: /skribbl kick (player)");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("ban")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    Player playerToBan = Bukkit.getPlayer(args[1]);
                    if (playerToBan == null) {
                        author.sendMessage(Main.getPre()+"Cannot find this player online on the server!");
                    } else if (Main.getBans().contains(playerToBan)) {
                        author.sendMessage(Main.getPre()+"This player has already been banned!");
                    } else {
                        Main.getBans().add(playerToBan);
                        playerToBan.sendMessage(Main.getPre()+"You've been banned from future Skribbl games in this session!");
                        if (Main.getParty().contains(playerToBan)) {
                            Main.getParty().remove(playerToBan);
                            for (Player player : Main.getParty()) {
                                player.sendMessage(Main.getPre()+playerToBan.getDisplayName()+" has been kicked from the game and banned from joining future ones!");}
                            author.sendMessage(Main.getPre()+"The player has now been removed from the current game and banned!");
                            Game.playerLeave(playerToBan);
                        } else {author.sendMessage(Main.getPre()+"The player has not been removed from the current game as they weren't in it but have been banned from future ones!");}}
                } else {author.sendMessage(Main.getPre()+"Format: /skribbl ban (player)");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("unban")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (args.length >= 2) {
                    Player playerUnbanned = null;
                    for (Player player : Main.getBans()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            Main.getBans().remove(player);
                            playerUnbanned = player; break;}}
                    if (playerUnbanned == null) {
                        author.sendMessage(Main.getPre()+"Cannot find this player in the bans list!");
                    } else {
                        for (Player player : Main.getParty()) {
                            player.sendMessage(Main.getPre()+playerUnbanned.getDisplayName()+" has been unbanned from joining Skribbl games!");}
                        author.sendMessage(Main.getPre()+"Player has now been removed from the bans list!");
                        if (playerUnbanned.isOnline()) {
                            playerUnbanned.sendMessage(Main.getPre()+"You've been unbanned from joining Skribbl games!");}}
                } else {author.sendMessage(Main.getPre()+"Format: /skribbl unban (player)");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("ready")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (!Main.getReady() && !Main.getStarted()) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                        author.sendMessage(Main.getPre()+"Building map; this may take a while!");
                        Game.mapRemoval();
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
                        author.sendMessage(Main.getPre()+"Done!");});
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.teleport(new Location(Main.getWorld(),4064,160,0));
                        if (Main.getBans().contains(player)) {
                            player.sendMessage(Main.getPre()+"A Skribbl game is about to start, but you're currently banned from joining Skribbl games!");
                        } else {
                            player.sendMessage(Main.getPre()+"A Skribbl game is about to start! Use §c/skribbl join §7to join the party ready to start!");}}
                    Main.setReady(true);
                } else {author.sendMessage("It is already ready!");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}
            
        } else if (args[0].equalsIgnoreCase("cancel")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (Main.getReady()) {
                    Main.setReady(false);
                    Main.getParty().clear();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(new Location(Main.getWorld(),0,Main.getWorld().getHighestBlockYAt(0,0)+1,0));
                        player.sendMessage(Main.getPre()+"The Skribbl game you were due to join has now been cancelled by the host. Apologies!");}
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> Game.mapRemoval(author));
                } else {author.sendMessage(Main.getPre()+"The game isn't planned yet and so can't be cancelled!");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("start")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (Main.getReady()) {
                    if (Main.getParty().size() >= 2) {
                        if (!Main.getStarted()) {
                            Main.setReady(false);
                            Main.setStarted(true);
                        } else {author.sendMessage(Main.getPre()+"The game is already started!");}
                    } else {author.sendMessage(Main.getPre()+"There must be at least 2 people in the party to start a game!");}
                } else {author.sendMessage(Main.getPre()+"The game isn't ready yet!");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("end")) {
            if (author.getDisplayName().equalsIgnoreCase(Main.getOp())) {
                if (Main.getStarted()) {
                    Game.end("of an operator decision");
                } else {author.sendMessage(Main.getPre()+"The game isn't running yet!");}
            } else {author.sendMessage(Main.getPre()+"You don't have permission to use this command!");}

        } else {author.sendMessage(Main.getPre()+"I- I am confused... (contact the developer of this plugin HopperElecYT)");}

        return true;
    }
}