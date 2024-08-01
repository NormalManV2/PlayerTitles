package nuclearkat.playertitles.command;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.conversation.TitleCreationConversation;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

public class TitleCreationCommand implements CommandExecutor {

    private final PlayerTitlesPlugin plugin;
    private final ConversationFactory conversationFactory;

    public TitleCreationCommand(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
        this.conversationFactory = new ConversationFactory(plugin)
                .withModality(true)
                .withEscapeSequence("cancel")
                .withLocalEcho(true)
                .withTimeout(30)
                .thatExcludesNonPlayersWithMessage("Only players can use this command.");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("titles.command.createtitle")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return false;
        }

        if (args.length < 4) {
            player.sendMessage(ChatColor.RED + "Usage: /createTitle <key> <displayName> <contents> <permission>");
            return false;
        }

        String key = args[0];
        String displayName = args[1];
        String tagContents = args[2];
        String permission = args[3];

        Conversation conversation = conversationFactory.withFirstPrompt(new TitleCreationConversation(plugin, key, displayName, tagContents, permission)).buildConversation(player);
        conversation.begin();

        return true;
    }
}
