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
import net.heavenus.plot.Main;
import net.heavenus.plot.biomes.Biomes;
import net.heavenus.plot.menu.library.PagedPlayerMenu;
import net.heavenus.plot.utils.BukkitUtils;
import net.heavenus.plot.utils.GenUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SelectBiomeMenu extends PagedPlayerMenu {

    public HashMap<ItemStack, Biomes> hashMap = new HashMap<>();
    private Plot plot;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(getCurrentInventory())) {
            evt.setCancelled(true);
            if (evt.getWhoClicked().equals(this.player) &&
                    evt.getClickedInventory() != null && evt.getClickedInventory().equals(getCurrentInventory())) {
                ItemStack item = evt.getCurrentItem();
                if (item != null && item.getType() != Material.AIR)
                    if (evt.getSlot() == this.previousPage) {
                        openPrevious();
                    } else if (evt.getSlot() == this.nextPage) {
                        openNext();
                    } else if (evt.getSlot() == 49) {
                        player.closeInventory();
                    } else {

                        if(plot == null){
                            return;
                        }

                        Biomes biomes = this.hashMap.get(item);

                        if (biomes != null) {
                            if (player.getName().equals("copinho_23")) {
                                player.sendMessage("ok, loading");
                            }

                            if (player.getName().equals("copinho_23")) {
                                player.sendMessage("Debug data:");
                            }

                            PlotArea plotArea = plot.getArea();

                            if (BiomeHandler.isRunning) {
                                if (player.getName().equals("copinho_23")) {
                                    player.sendMessage("is running");
                                }
                                if (player.getName().equals("copinho_23")) {
                                    player.sendMessage("alreadyexist");
                                }
                                player.sendMessage("§cJá existe uma plot em processo de geração de bioma, por favor, aguarde.");
                                if (BiomeHandler.runner.equals(player.getName()) && !BiomeHandler.runners.contains(player.getName()))
                                    BiomeHandler.runners.add(player.getName());
                                return;
                            }
                            if (player.getName().equals("copinho_23")) {
                                player.sendMessage("generating");
                            }
                            BiomeHandler.getNewGenerator(biomes.getBiome(), new Random(System.nanoTime()).nextLong());
                            if (player.getName().equals("copinho_23")) {
                                player.sendMessage("newgeninstanced");
                            }
                            GenUtils.getGenerationRunnable(plot, player).run();
                            if (player.getName().equals("copinho_23")) {
                                player.sendMessage("running runnable");
                            }
                            this.plot = null;
                            player.closeInventory();
                        }
                    }
            }
        }
    }

    public SelectBiomeMenu(Player player, Plot plot) {
        super(player, "Selecionar bioma", 6);
        List<ItemStack> items = new ArrayList<>();
        this.previousPage = 19;
        this.nextPage = 26;
        this.plot = plot;
        onlySlots(
                10, 11, 12, 13, 14, 15, 16, 19, 20, 21,
                22, 23, 24, 25, 28, 29, 30, 31, 32, 33,
                34);
        removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cCancelar"), 49);
        Biomes.getBiomes().forEach(biomes -> {
            items.add(biomes.getIcon());
            this.hashMap.put(biomes.getIcon(), biomes);
        });

        setItems(items);
        register(Main.getInstance());
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                open();
            }
        }, 60L);
    }

    public void cancel() {
        HandlerList.unregisterAll((Listener) this);
        this.plot = null;
        this.hashMap.clear();
        this.hashMap = null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            if (this.plot != null) {
                plot.deletePlot(new Runnable() {
                    @Override
                    public void run() {
                        evt.getPlayer().sendMessage("§cSua plot foi excluída pois você não selecionou um bioma.");
                    }
                });
            }
            cancel();
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            if (this.plot != null) {
                plot.deletePlot(new Runnable() {
                    @Override
                    public void run() {
                        evt.getPlayer().sendMessage("§cSua plot foi excluída pois você não selecionou um bioma.");
                    }
                });
            }
            cancel();
        }
    }

}
