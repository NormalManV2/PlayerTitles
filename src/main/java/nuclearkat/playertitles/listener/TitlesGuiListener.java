package nuclearkat.playertitles.listener;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class TitlesGuiListener implements Listener {

    private final PlayerTitlesPlugin plugin;

    public TitlesGuiListener(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        String inventoryTitle = event.getView().getTitle();
        if (!inventoryTitle.startsWith("Select Title")) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();
        String clickedItemName = ChatColor.stripColor(itemMeta.getDisplayName());

        if ("Next Page".equalsIgnoreCase(clickedItemName)) {
            int currentPage = getPageIndex(event.getView().getTitle());
            plugin.getTitlesGUI().openNameTagGUI(player, currentPage + 1);

        } else if ("Previous Page".equalsIgnoreCase(clickedItemName)) {
            int currentPage = getPageIndex(event.getView().getTitle());
            plugin.getTitlesGUI().openNameTagGUI(player, currentPage - 1);

        } else if ("Click to Toggle Title".equalsIgnoreCase(clickedItemName)) {
            plugin.getTextDisplayHandler().removeTextDisplays(player.getUniqueId());
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have disabled your title!"));
        } else if (" ".equalsIgnoreCase(clickedItemName)){
            player.closeInventory();
        } else {
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();

            String titleKey = container.get(plugin.getTitleKey(), PersistentDataType.STRING);
            Title title = plugin.getTitleManager().getTitle(titleKey);

            if (title == null) {
                player.sendMessage(ChatColor.DARK_RED + "Title not found: THIS IS AN ERROR, PLEASE REPORT! <TitlesListener> ");
                return;
            }
            this.handleTitleSelection(player, title);
        }
    }

    private int getPageIndex(String title) {
        return Integer.parseInt(title.replaceAll("[^0-9]", "")) - 1;
    }

    private void handleTitleSelection(Player player, Title title) {
        if (!player.hasPermission(title.getPermission())) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You don't have permission to use this title!");
            return;
        }

        player.closeInventory();
        plugin.getTextDisplayHandler().removeTextDisplays(player.getUniqueId());
        plugin.getTextDisplayHandler().displayTitle(player, title);
    }
}
