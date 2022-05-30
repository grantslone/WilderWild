package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String spawnGroup = remapper.mapClassName("intermediary","net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(spawnGroup).addEnum("FIREFLIES", "fireflies", 30, true, false, 64).build();
    }
}
