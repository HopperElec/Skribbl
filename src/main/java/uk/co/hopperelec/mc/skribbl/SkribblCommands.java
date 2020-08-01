package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SkribblCommands {
    Main main;
    public SkribblCommands(Main mainConstruct) {main = mainConstruct;}

    public boolean command(Player author, String[] args) {

        if (args[0].equalsIgnoreCase("join")) {
            if (main.getReady()) {
                if (main.getBans().contains(author)) {
                    for (Player player : main.getParty()) {
                        player.sendMessage(main.getPre()+author.getDisplayName()+" tried to join the game but is banned!");}
                    author.sendMessage(main.getPre()+"Sorry, but you're banned from joining Skribbl games at the moment. Contact "+main.getOp()+" if you think this is a mistake!");
                } else if (main.getParty().contains(author)) {
                    author.sendMessage(main.getPre()+"You're already in the list of people wanting to take part in the next Skribbl game!");
                } else {
                    main.getParty().add(author);
                    main.getPoints().put(author,0);
                    for (Player player : main.getParty()) {
                        player.sendMessage(main.getPre()+author.getDisplayName()+" has joined the game!");}
                    author.sendMessage(main.getPre()+"You've now been added to the list of people wanting to take part in the next Skribbl game!");}
            } else {author.sendMessage(main.getPre()+"A game is not ready yet!");}

        } else if (args[0].equalsIgnoreCase("leave")) {
            if (main.getParty().contains(author)) {
                main.getParty().remove(author);
                for (Player player : main.getParty()) {
                    player.sendMessage(main.getPre()+author.getDisplayName()+" has left the game!");}
                author.sendMessage(main.getPre()+"You've now been removed from the list of people wanting to take part in the next Skribbl game!");
                if (main.getStarted()) {main.getGame().playerLeave(author);}
            } else {author.sendMessage(main.getPre()+"You aren't even in the list of people wanting to take part in the next Skribbl game, yet!");}

        } else if (args[0].equalsIgnoreCase("kick")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (args.length >= 2) {
                    Player playerToKick = null;
                    for (Player player : main.getParty()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            playerToKick = player;
                            player.sendMessage(main.getPre()+"You've been kicked from the current Skribbl game!");}}
                    if (playerToKick == null) {author.sendMessage(main.getPre()+"Cannot find this player in the current party!");
                    } else {
                        main.getParty().remove(playerToKick);
                        for (Player player : main.getParty()) {
                            player.sendMessage(main.getPre()+playerToKick.getDisplayName()+" has been kicked from the game!");}
                        author.sendMessage(main.getPre()+"The player has now been removed from the current game, but they may rejoin!");
                        main.getGame().playerLeave(playerToKick);}
                } else {author.sendMessage(main.getPre()+"Format: /skribbl kick (player)");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("ban")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (args.length >= 2) {
                    Player playerToBan = Bukkit.getPlayer(args[1]);
                    if (playerToBan == null) {
                        author.sendMessage(main.getPre()+"Cannot find this player online on the server!");
                    } else if (main.getBans().contains(playerToBan)) {
                        author.sendMessage(main.getPre()+"This player has already been banned!");
                    } else {
                        main.getBans().add(playerToBan);
                        playerToBan.sendMessage(main.getPre()+"You've been banned from future Skribbl games in this session!");
                        if (main.getParty().contains(playerToBan)) {
                            main.getParty().remove(playerToBan);
                            for (Player player : main.getParty()) {
                                player.sendMessage(main.getPre()+playerToBan.getDisplayName()+" has been kicked from the game and banned from joining future ones!");}
                            author.sendMessage(main.getPre()+"The player has now been removed from the current game and banned!");
                            main.getGame().playerLeave(playerToBan);
                        } else {author.sendMessage(main.getPre()+"The player has not been removed from the current game as they weren't in it but have been banned from future ones!");}}
                } else {author.sendMessage(main.getPre()+"Format: /skribbl ban (player)");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("unban")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (args.length >= 2) {
                    Player playerUnbanned = null;
                    for (Player player : main.getBans()) {
                        if (player.getDisplayName().equalsIgnoreCase(args[1])) {
                            main.getBans().remove(player);
                            playerUnbanned = player; break;}}
                    if (playerUnbanned == null) {
                        author.sendMessage(main.getPre()+"Cannot find this player in the bans list!");
                    } else {
                        for (Player player : main.getParty()) {
                            player.sendMessage(main.getPre()+playerUnbanned.getDisplayName()+" has been unbanned from joining Skribbl games!");}
                        author.sendMessage(main.getPre()+"Player has now been removed from the bans list!");
                        if (playerUnbanned.isOnline()) {
                            playerUnbanned.sendMessage(main.getPre()+"You've been unbanned from joining Skribbl games!");}}
                } else {author.sendMessage(main.getPre()+"Format: /skribbl unban (player)");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("ready")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (!main.getReady() && !main.getStarted()) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                        author.sendMessage(main.getPre()+"Building map; this may take a while!");
                        main.getGame().mapRemoval();
                        for (int a = 1; a <= 64; a++) for (int b = 1; b <= 64; b++) {
                            main.getWorld().getBlockAt(4095,127+a,b-32).setType(Material.SEA_LANTERN);
                            main.getWorld().getBlockAt(4095,127+a,b-32).setType(Material.WHITE_WOOL);
                            main.getWorld().getBlockAt(4030,127+a,b-32).setType(Material.SEA_LANTERN);
                            main.getWorld().getBlockAt(4030+a,127,b-32).setType(Material.SEA_LANTERN);
                            main.getWorld().getBlockAt(4030+a,192,b-32).setType(Material.WHITE_STAINED_GLASS);
                            main.getWorld().getBlockAt(4030+a,127+b,-32).setType(Material.SEA_LANTERN);
                            main.getWorld().getBlockAt(4030+a,127+b,33).setType(Material.SEA_LANTERN);
                            main.getWorld().getBlockAt(4031,160,b-32).setType(Material.BARRIER);}
                        for (int y = 161; y <= 191; y++) for (int z = -31; z <= 32; z++) {
                            main.getWorld().getBlockAt(4032,y,z).setType(Material.BARRIER);}
                        author.sendMessage(main.getPre()+"Done!");});
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.teleport(new Location(main.getWorld(),4064,160,0));
                        if (main.getBans().contains(player)) {
                            player.sendMessage(main.getPre()+"A Skribbl game is about to start, but you're currently banned from joining Skribbl games!");
                        } else {
                            player.sendMessage(main.getPre()+"A Skribbl game is about to start! Use §c/skribbl join §7to join the party ready to start!");}}
                    main.setReady(true);
                    main.getParty().add(author);
                    main.getPoints().put(author,0);
                } else {author.sendMessage(main.getPre()+"The game is already ready!");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}
            
        } else if (args[0].equalsIgnoreCase("cancel")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (main.getReady()) {
                    main.setReady(false);
                    main.getParty().clear();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(new Location(main.getWorld(),0,main.getWorld().getHighestBlockYAt(0,0)+1,0));
                        player.sendMessage(main.getPre()+"The Skribbl game you were due to join has now been cancelled by the host. Apologies!");}
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> main.getGame().mapRemoval(author));
                } else {author.sendMessage(main.getPre()+"The game isn't planned yet and so can't be cancelled!");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("start")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (main.getReady()) {
                    if (main.getParty().size() >= 2) {
                        if (!main.getStarted()) {
                            main.setReady(false);
                            main.setStarted(true);
                            for (Player player : main.getParty()) {
                                player.teleport(new Location(main.getWorld(),4032,161,0));
                                player.setGameMode(GameMode.ADVENTURE);
                                player.setScoreboard(main.getScoreboard());
                                main.getPoints().put(player,0);}
                            main.getGame().nextDrawer();
                        } else {author.sendMessage(main.getPre()+"The game is already started!");}
                    } else {author.sendMessage(main.getPre()+"There must be at least 2 people in the party to start a game!");}
                } else {author.sendMessage(main.getPre()+"The game isn't ready yet!");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}

        } else if (args[0].equalsIgnoreCase("end")) {
            if (author.getDisplayName().equalsIgnoreCase(main.getOp())) {
                if (main.getStarted()) {
                    main.getGame().end("of an operator decision");
                } else {author.sendMessage(main.getPre()+"The game isn't running yet!");}
            } else {author.sendMessage(main.getPre()+"You don't have permission to use this command!");}

        } else {author.sendMessage(main.getPre()+"I- I am confused... (contact the developer of this plugin HopperElecYT)");}

        return true;
    }
}