package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

public final class Main extends JavaPlugin {
    List<String> cmds = new ArrayList<>();
    SkribblCommands skribblCmds = new SkribblCommands();
    String worldname = "world";
    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    public static Plugin plugin = null;
    static boolean ready = false;
    static boolean started = false;
    static Player currentDrawer;
    static String op = "HopperElecYT";
    static String pre = "§4[§cSkribbl§4]§r§7 ";
    static List<Player> party = new ArrayList<>();
    static List<Player> bans = new ArrayList<>();
    static World world;
    static Random random = new Random();
    static String currentWord = null;
    static Map<Player,Integer> points = new HashMap<>();
    static Scoreboard scoreboard;

    public static Plugin getPlugin() {return plugin;}
    public static boolean getReady() {return ready;}
    public static boolean getStarted() {return started;}
    public static Player getCurrentDrawer() {return currentDrawer;}
    public static String getOp() {return op;}
    public static String getPre() {return pre;}
    public static List<Player> getParty() {return party;}
    public static List<Player> getBans() {return bans;}
    public static World getWorld() {return world;}
    public static Random getRandom() {return random;}
    public static String getCurrentWord() {return currentWord;}
    public static Map<Player,Integer> getPoints() {return points;}
    public static Scoreboard getScoreboard() {return scoreboard;}

    public static void setReady(boolean value) {ready = value;}
    public static void setStarted(boolean value) {started = value;}
    public static void setCurrentDrawer(Player value) {currentDrawer = value;}
    public static void setCurrentWord(String value) {currentWord = value;}

    @Override
    public void onEnable() {
        plugin = this;
        world = Bukkit.getWorld(worldname);
        if (world == null) {
            System.out.println("Cannot find world by name "+worldname+"! Cancelling startup of plugin.");
            this.setEnabled(false);}

        if (scoreboardManager == null) {
            System.out.println("Bukkit scoreboard manager failed! Cancelling startup of plugin.");
            this.setEnabled(false);}
        scoreboard = scoreboardManager.getNewScoreboard();

        cmds.add("ready");cmds.add("cancel");cmds.add("start");cmds.add("end");cmds.add("kick");cmds.add("ban");cmds.add("unban");cmds.add("join");cmds.add("leave");
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {Game.mapRemoval();}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (author instanceof Player) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                author.sendMessage("§e§l/skribbl §r§4- §eCore command for HopperElecYT's Skribbl plugin");
                author.sendMessage("§e§l/skribbl help §r§4- §eDisplays this page");
                if (((Player) author).getDisplayName().equalsIgnoreCase(op)) {
                    author.sendMessage("§e§l/skribbl ready §r§4- §ePrepares for a game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl cancel §r§4- §eUnreadies a game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl start §r§4- §eStarts a game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl end §r§4- §eForcefully ends a game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl kick §r§4- §eKicks a player from an ongoing game but can rejoin ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl ban §r§4- §eBans a player from taking part in games until server restart ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl unban §r§4- §eUnbans a player from taking part in Skribbl games in this session ("+op+" only!)");}
                author.sendMessage("§e§l/skribbl join §r§4- §eJoins the list of players wanting to take part in the next Skribbl game");
                author.sendMessage("§e§l/skribbl leave §r§4- §eLeaves the list of players wanting to take part in the next Skribbl game");
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            } else {
                boolean found = false;
                for (String arg : cmds) {
                    if (args[0].equalsIgnoreCase(arg)) {
                        found = true; break;}}
                if (found) {
                    return skribblCmds.command((Player) author,args);
                } else {author.sendMessage("Invalid Skribbl command!"); return false;}}
        } else {return false;}
        return true;
    }
}
