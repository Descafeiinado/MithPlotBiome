package net.heavenus.plot.utils;

import java.util.HashMap;

import net.heavenus.plot.Main;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TaskManager {
    public static int taskRepeat(Runnable r, int interval) {
        return Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)Main.getInstance(), r, interval, interval);
    }

    public static MutableInt index = new MutableInt(0);

    public static HashMap<Integer, Integer> tasks = new HashMap<>();

    public static void taskAsync(Runnable r) {
        if (r == null)
            return;
        Main.getInstance().getServer().getScheduler().runTaskAsynchronously((Plugin)Main.getInstance(), r).getTaskId();
    }

    public static void task(Runnable r) {
        if (r == null)
            return;
        Main.getInstance().getServer().getScheduler().runTask((Plugin)Main.getInstance(), r).getTaskId();
    }

    public static void taskLater(Runnable r, int delay) {
        if (r == null)
            return;
        Main.getInstance().getServer().getScheduler().runTaskLater((Plugin)Main.getInstance(), r, delay).getTaskId();
    }

    public static void taskLaterAsync(Runnable r, int delay) {
        Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously((Plugin)Main.getInstance(), r, delay);
    }

    public static void cancelTask(int task) {
        if (task != -1)
            Bukkit.getScheduler().cancelTask(task);
    }
}
