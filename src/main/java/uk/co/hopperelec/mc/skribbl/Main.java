package uk.co.hopperelec.mc.skribbl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {
    public static Plugin plugin = null;
    List<String> cmds = new ArrayList<>();
    static List<List<Player>> party = new ArrayList<>();
    static List<List<Player>> bans = new ArrayList<>();
    static Map<Player,List<Player>> invites = new HashMap<>();
    SkribblCommands skribblCmds = new SkribblCommands();
    String op = "HopperElecYT";
    public static List<List<Player>> getParty() {return party;}
    public static Map<Player,List<Player>> getInvites() {return invites;}
    public static Plugin getPlugin() {return plugin;}

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        cmds.add("start"); cmds.add("kick");cmds.add("ban");cmds.add("unban");cmds.add("join");cmds.add("leave");
    }

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (author instanceof Player) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                author.sendMessage("§e§l/skribbl §r§4- §eCore command for HopperElecYT's Skribbl plugin");
                author.sendMessage("§e§l/skribbl help §r§4- §eDisplays this page");
                if (((Player) author).getDisplayName().equalsIgnoreCase(op)) {
                    author.sendMessage("§e§l/skribbl start §r§4- §eStarts a Skribbl game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl kick §r§4- §eStarts a Skribbl game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl ban §r§4- §eStarts a Skribbl game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl unban §r§4- §eStarts a Skribbl game ("+op+" only!)");}
                author.sendMessage("§e§l/skribbl join §r§4- §eLists Skribbl party commands");
                author.sendMessage("§e§l/skribbl leave §r§4- §eLists Skribbl party commands");
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
