package net.Dockter.removethis;

import java.io.File;
import java.io.PrintStream;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;

public class Ping
{
  private final File configFile = new File("plugins/PluginStats/config.yml");
  private final String logFile = "plugins/PluginStats/log.txt";
  private final YamlConfiguration config = YamlConfiguration.loadConfiguration(this.configFile);
  private Logger logger = null;

  public void init(Plugin plugin)
  {
    if ((configExists().booleanValue()) && (logExists().booleanValue()) && (!this.config.getBoolean("opt-out")))
    {
      plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Pinger(plugin, this.config.getString("guid"), this.logger), 10L, 1728000L);
      System.out.println("[" + plugin.getDescription().getName() + "] Usage statistics are being kept for this plugin. To opt-out for any reason, check plugins/PluginStats/config.yml");
    }
  }

  private Boolean configExists()
  {
    this.config.addDefault("opt-out", Boolean.valueOf(false));
    this.config.addDefault("guid", UUID.randomUUID().toString());
    if ((!this.configFile.exists()) || (this.config.get("guid", null) == null))
    {
      System.out.println("PluginStats is initializing for the first time. To opt-out for any reason check plugins/PluginStats/config.yml");
      try
      {
        this.config.options().copyDefaults(true);
        this.config.save(this.configFile);
      }
      catch (Exception ex) {
        System.out.println("Error creating PluginStats configuration file.");
        ex.printStackTrace();
        return Boolean.valueOf(false);
      }
    }
    return Boolean.valueOf(true);
  }

  private Boolean logExists()
  {
    try
    {
      FileHandler handler = new FileHandler("plugins/PluginStats/log.txt", true);
      this.logger = Logger.getLogger("com.randomappdev");
      this.logger.setUseParentHandlers(false);
      this.logger.addHandler(handler);
    }
    catch (Exception ex) {
      System.out.println("Error creating PluginStats log file.");
      ex.printStackTrace();
      return Boolean.valueOf(false);
    }
    return Boolean.valueOf(true);
  }
}