package de.marcely.bedwarsaddon.selectshopdesign;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.event.player.PlayerOpenShopEvent;
import de.marcely.bedwars.api.event.player.PlayerQuitArenaEvent;
import de.marcely.bedwars.api.game.shop.layout.ShopLayout;
import de.marcely.bedwars.api.game.shop.layout.ShopLayoutType;
import de.marcely.bedwarsaddon.selectshopdesign.utils.configupdater.ConfigUpdater;
import de.marcely.bedwarsaddon.selectshopdesign.utils.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BedwarsAddonSelectShopDesign extends JavaPlugin {

	private static BedwarsAddonSelectShopDesign instance;
	
	//public static HashMap<ShopLayoutType, ItemStack> DESIGN_ICON = new HashMap<>();
	public static List<ShopLayoutType> DESIGN_DISABLED = new ArrayList<>();
	public static ItemStack DESIGN_ICON_MISSING;
	public static HashMap<Player, ShopLayoutType> PLAYER_DESIGNS = new HashMap<>();

	@Override
	public void onEnable(){
		instance = this;
		loadConfig();
		
		// register default icon
		/*
		DESIGN_ICON.put(ShopLayoutType.NORMAL, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.HIVEMC, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.HYPIXEL, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.HYPIXEL_V2, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.GOMMEHD, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.MINESUCHT, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.REWINSIDE, new ItemStack(Material.POTATO));
		DESIGN_ICON.put(ShopLayoutType.BERGWERKLABS, new ItemStack(Material.POTATO));
		 */

		/*
		DESIGN_ICON.put(ShopDesignType.GommeHD.getData(), Util.getSkullItemstack("GommeHD", 1));
		DESIGN_ICON.put(ShopDesignType.HiveMC.getData(), new ItemStack(Material.GOLDEN_APPLE));
		DESIGN_ICON.put(ShopDesignType.HyPixel.getData(), new ItemStack(Material.WOOD_SWORD));
		DESIGN_ICON.put(ShopDesignType.Rewinside.getData(), Util.getSkullItemstack("Rewinside", 1));

		 */

		assert XMaterial.GUNPOWDER.parseMaterial() != null;
		DESIGN_ICON_MISSING = new ItemStack(XMaterial.GUNPOWDER.parseMaterial());
		
		// load after every plugin has been loaded because maybe there's a custom design
		BedwarsAPI.onReady(this::loadConfig);
		
		// register item
		BedwarsAPI.getGameAPI().registerLobbyItemHandler(RegisterLobbyItem.getHandler());
		
		// events
		Bukkit.getPluginManager().registerEvents(new Listener(){
			@EventHandler
			public void onPlayerQuitArenaEvent(PlayerQuitArenaEvent event){
				PLAYER_DESIGNS.remove(event.getPlayer());
			}
			
			@EventHandler
			public void onPlayerOpenShopEvent(PlayerOpenShopEvent event){
				event.setLayout(getPlayerDesign(event.getPlayer()));
			}
		}, this);
	}
	
	public static ShopLayout getPlayerDesign(Player player){
		if(PLAYER_DESIGNS.containsKey(player)){
			return PLAYER_DESIGNS.get(player).getLayout();
		}else{
			return BedwarsAPI.getGameAPI().getDefaultShopLayout();
		}
	}

	public static BedwarsAddonSelectShopDesign getInstance(){
		return instance;
	}

	private void loadConfig(){
		saveDefaultConfig();
		File configFile = new File(getDataFolder(), "config.yml");

		try {
			ConfigUpdater.update(this, "config.yml", configFile, Collections.singletonList("Nothing"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reloadConfig();
	}
}
