package net.heavenus.plot.utils;

import com.empcraft.biomes.BiomeHandler;
import com.empcraft.biomes.BiomeSelection;
import com.empcraft.biomes.ChunkLoc;
import com.intellectualcrafters.plot.generator.ClassicPlotWorld;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import com.intellectualcrafters.plot.object.RegionWrapper;
import com.intellectualcrafters.plot.util.block.GlobalBlockQueue;
import com.intellectualcrafters.plot.util.block.LocalBlockQueue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Set;

public class GenUtils {

    public static Runnable getGenerationRunnable(Plot plot, Player player){

        int height;

        PlotArea plotworld = plot.getArea();
        if (plotworld instanceof ClassicPlotWorld) {
            height = ((ClassicPlotWorld)plotworld).PLOT_HEIGHT;
        } else {
            height = 64;
        }

        final World world = Bukkit.getWorld((plot.getArea()).worldname);
        final String worldname = world.getName();
        final Set<RegionWrapper> allRegions = plot.getRegions();
        final ArrayDeque<RegionWrapper> regions = new ArrayDeque<>(allRegions);
        Runnable run = new Runnable() {
            public void run() {
                if (regions.size() == 0) {
                    player.sendMessage("&aPronto!");
                    return;
                }
                final Runnable task = this;
                RegionWrapper region = regions.poll();
                Location pos1 = new Location(world, region.minX, region.minY, region.minZ);
                Location pos2 = new Location(world, region.maxX, region.maxY, region.maxZ);
                BiomeSelection selection = new BiomeSelection(world, pos1, pos2, height);
                final int bxo = pos1.getBlockX();
                final int exo = pos2.getBlockX();
                final int bzo = pos1.getBlockZ();
                final int ezo = pos2.getBlockZ();
                final int bx = bxo >> 4;
                final int ex = exo >> 4;
                final int bz = bzo >> 4;
                final int ez = ezo >> 4;
                final HashMap<ChunkLoc, BlockState[][][]> blockMap = new HashMap<>();
                for (int x = bx; x <= ex; x++) {
                    for (int z = bz; z <= ez; z++) {
                        if (x == bx || x == ex || z == bz || z == ez) {
                            BlockState[][][] blocks = new BlockState[16][16][164];
                            for (int i = 0; i < 16; i++) {
                                for (int j = 0; j < 16; j++) {
                                    int X = (x << 4) + i;
                                    int Z = (z << 4) + j;
                                    if (X < bxo || X > exo || Z < bzo || Z > ezo) {
                                        boolean contains = false;
                                        for (RegionWrapper r : allRegions) {
                                            if (r.isIn(X, Z)) {
                                                contains = true;
                                                break;
                                            }
                                        }
                                        if (!contains)
                                            for (int y = 0; y < 164; y++) {
                                                Block block = world.getBlockAt(X, y, Z);
                                                short cid = (short)block.getTypeId();
                                                if (cid != 0)
                                                    blocks[i][j][y] = block.getState();
                                            }
                                    }
                                }
                            }
                            ChunkLoc chunk = new ChunkLoc(x, z);
                            blockMap.put(chunk, blocks);
                        }
                    }
                }
                BiomeHandler.generate(selection, player, new Runnable() {
                    public void run() {
                        TaskManager.taskLater(new Runnable() {
                            public void run() {
                                for (int x = bx; x <= ex; x++) {
                                    for (int z = bz; z <= ez; z++) {
                                        if (x == bx || x == ex || z == bz || z == ez) {
                                            LocalBlockQueue queue = GlobalBlockQueue.IMP.getNewQueue(worldname, false);
                                            ChunkLoc chunk = new ChunkLoc(x, z);
                                            BlockState[][][] blocks = (BlockState[][][])blockMap.get(chunk);
                                            for (int i = 0; i < 16; i++) {
                                                for (int j = 0; j < 16; j++) {
                                                    int X = (x << 4) + i;
                                                    int Z = (z << 4) + j;
                                                    if (X < bxo || X > exo || Z < bzo || Z > ezo) {
                                                        boolean contains = false;
                                                        for (RegionWrapper r : allRegions) {
                                                            if (r.isIn(X, Z)) {
                                                                contains = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!contains)
                                                            for (int y = 0; y < 164; y++) {
                                                                BlockState state = blocks[i][j][y];
                                                                if (state != null) {
                                                                    if (state.getClass().getName().endsWith("CraftBlockState")) {
                                                                        state.update(true, false);
                                                                    } else {
                                                                        queue.setBlock(X, y, Z, (short)state.getTypeId(), state.getRawData());
                                                                    }
                                                                } else {
                                                                    queue.setBlock(X, y, Z, 0, 0);
                                                                }
                                                            }
                                                    }
                                                }
                                            }
                                            queue.enqueue();
                                        }
                                    }
                                }
                            }
                        }, 20);
                        task.run();
                    }
                });
            }
        };
        return run;
    }

}
