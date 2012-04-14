package net.Dockter.HotBarReload.listeners;

import net.Dockter.HotBarReload.Util;
import net.Dockter.HotBarReload.integration.PermissionsManager;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockEvents implements Listener
{
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event)
  {
    if ((!event.isCancelled()) && (event.getPlayer().getGameMode() != GameMode.CREATIVE))
    {
      if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow"))
      {
        int currentDurability = event.getPlayer().getItemInHand().getDurability();
        int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
        if (maxDurability > 0)
        {
          if (currentDurability >= maxDurability)
          {
            Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType());
          }
        }
        else {
          Player player = event.getPlayer();
          if (player.getItemInHand().getAmount() == 1)
          {
            Util.ReloadItemBar(player, player.getItemInHand().getType());
          }
        }
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if ((!event.isCancelled()) && (event.getPlayer().getGameMode() != GameMode.CREATIVE))
    {
      if (!PermissionsManager.hasPermission(event.getPlayer(), "autoitembarreload.disallow"))
      {
        int currentDurability = event.getPlayer().getItemInHand().getDurability();
        int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
        if (maxDurability > 0)
        {
          if (currentDurability >= maxDurability)
          {
            Util.ReloadItemBar(event.getPlayer(), event.getPlayer().getItemInHand().getType());
          }
        }
      }
    }
  }
}