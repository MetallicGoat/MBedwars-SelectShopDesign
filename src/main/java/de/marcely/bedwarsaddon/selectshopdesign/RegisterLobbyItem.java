package de.marcely.bedwarsaddon.selectshopdesign;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.game.lobby.LobbyItem;
import de.marcely.bedwars.api.game.lobby.LobbyItemHandler;
import de.marcely.bedwarsaddon.selectshopdesign.utils.CreateGUI;
import org.bukkit.entity.Player;

public class RegisterLobbyItem {

    public static LobbyItemHandler getHandler(){
        return new LobbyItemHandler("SelectShopDesign", BedwarsAddonSelectShopDesign.getInstance()) {
            @Override
            public void handleUse(Player player, Arena arena, LobbyItem lobbyItem) {
                CreateGUI.shopLayoutSelector(player).open(player);
                //Util.playSound(player, Sound.LOBBY_VOTEARENA_OPEN);
            }

            @Override
            public boolean isVisible(Player player, Arena arena, LobbyItem lobbyItem) {
                return true;
            }
        };
    }
}
