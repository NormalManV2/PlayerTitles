package nuclearkat.playertitles.conversation;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TitleCreationConversation extends StringPrompt {

    private final PlayerTitlesPlugin plugin;
    private final String key;
    private final String displayName;
    private final String tagContents;
    private final String permission;

    public TitleCreationConversation(PlayerTitlesPlugin plugin, String key, String displayName, String tagContents, String permission) {
        this.plugin = plugin;
        this.key = key;
        this.displayName = displayName;
        this.tagContents = tagContents;
        this.permission = permission;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.YELLOW + "Please enter the title lore, one line at a time. Type 'done' when finished.";
    }

    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, String input) {
        List<String> lore = (List<String>) context.getSessionData("lore");

        if (input == null) {
            plugin.getLogger().severe("INPUT IS NULL WHEN CREATING A TITLE");
            return null;
        }

        if (input.equalsIgnoreCase("done") || input.equalsIgnoreCase("finish")) {
            NamespacedKey titleKey = plugin.getTitleKey();
            NamespacedKey titleContentsKey = plugin.getTitleContentsKey();

            plugin.getTitleManager().createTitle(key, displayName, tagContents, permission, titleKey, titleContentsKey, lore.toArray(new String[0]));
            context.getForWhom().sendRawMessage(ChatColor.GREEN + "Title created with key: " + key);
            plugin.getTitleManager().applyTitle(key, (Player) context.getForWhom());
            return Prompt.END_OF_CONVERSATION;
        } else {
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(input);
            context.setSessionData("lore", lore);
            return this;
        }
    }
}
