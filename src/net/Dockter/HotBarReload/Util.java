package net.Dockter.HotBarReload;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Util
{
  @SuppressWarnings("deprecation")
public static void ReloadItemBar(Player player, Material itemType)
  {
    int destSlot = -1;
    int srcSlot = -1;

    destSlot = player.getInventory().getHeldItemSlot();
    srcSlot = getSlot(destSlot, player.getInventory().all(itemType));

    if (srcSlot >= 0)
    {
      player.getInventory().setItem(destSlot, player.getInventory().getItem(srcSlot));
      player.getInventory().clear(srcSlot);
      player.updateInventory();
    }
  }

  private static int getSlot(int destSlot, HashMap<Integer, ?> items)
  {
    int count = 0;
    int srcSlot = -1;
    if (items != null)
    {
      if (items.size() > 0)
      {
        do
        {
          if (items.size() > count)
          {
            srcSlot = ((Integer)items.keySet().toArray()[count]).intValue();
          }
          count++;
        }while ((srcSlot == destSlot) && (items.size() > count));
      }
    }
    return srcSlot;
  }
}