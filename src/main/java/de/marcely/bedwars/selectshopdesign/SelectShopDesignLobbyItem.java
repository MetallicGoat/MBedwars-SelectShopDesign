package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.game.lobby.LobbyItem;
import de.marcely.bedwars.api.game.lobby.LobbyItemHandler;
import de.marcely.bedwars.api.player.PlayerDataAPI;
import de.marcely.bedwars.tools.gui.ClickListener;
import de.marcely.bedwars.tools.gui.GUI;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectShopDesignLobbyItem {
    public static LobbyItemHandler getHandler(SelectShopDesignPlugin plugin){
        return new LobbyItemHandler("SelectShopDesign", plugin) {
            @Override
            public void handleUse(Player player, Arena arena, LobbyItem lobbyItem) {
                shopLayoutSelector().open(player);
            }

            @Override
            public boolean isVisible(Player player, Arena arena, LobbyItem lobbyItem) {
                return true;
            }
        };
    }

    public static GUI shopLayoutSelector(){
        final ChestGUI gui = new ChestGUI();
        gui.setHeight(Config.guiHeight);
        gui.setTitle(Config.guiTitle);

        for(ShopLayoutAttributes attribute : Config.shopLayouts) {
            final ItemStack is = attribute.getIcon();
            final ItemMeta meta = is.getItemMeta();

            if(meta == null)
                continue;

            meta.setDisplayName(attribute.getLayoutName());
            meta.setLore(attribute.getLore());

            gui.setItem(is, attribute.getPosition(), new ClickListener() {
                @Override
                public void onClick(Player player, boolean b, boolean b1) {
                    PlayerDataAPI.get().getProperties(player, playerProperties -> {
                        playerProperties.set("selected_shop_layout", attribute.getId());
                    });
                }
            });
        }
        return gui;
    }
}
