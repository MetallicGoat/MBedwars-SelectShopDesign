package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.message.Message;
import de.marcely.bedwars.api.player.PlayerDataAPI;
import de.marcely.bedwars.tools.gui.CenterFormat;
import de.marcely.bedwars.tools.gui.ClickListener;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectionGUI {
    public static void open(Player player){
        final ChestGUI gui = new ChestGUI();
        final int height = (int) Math.ceil((float) Config.shopLayouts.size() / 9);

        gui.setHeight(height);
        gui.setTitle(Message.build(Config.guiTitle).done(player));

        for(ShopLayoutAttributes attributes : Config.shopLayouts) {
            final ItemStack icon = attributes.getIcon();

            if(GameAPI.get().getShopLayout(attributes.getId()) == null)
                continue;

            PlayerDataAPI.get().getProperties(player, playerProperties -> {
                final Optional<String> currLayout = playerProperties.get("selected_shop_layout");
                final boolean selected = (currLayout.isPresent() && attributes.getId().equalsIgnoreCase(currLayout.get())) || (!currLayout.isPresent() && attributes.getId().equalsIgnoreCase(GameAPI.get().getDefaultShopLayout().getName()));

                formatItemStack(icon, attributes, player, selected);

                gui.addItem(icon, new ClickListener() {
                    @Override
                    public void onClick(Player player, boolean b, boolean b1) {
                        PlayerDataAPI.get().getProperties(player, playerProperties -> {
                            playerProperties.set("selected_shop_layout", attributes.getId());
                            open(player);
                        });
                    }
                });
            });
        }

        // Formatting
        for(int i = 0; i < height; i++)
            gui.formatRow(i, CenterFormat.ALIGNED);

        gui.open(player);
    }

    public static void formatItemStack(ItemStack icon, ShopLayoutAttributes attributes, Player player, boolean selected) {
        final ItemMeta meta = icon.getItemMeta();

        if(meta == null)
            return;

        final String title = selected ? Config.selectedLayoutTitle : Config.unselectedLayoutTitle;
        final List<String> lore = selected ? Config.selectedLayoutLore : Config.unselectedLayoutLore;
        final List<String> loreFormatted = new ArrayList<>();

        for (String loreLine : lore)
            loreFormatted.add(Message.build(loreLine)
                    .placeholder("design", attributes.getLayoutName())
                    .placeholder("description", attributes.getDescription())
                    .done(player));

        meta.setDisplayName(Message.build(title)
                .placeholder("design", attributes.getLayoutName())
                .placeholder("description", attributes.getDescription())
                .done(player)
        );

        meta.setLore(loreFormatted);
        icon.setItemMeta(meta);
    }
}
