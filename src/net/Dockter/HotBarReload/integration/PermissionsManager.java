package net.Dockter.HotBarReload.integration;

import com.epicsagaonline.bukkit.AutoItemBarReload.Log;
// import com.herocraftonline.dthielke.lists.Lists;
// import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsManager
{
  private static WorldsHolder GroupManager_Perms = null;
  private static PermissionHandler Permissions_Perms = null;
  private static PermissionManager PEX_Perms = null;
//  private static Lists Lists_Perms = null;
  private static Plugin plugin = null;

  public static void Init(Plugin inPlugin)
  {
    plugin = inPlugin;

    boolean permStart = startPermissionsEX();
    if (permStart)
    {
      Log.Write("Using 'PermissionsEX' plugin for permission management.");
    }
    if (!permStart)
    {
      permStart = startGroupManager();
      if (permStart)
      {
        Log.Write("Using 'GroupManager' plugin for permission management.");
      }
    }
    if (!permStart)
    {
      permStart = startLists();
      if (permStart)
      {
        Log.Write("Using 'Lists' plugin for permission management.");
      }
    }
    if (!permStart)
    {
      permStart = startPermissions();
      if (permStart)
      {
        Log.Write("Using 'Permissions' plugin for permission management.");
      }
    }
    if (!permStart)
    {
      Log.Write("Using Bukkit Permissions for permission management.");
    }
  }

  public static boolean hasPermission(Player player, String permission)
  {
    boolean result = false;
    try
    {
      if (Permissions_Perms != null)
      {
        result = Permissions_Perms.has(player, permission);
      } else if (PEX_Perms != null)
      {
        result = PEX_Perms.has(player, permission);
      } else if (GroupManager_Perms != null)
      {
        result = GroupManager_Perms.getWorldData(player).getPermissionsHandler().has(player, permission); } else {
      //  if (Lists_Perms != null)
      //  {
      //    PrivilegedList lst = Lists_Perms.getList(permission.replace("epiczones.", ""));
      //    if (lst != null)
      //    {
      //      Map users = lst.getUsers();
      //      if (users != null)
      //      {
      //        if (users.get(player.getName().toLowerCase()) != null)
      //        {
      //          return true;
      //        }
      //      }
      //    }
      //    return false;
      //  }

        result = player.hasPermission(permission);
      }
    }
    catch (Exception e) {
      Log.Write(e.getMessage());
    }
    return result;
  }

  public static ArrayList<String> getGroupNames(Player player)
  {
    ArrayList result = new ArrayList();
    if (PEX_Perms != null)
    {
      for (PermissionGroup pg : PEX_Perms.getGroups())
      {
        for (PermissionUser pu : pg.getUsers())
        {
          if (!pu.getName().equalsIgnoreCase(player.getName()))
            continue;
          result.add(pg.getName());
        }
      }
    }
    else if (GroupManager_Perms != null)
    {
      Collection<Group> grps = GroupManager_Perms.getWorldData(player).getGroupList();
      if (grps != null)
      {
        for (Group grp : grps)
        {
          result.add(grp.getName());
        }
      }
   // } else if (Lists_Perms != null)
   // {
   //   PrivilegedList[] lst = Lists_Perms.getLists(player.getName());
   //   if (lst != null)
   //   {
   //     for (PrivilegedList prv : lst)
    //    {
    //      result.add(prv.getName());
     //   }
    //  }
    } else if (Permissions_Perms != null)
    {
      String[] grps = Permissions_Perms.getGroups(player.getWorld().getName(), player.getName());
      if (grps != null)
      {
        for (String grp : grps)
        {
          result.add(0, grp);
        }
      }
    }
    else {
      if (player.isOp())
      {
        result.add("op");
      }
      result.add("default");
    }
    return result;
  }

  public static boolean startPermissions()
  {
    Plugin p = plugin.getServer().getPluginManager().getPlugin("Permissions");
    if (p != null)
    {
      if (!p.isEnabled())
      {
        plugin.getServer().getPluginManager().enablePlugin(p);
      }
      Permissions_Perms = ((Permissions)p).getHandler();
      return Permissions_Perms != null;
    }
    return false;
  }

  public static boolean startPermissionsEX()
  {
    Plugin p = plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
    if (p != null)
    {
      if (!p.isEnabled())
      {
        plugin.getServer().getPluginManager().enablePlugin(p);
      }
      PEX_Perms = PermissionsEx.getPermissionManager();
      return PEX_Perms != null;
    }
    return false;
  }

  public static boolean startGroupManager()
  {
    Plugin p = plugin.getServer().getPluginManager().getPlugin("GroupManager");
    if (p != null)
    {
      if (!p.isEnabled())
      {
        plugin.getServer().getPluginManager().enablePlugin(p);
      }
      GroupManager gm = (GroupManager)p;
      GroupManager_Perms = gm.getWorldsHolder();
      return GroupManager_Perms != null;
    }
    return false;
  }

  private static boolean startLists()
  {
    Plugin p = plugin.getServer().getPluginManager().getPlugin("Lists");
    if (p != null)
    {
      if (!p.isEnabled())
      {
        plugin.getServer().getPluginManager().enablePlugin(p);
      }
    //  Lists_Perms = (Lists)p;
      return true;
    }
    return false;
  }
}