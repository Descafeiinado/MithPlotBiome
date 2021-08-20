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
        SelectBiomeMenu view = new SelectBiomeMenu();
        view.open((Player) event.getPlayer(), ImmutableMap.of("event", event));
    }

}
