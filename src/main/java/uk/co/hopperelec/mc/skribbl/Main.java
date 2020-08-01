package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.*;

public final class Main extends JavaPlugin {
    List<String> cmds = new ArrayList<>();
    SkribblCommands skribblCmds = new SkribblCommands(this);
    ScoreboardManager scoreboardManager;
    final String worldname = "world";
    final String op = "HopperElecYT";
    final String pre = "§4[§cSkribbl§4]§r§7 ";
    final long roundLength = 200;
    final Random random = new Random();
    public Plugin plugin = null;
    boolean ready = false;
    boolean started = false;
    Game game = new Game(this);
    Player currentDrawer;
    List<Player> party = new ArrayList<>();
    List<Player> bans = new ArrayList<>();
    World world;
    String currentWord = null;
    List<Integer> currentHint = new ArrayList<>();
    List<String> wordList = new ArrayList<>();
    List<String> usedWords = new ArrayList<>();
    List<Player> correctGuessers = new ArrayList<>();
    Map<Player,Integer> points = new HashMap<>();
    Scoreboard scoreboard;
    Objective objective;

    public Plugin getPlugin() {return plugin;}
    public boolean getReady() {return ready;}
    public boolean getStarted() {return started;}
    public Game getGame() {return game;}
    public Player getCurrentDrawer() {return currentDrawer;}
    public String getOp() {return op;}
    public String getPre() {return pre;}
    public long getRoundLength() {return roundLength;}
    public List<Player> getParty() {return party;}
    public List<Player> getBans() {return bans;}
    public World getWorld() {return world;}
    public Random getRandom() {return random;}
    public String getCurrentWord() {return currentWord;}
    public List<Integer> getCurrentHint() {return currentHint;}
    public List<String> getWordList() {return wordList;}
    public List<String> getUsedWords() {return usedWords;}
    public List<Player> getCorrectGuessers() {return correctGuessers;}
    public Map<Player,Integer> getPoints() {return points;}
    public Scoreboard getScoreboard() {return scoreboard;}
    public Objective getObjective() {return objective;}

    public void setReady(boolean value) {ready = value;}
    public void setStarted(boolean value) {started = value;}
    public void setCurrentDrawer(Player value) {currentDrawer = value;}
    public void setCurrentWord(String value) {currentWord = value;}

    @Override
    public void onEnable() {
        plugin = this;
        world = Bukkit.getWorld(worldname);
        if (world == null) {
            System.out.println("Cannot find world by name "+worldname+"! Cancelling startup of plugin.");
            this.setEnabled(false); return;}

        scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager == null) {
            System.out.println("Bukkit scoreboard manager failed! Cancelling startup of plugin.");
            this.setEnabled(false); return;}
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("skribblPoints","dummy","§l§4Points");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        cmds.add("ready");cmds.add("cancel");cmds.add("start");cmds.add("end");cmds.add("kick");cmds.add("ban");cmds.add("unban");cmds.add("join");cmds.add("leave");
        getServer().getPluginManager().registerEvents(new Events(this), this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this,() -> {
            try {
                if (getDataFolder().mkdir()) {
                    File output = new File(getDataFolder(),"words.txt");
                    if (output.createNewFile()) {
                        InputStream input = getResource("words.txt");
                        if (input != null) {
                            java.nio.file.Files.copy(input,output.toPath(),StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            System.out.println("Cannot find default (built-in to plugin) words.txt! Cancelling startup of plugin.");
                            this.setEnabled(false); return;}}}
                BufferedReader reader = new BufferedReader(new FileReader(getDataFolder()+"\\words.txt"));
                while (reader.ready()) {wordList.add(reader.readLine());}
            } catch (IOException e) {e.printStackTrace();}});
    }

    @Override
    public void onDisable() {
        // game.mapRemoval();
        for (int x = 4029; x <= 4096; x++) for (int y = 126; y <= 193; y++) for (int z = -33; z <= 34; z++) {
            world.getBlockAt(x,y,z).setType(Material.AIR);}
    }

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
