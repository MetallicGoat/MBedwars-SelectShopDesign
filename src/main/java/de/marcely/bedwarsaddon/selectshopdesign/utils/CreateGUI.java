package de.marcely.bedwarsaddon.selectshopdesign.utils;

import de.marcely.bedwars.api.game.shop.layout.ShopLayoutType;
import de.marcely.bedwars.tools.gui.ClickListener;
import de.marcely.bedwars.tools.gui.ClickableGUI;
import de.marcely.bedwars.tools.gui.GUIItem;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import de.marcely.bedwarsaddon.selectshopdesign.BedwarsAddonSelectShopDesign;
import de.marcely.bedwarsaddon.selectshopdesign.Message;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CreateGUI {

    private static final BedwarsAddonSelectShopDesign plugin = BedwarsAddonSelectShopDesign.getInstance();

    public static ClickableGUI shopLayoutSelector(Player player){
        final int guiHeight = plugin.getConfig().getInt("GUI.Height");

        final ChestGUI gui = new ChestGUI();
        gui.setHeight(guiHeight);
        gui.setTitle(Message.GUI_TITLE.getMessage());

        for(final ShopLayoutType shopLayoutType: ShopLayoutType.values()) {

            // ignore if it's beta or if the design has been disabled
            //TODO disable from config
            //BedwarsAddonSelectShopDesign.DESIGN_DISABLED.contains(shopLayoutType.name()) ||
            if (shopLayoutType == ShopLayoutType.PLUGIN)
                continue;

            // if there's no icon, use missing icon
            GUIAttributes GUIAttributes = new GUIAttributes(shopLayoutType);

            ItemStack is = GUIAttributes.getIconMaterial();

            ItemMeta meta = is.getItemMeta();
            assert meta != null;
            meta.setDisplayName(GUIAttributes.getIconName());

            // add glow effect&lore if player selected the design
            if (!BedwarsAddonSelectShopDesign.getPlayerDesign(player).equals(shopLayoutType.getLayout())){
                meta.setLore(GUIAttributes.getIconLore());
            }else{
                List<String> enabledLore = GUIAttributes.getIconLore();
                enabledLore.add(0, " ");
                enabledLore.add(0, Message.DESIGN_CHOSEN.getMessage());
                meta.setLore(enabledLore);
            }

            is.setItemMeta(meta);

            // add item to gui
            gui.open(player);


            //TODO test this
            gui.setItem(new GUIItem(is, (player12, b, b1) -> {
                if(!BedwarsAddonSelectShopDesign.PLAYER_DESIGNS.containsKey(player)){
                    BedwarsAddonSelectShopDesign.PLAYER_DESIGNS.put(player, shopLayoutType);

                    player.closeInventory();
                    player.sendMessage(Message.DESIGN_CHOOSE.getMessage().replace("{design}", shopLayoutType.name()));
                    //Util.playSound(player, Sound.LOBBY_VOTEARENA_VOTE);

                }else{
                    final ShopLayoutType pd = BedwarsAddonSelectShopDesign.PLAYER_DESIGNS.get(player);

                    if(!pd.equals(shopLayoutType)){
                        BedwarsAddonSelectShopDesign.PLAYER_DESIGNS.put(player, shopLayoutType);

                        player.closeInventory();
                        player.sendMessage(Message.DESIGN_CHOSEN.getMessage().replace("{design}", shopLayoutType.name()));
                        //Util.playSound(player, Sound.LOBBY_VOTEARENA_VOTE);

                    }else{
                        player.sendMessage(Message.DESIGN_CHOOSE_ALREADY.getMessage().replace("{design}", shopLayoutType.name()));
                        //Util.playSound(player, Sound.LOBBY_VOTEARENA_ALREADYVOTED);
                    }
                }

            }), GUIAttributes.getIconSlot());
        }
        return gui;
    }
}
