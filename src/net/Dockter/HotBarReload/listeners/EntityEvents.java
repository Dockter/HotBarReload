package net.Dockter.HotBarReload.listeners;

import net.Dockter.HotBarReload.Util;
import net.Dockter.HotBarReload.integration.PermissionsManager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityEvents
  implements Listener
{
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event)
  {
    if (!event.isCancelled())
    {
      if ((event instanceof EntityDamageByEntityEvent))
      {
        EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent)event;
        if ((subEvent.getDamager() instanceof Player))
        {
          Player player = (Player)subEvent.getDamager();
          if (player.getGameMode() != GameMode.CREATIVE)
          {
            if (!PermissionsManager.hasPermission(player, "autoitembarreload.disallow"))
            {
              int currentDurability = player.getItemInHand().getDurability();
              int maxDurability = player.getItemInHand().getType().getMaxDurability();
              if (maxDurability > 0)
              {
                if (currentDurability >= maxDurability)
                {
                  Util.ReloadItemBar(player, player.getItemInHand().getType());
                }

              }
              else if (player.getItemInHand().getAmount() == 0)
              {
                Util.ReloadItemBar(player, player.getItemInHand().getType());
              }
            }
          }
        }
      }
    }
  }
}