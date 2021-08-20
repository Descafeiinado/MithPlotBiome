package net.heavenus.plot.menu;

import com.empcraft.biomes.BiomeHandler;
import com.empcraft.biomes.BiomeSelection;
import com.google.common.eventbus.Subscribe;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import com.plotsquared.bukkit.events.PlotEvent;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewSlotContext;
import net.heavenus.plot.biomes.Biomes;
import net.heavenus.plot.menu.library.PagedPlayerMenu;
import net.heavenus.plot.utils.GenUtils;
import org.bukkit.entity.Player;

import java.util.Random;

public class SelectBiomeMenu extends View {
    private boolean success;
    public SelectBiomeMenu() {
        super(3, "Selecionar bioma");
        Biomes.getBiomes().forEach(biomes -> {
            slot(biomes.getSlot(), biomes.getIcon());
        });
    }

    @Override
    protected void onClick(ViewSlotContext context) {
        Biomes biomes = Biomes.getBiomesFromSlot(context.getSlot());
        PlayerClaimPlotEvent plotEvent = context.get("event");
        Player player = context.getPlayer();

        if (plotEvent == null || plotEvent.isCancelled()) return;


        plotEvent.setCancelled(false);

        Plot plot = plotEvent.getPlot();
        PlotArea plotArea = plot.getArea();

        this.success = true;

        if (BiomeHandler.isRunning) {
            player.sendMessage("§cJá existe uma plot em processo de geração de bioma, por favor, aguarde.");
            if (BiomeHandler.runner.equals(player.getName()) && !BiomeHandler.runners.contains(player.getName()))
                BiomeHandler.runners.add(player.getName());
            return;
        }
        BiomeHandler.getNewGenerator(biomes.getBiome(), new Random(System.nanoTime()).nextLong());
        GenUtils.getGenerationRunnable(plot, player).run();

    }

    @Override
    protected void onClose(ViewContext context) {
        if(!success){
            PlayerClaimPlotEvent plotEvent = context.get("event");
            plotEvent.setCancelled(true);
        }
    }
}
