package net.Dockter.HotBarReload.listeners;

import net.Dockter.HotBarReload.Util;
import net.Dockter.HotBarReload.integration.PermissionsManager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerEvents implements Listener {
	
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if ((!event.isCancelled()) && (event.getPlayer().getGameMode() != GameMode.CREATIVE)) {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow")) {
          int currentDurability = event.getPlayer().getItemInHand().getDurability();
          int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
          
          if (maxDurability > 0) {
            if (currentDurability >= maxDurability) {
              Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType());
            }
          } else {
            Player player = event.getPlayer();
            
            if (player.getItemInHand().getAmount() == 0) {
              Util.ReloadItemBar(player, player.getItemInHand().getType());
            }
          }
        }
      }
    }
  }

  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if ((!event.isCancelled()) && (event.getPlayer().getGameMode() != GameMode.CREATIVE)) {
      Player player = event.getPlayer();
      if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow")) {
        if (player.getItemInHand().getAmount() == 0) {
          Util.ReloadItemBar(player, event.getItemDrop().getItemStack().getType());
        }
      }
    }
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if ((!event.isCancelled()) && (event.getPlayer().getGameMode() != GameMode.CREATIVE)) {
      if (event.getRightClicked() != null) {
        if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow")) {
          int currentDurability = event.getPlayer().getItemInHand().getDurability();
          int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
          
          if (maxDurability > 0) {
            if (currentDurability >= maxDurability) {
              Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType());
            }
          } else {
            Player player = event.getPlayer();
            if (player.getItemInHand().getAmount() == 0) {
              Util.ReloadItemBar(player, player.getItemInHand().getType());
            }
          }
        }
      }
    }
  }

  @EventHandler
  public void onPlayerFish(PlayerFishEvent event) {
    if ((!event.isCancelled()) && (event.getPlayer().getGameMode() != GameMode.CREATIVE)) {
      if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow")) {
        int currentDurability = event.getPlayer().getItemInHand().getDurability();
        int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
        
        if (currentDurability >= maxDurability) {
          Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType());
        }
      }
    }
  }

  @EventHandler
  public void onPlayerEggThrow(PlayerEggThrowEvent event) {
    if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
      if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow")) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getAmount() == 0) {
          Util.ReloadItemBar(player, player.getItemInHand().getType());
        }
      }
    }
  }
}