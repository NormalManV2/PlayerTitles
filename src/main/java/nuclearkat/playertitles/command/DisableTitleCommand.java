package nuclearkat.playertitles.command;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisableTitleCommand implements CommandExecutor {

    private final PlayerTitlesPlugin plugin;

    public DisableTitleCommand(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player player)) {
            return false;
        }

        plugin.getTextDisplayHandler().removeTextDisplays(player.getUniqueId());
        player.sendMessage(ChatColor.RED + "You have just disabled your title!");
        return true;
    }
}
