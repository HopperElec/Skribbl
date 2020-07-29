package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Game {
    Main main;
    public Game(Main mainConstruct) {main = mainConstruct;}

    public void mapRemoval(Player remover) {
        remover.sendMessage(main.getPre()+"Removing map; this may take a while!");
        mapRemoval();
        remover.sendMessage(main.getPre()+"Done!");}

    public void mapRemoval() {
        for (int x = 4029; x <= 4096; x++) for (int y = 126; y <= 193; y++) for (int z = -33; z <= 34; z++) {
            main.getWorld().getBlockAt(x,y,z).setType(Material.AIR);}}

    public void playerLeave(Player player) {
        if (main.getParty().size() <= 1) {
            end("there are no longer enough players");
        } else if (main.getCurrentDrawer() == player) {
            nextDrawer();}}

    public void end(String reason) {
        main.setCurrentDrawer(null);
        main.setStarted(false);
        main.getParty().clear();
        main.setCurrentWord(null);
        main.getCorrectGuessers().clear();
        main.getPoints().clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(new Location(main.getWorld(),0,main.getWorld().getHighestBlockYAt(0,0)+1,0));
            player.sendMessage(main.getPre()+"The Skribbl game you were playing has now been ended because "+reason+". Apologies!");}
        mapRemoval();}

    public void nextDrawer() {
        // main.getScoreboard(); (Refresh scoreboard with points here)

        if (main.getCurrentDrawer() == null) {
            main.setCurrentDrawer(main.getParty().get(main.getRandom().nextInt(main.getParty().size())));
        } else {
            main.getCorrectGuessers().clear();
            main.getCurrentDrawer().teleport(new Location(main.getWorld(),4033,161,0));
            main.getCurrentDrawer().setFlying(false);
            int index = main.getParty().indexOf(main.getCurrentDrawer());
            if (index == main.getParty().size()-1) {
                main.setCurrentDrawer(main.getParty().get(0));
            } else {
                main.setCurrentDrawer(main.getParty().get(index+1));}}
        main.getCurrentDrawer().teleport(new Location(main.getWorld(),4064,160,0,-90,0));
        main.getCurrentDrawer().setAllowFlight(true);
        main.getCurrentDrawer().setFlying(true);
        for (Player player : main.getParty()) {
            player.sendMessage(main.getPre()+main.getCurrentDrawer().getDisplayName()+" is now drawing!");}
        main.getCurrentDrawer().sendMessage(main.getPre()+"Your word is "+main.getCurrentWord());

        Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(),() -> {
            for (Player player : main.getParty()) {
                player.sendMessage(main.getPre()+main.getCurrentDrawer().getDisplayName()+" ended the round! The word was "+main.getCurrentWord());}
            nextDrawer();
        },200L);}
}
