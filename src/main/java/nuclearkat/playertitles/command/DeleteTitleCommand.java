package nuclearkat.playertitles.command;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.title.Title;
import nuclearkat.playertitles.util.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteTitleCommand implements CommandExecutor {

    private final PlayerTitlesPlugin plugin;

    public DeleteTitleCommand(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("titles.command.delete_title")){
            player.sendMessage("You don't have permission to use this command.");
            return false;
        }

        if (args.length > 2 || args.length < 1) {

            player.sendMessage("Usage: /deleteTitle <titleKey>");
            return false;
        }

        Title title = plugin.getTitleManager().getTitle(args[0]);

        if (title == null) {
            player.sendMessage("Title not found.");
            return false;
        }
        player.sendMessage(ColorUtil.convertLegacyColorCodes("<red>You have just deleted the title: " + title.getDisplayName()));
        plugin.getTextDisplayHandler().removeTextDisplays(player.getUniqueId());
        handleTitleDeletion(title);
        return true;
    }

    private void handleTitleDeletion(Title title){
        plugin.getTitleManager().removeTitle(title.getKey());
    }
}
