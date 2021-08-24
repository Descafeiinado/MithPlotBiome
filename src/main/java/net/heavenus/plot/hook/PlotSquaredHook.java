package net.heavenus.plot.hook;

import com.intellectualcrafters.plot.api.PlotAPI;
import net.heavenus.plot.Main;
import net.heavenus.plot.hook.plotsquared.Listeners;
import org.bukkit.Bukkit;

public class PlotSquaredHook {

    protected static PlotAPI plotAPI;

    public static void setupHook() {
        plotAPI = new PlotAPI();
        Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
    }

    public static PlotAPI getPlotAPI() {
        return plotAPI;
    }
}
