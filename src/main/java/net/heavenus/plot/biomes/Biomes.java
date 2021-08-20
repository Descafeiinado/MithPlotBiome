package net.heavenus.plot.biomes;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.heavenus.plot.utils.BukkitUtils;
import net.heavenus.plot.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Biomes {

    protected String name, head;
    protected Integer slot;
    protected Biome biome;

    public Biomes(String name, Integer slot, String head, Biome biome) {
        this.name = name;
        this.slot = slot;
        this.head = head;
        this.biome = biome;
    }

    public String getName() {
        return name;
    }

    public Biome getBiome() {
        return biome;
    }

    public Integer getSlot() {
        return slot;
    }

    public String getHead() {
        return head;
    }

    public ItemStack getIcon() {
        return new ItemBuilder(Material.SKULL_ITEM).durability(3).skinFromValue(this.head).name("§aBioma: §f" + this.name).
                lore("§8Bioma", "", " §7Defina dinâmicamente o bioma", " §7que a sua plot receberá.", "", "§4§l * AVISO: §fUtilize com cautela,", " §festa ação não pode ser desfeita.", "", "§eClique para definir.")
                .build();
    }

    private static ArrayList<Biomes> biomes = new ArrayList<>();

    public static ArrayList<Biomes> getBiomes() {
        return biomes;
    }

    public static Biomes getBiomesFromSlot(Integer slot) {
        return biomes.stream().filter(predicate -> predicate.getSlot().equals(slot)).findAny().orElse(null);
    }

    public static void setupBiomes() {
        biomes.add(new Biomes("Planície", 10, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ0OWI5MzE4ZTMzMTU4ZTY0YTQ2YWIwZGUxMjFjM2Q0MDAwMGUzMzMyYzE1NzQ5MzJiM2M4NDlkOGZhMGRjMiJ9fX0=", Biome.PLAINS));
        biomes.add(new Biomes("Floresta", 11, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNjNTJlYWU3NDdjYWQ1YjRmZDE5YjFhMjNiMzlhMzM2YjYyZWQ0MjI3OTdhNjIyZDA0NWY0M2U1ZDM4In19fQ==", Biome.COLD_TAIGA));
        biomes.add(new Biomes("Deserto", 12, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxOTkzZTRjZmRhMTUzZWFmN2RjMTM4ZDUyYmJhNWMyODNkMDE2MzI2MDIyNjIxNjE3NzZmMGY0Yjg2YSJ9fX0=", Biome.DESERT));
        biomes.add(new Biomes("Nevasca", 13, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxMmJmNzY4MTFkZjJjMDIwY2VjZDM0Y2Q4ZDFmMmFlZDI4YTU2MDY3NDMyODEzMGQyZDM1YTBlOWI1YzdiOSJ9fX0=", Biome.ICE_PLAINS));
        biomes.add(new Biomes("Ilha de Cogumelos", 14, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE0NWQxYjQxN2NiZGRjMjE3NjdiMDYwNDRlODk5YjI2NmJmNzhhNjZlMjE4NzZiZTNjMDUxNWFiNTVkNzEifX19", Biome.MUSHROOM_ISLAND));
        biomes.add(new Biomes("Savanna", 15, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjExNmI5ZDhkZjM0NmEyNWVkZDA1Zjg0MmU3YTkzNDViZWFmMTZkY2E0MTE4YWJmNWE2OGM3NWJjYWFlMTAifX19", Biome.SAVANNA));
    }

}
