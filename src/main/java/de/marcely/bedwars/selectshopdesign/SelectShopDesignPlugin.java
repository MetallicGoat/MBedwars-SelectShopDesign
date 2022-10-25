package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.event.player.PlayerOpenShopEvent;
import de.marcely.bedwars.api.game.shop.layout.ShopLayout;
import de.marcely.bedwars.api.player.PlayerDataAPI;
import de.marcely.bedwars.api.player.PlayerProperties;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class SelectShopDesignPlugin extends JavaPlugin {

	private static final byte MBEDWARS_API_NUM = 14;
	private static final String MBEDWARS_API_NAME = "5.0.14.2";

	@Getter private SelectShopDesignAddon addon;

	@Override
	public void onEnable(){
		if(!validateMBedwars()) return;
		if(!registerAddon()) return;

		Config.load(this);

		addon.registerMessageMappings();
		addon.registerCommands();

		// register item
		BedwarsAPI.getGameAPI().registerLobbyItemHandler(CustomLobbyItem.getHandler(this));
		
		// event
		Bukkit.getPluginManager().registerEvents(new Listener(){
			@EventHandler
			public void onPlayerOpenShopEvent(PlayerOpenShopEvent event){
				final Player player = event.getPlayer();
				final Optional<PlayerProperties> playerData = PlayerDataAPI.get().getPropertiesNow(player.getUniqueId());
				if(!playerData.isPresent())
					return;

				final Optional<String> layoutName = playerData.get().get("selected_shop_layout");
				if(!layoutName.isPresent())
					return;

				final ShopLayout layout = GameAPI.get().getShopLayout(layoutName.get());
				if(layout != null)
					event.setLayout(layout);
			}
		}, this);
	}

	private boolean validateMBedwars(){
		try{
			final Class<?> apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
			final int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

			if(apiVersion < MBEDWARS_API_NUM)
				throw new IllegalStateException();
		}catch(Exception e){
			getLogger().warning("Sorry, your installed version of MBedwars is not supported. Please install at least v" + MBEDWARS_API_NAME);
			Bukkit.getPluginManager().disablePlugin(this);

			return false;
		}

		return true;
	}

	private boolean registerAddon(){
		this.addon = new SelectShopDesignAddon(this);

		if(!this.addon.register()){
			getLogger().warning("It seems like the addon is already running. Please delete the duplicate and try again");
			Bukkit.getPluginManager().disablePlugin(this);

			return false;
		}

		return true;
	}
}
