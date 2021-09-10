package de.marcely.bedwarsaddon.selectshopdesign;

import org.bukkit.ChatColor;

import java.util.HashMap;

public enum Message {

	GUI_TITLE(ChatColor.AQUA + "Select Shop Design"),
	DESIGN_CHOOSE(ChatColor.GREEN + "SELECTED"),
	DESIGN_CHOOSE_ALREADY(ChatColor.RED + "You already selected {design}!"),
	DESIGN_CHOSEN(ChatColor.GOLD + "You selected this design");
	
	private static final HashMap<Message, String> customMessages = new HashMap<>();
	
	private final String message;
	
	Message(String msg){
		this.message = msg;
	}
	
	public String getMessage(){
		return ChatColor.translateAlternateColorCodes('&', customMessages
				.getOrDefault(this, this.message));
	}
	
	public void setCustomMessage(String msg){
		customMessages.put(this, msg);
	}
	
	public static Message getByName(String msg){
		for(Message m:values()){
			if(m.name().equalsIgnoreCase(msg))
				return m;
		}
		
		return null;
	}
}
