package net.heavenus.plot;

import net.heavenus.plot.hook.PlotSquaredHook;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        PlotSquaredHook.setupHook();
        getLogger().info("O plugin foi iniciado.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("O plugin foi desativado.");
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

}
