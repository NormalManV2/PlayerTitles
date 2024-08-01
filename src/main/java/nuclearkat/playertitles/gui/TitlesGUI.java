package nuclearkat.playertitles.gui;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitlesGUI {

    private static final int TITLES_PER_PAGE = 28;
    private static final int INVENTORY_SIZE = 54;
    private static final int NEXT_PAGE_SLOT = 53;
    private static final int PREVIOUS_PAGE_SLOT = 45;
    private static final ItemStack BORDER_ITEM = createBorderItem();

    private final PlayerTitlesPlugin plugin;
    private List<Title> titles;
    private final Map<Integer, Inventory> pages;

    public TitlesGUI(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
        this.titles = new ArrayList<>(plugin.getTitleManager().getTitlesMap().values());
        this.pages = new HashMap<>();
        initPages();
    }

    public void updateTitlesList() {
        this.titles = new ArrayList<>(plugin.getTitleManager().getTitlesMap().values());
        pages.clear();
        initPages();
    }

    private void initPages() {
        int pageCount = (int) Math.ceil((double) titles.size() / TITLES_PER_PAGE);
        for (int i = 0; i < pageCount; i++) {
            Inventory page = Bukkit.createInventory(null, INVENTORY_SIZE, "Select Title (Page " + (i + 1) + ")");
            addBorder(page);

            int start = i * TITLES_PER_PAGE;
            int end = Math.min(start + TITLES_PER_PAGE, titles.size());
            for (int j = start; j < end; j++) {
                Title title = titles.get(j);
                ItemStack itemStack = title.build();
                page.addItem(itemStack);
            }

            page.setItem(PREVIOUS_PAGE_SLOT, createNavigationItem("&cPrevious Page"));
            page.setItem(NEXT_PAGE_SLOT, createNavigationItem("&cNext Page"));

            pages.put(i, page);
        }
    }

    private void addBorder(Inventory page) {
        for (int slot = 0; slot < page.getSize(); slot++) {
            if (slot < 9 || slot >= 45 || slot % 9 == 0 || slot % 9 == 8) {
                page.setItem(slot, BORDER_ITEM);
            }
        }
    }

    private static ItemStack createBorderItem() {
        ItemStack borderItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = borderItem.getItemMeta();
        if (meta == null) {
            return null;
        }
        meta.setDisplayName(" ");
        borderItem.setItemMeta(meta);
        return borderItem;
    }

    private ItemStack createNavigationItem(String name) {
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public void openNameTagGUI(Player player, int pageIndex) {
        Inventory page = pages.get(pageIndex);
        if (page != null) {
            player.openInventory(page);
        }
    }
}
