package net.heavenus.plot.hook.plotsquared;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.Subscribe;
import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import me.saiintbrisson.minecraft.View;
import net.heavenus.plot.menu.SelectBiomeMenu;
import net.heavenus.plot.utils.EventWaiter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class Listeners implements Listener {
    @EventHandler
    public void onNewPlotCreate(PlayerClaimPlotEvent event){
        if(event == null){
            System.out.println("event is null");
            return;
        }

        if(event.getPlayer() == null){
            System.out.println("player is null");
            return;
        }

        if(event.getPlot() == null){
            System.out.println("plot is null");
            return;
        }
        if(event.getPlayer().getName().equals("copinho_23")) {
            event.getPlayer().sendMessage("nothing null, starting");
        }
        new SelectBiomeMenu(event.getPlayer(), event.getPlot());
        if(event.getPlayer().getName().equals("copinho_23")) {
            event.getPlayer().sendMessage("menu opened");
        }
    }

}
