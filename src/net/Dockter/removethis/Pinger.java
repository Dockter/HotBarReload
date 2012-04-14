package net.Dockter.removethis;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

class Pinger
  implements Runnable
{
  private Plugin plugin;
  private String guid;
  private Logger logger;

  public Pinger(Plugin plugin, String guid, Logger theLogger)
  {
    this.plugin = plugin;
    this.guid = guid;
    this.logger = theLogger;
  }

  public void run()
  {
    pingServer();
  }

  private void pingServer()
  {
    String authors = "";
    try
    {
      for (String auth : this.plugin.getDescription().getAuthors())
      {
        authors = authors + " " + auth;
      }
      authors = authors.trim();

      String url = String.format("http://pluginstats.randomappdev.com/ping.php?snam=%s&sprt=%s&shsh=%s&sver=%s&spcnt=%s&pnam=%s&pmcla=%s&paut=%s&pweb=%s&pver=%s", new Object[] { URLEncoder.encode(this.plugin.getServer().getServerName(), "UTF-8"), Integer.valueOf(this.plugin.getServer().getPort()), this.guid, URLEncoder.encode(Bukkit.getVersion(), "UTF-8"), Integer.valueOf(this.plugin.getServer().getOnlinePlayers().length), URLEncoder.encode(this.plugin.getDescription().getName(), "UTF-8"), URLEncoder.encode(this.plugin.getDescription().getMain(), "UTF-8"), URLEncoder.encode(authors, "UTF-8"), URLEncoder.encode(this.plugin.getDescription().getWebsite().toLowerCase().replace("http://", "").replace("https://", ""), "UTF-8"), URLEncoder.encode(this.plugin.getDescription().getVersion(), "UTF-8") });

      new URL(url).openConnection().getInputStream();
      this.logger.log(Level.INFO, "PluginStats pinged the central server.");
    }
    catch (Exception ex)
    {
      this.logger.log(Level.SEVERE, ex.getStackTrace().toString());
    }
  }
}