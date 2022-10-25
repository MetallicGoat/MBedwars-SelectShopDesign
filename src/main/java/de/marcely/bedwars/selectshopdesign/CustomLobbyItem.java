package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.game.lobby.LobbyItem;
import de.marcely.bedwars.api.game.lobby.LobbyItemHandler;
import org.bukkit.entity.Player;

public class CustomLobbyItem {
    public static LobbyItemHandler getHandler(SelectShopDesignPlugin plugin){
        return new LobbyItemHandler("SelectShopDesign", plugin) {
            @Override
            public void handleUse(Player player, Arena arena, LobbyItem lobbyItem) {
                SelectionGUI.open(player);
            }

            @Override
            public boolean isVisible(Player player, Arena arena, LobbyItem lobbyItem) {
                return true;
            }
        };
    }

}
