package net.frozenblock.wilderwild.datagen;

import com.google.common.collect.Maps;
import com.mojang.serialization.JsonOps;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.init.WWBiomeTags;
import net.frozenblock.wilderwild.init.WWBiomes;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WilderWildBiomeModifierProvider {
    private static final RegistryAccess ACCESS = RegistryAccess.builtinCopy();
    private static final Registry<Biome> BIOME_REGISTRY = ACCESS.registryOrThrow(Registry.BIOME_REGISTRY);
    private static final Registry<PlacedFeature> PLACED_FEATURES = ACCESS.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
    private static final HashMap<ResourceLocation, BiomeModifier> MODIFIERS = Maps.newHashMap();

    public static JsonCodecProvider<BiomeModifier> bootstrap(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        addFlowers();
        addWildGrass();
        addTrees();
        addMushrooms();

        addModdedBiomesFeatures();

        addFeature("add_pollen", WWBiomeTags.HAS_POLLEN, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED);

        return JsonCodecProvider.forDatapackRegistry(dataGenerator, existingFileHelper, WilderWild.MOD_ID, RegistryOps.create(JsonOps.INSTANCE, ACCESS), ForgeRegistries.Keys.BIOME_MODIFIERS, MODIFIERS);
    }

    private static void addModdedBiomesFeatures() {
        addCypressWetlandsFeatures();
    }

    private static void addCypressWetlandsFeatures() {
        addFeature("add_cypress_wetlands_vegetal_features", WWBiomes.CYPRESS_WETLANDS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED, WilderPlacedFeatures.SEAGRASS_CYPRESS, WilderPlacedFeatures.SEEDING_DANDELION_CYPRESS, WilderPlacedFeatures.MILKWEED_CYPRESS, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP, VegetationPlacements.PATCH_PUMPKIN);
        addFeature("add_cypress_wetlands_paths", WWBiomes.CYPRESS_WETLANDS, GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH, WilderMiscPlaced.UNDER_WATER_CLAY_PATH);
//        addFeature("add_cypress_wetlands_springs", WWBiomes.CYPRESS_WETLANDS, GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
    }

    private static void addWildGrass() {
        addFeature("add_rare_grass", WWBiomeTags.HAS_NEW_RARE_GRASS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_GRASS_PLACED);
        addFeature("add_rare_large_fern_and_grass", WWBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE);
        addFeature("add_large_fern_and_grass", WWBiomeTags.HAS_LARGE_FERN_AND_GRASS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS);
    }

    private static void addFlowers() {
        addFeature("add_carnation", WWBiomeTags.HAS_CARNATION, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION);
        addFeature("add_datura", WWBiomeTags.HAS_DATURA, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA_BIRCH);
        addFeature("add_glory_of_the_snow", WWBiomeTags.HAS_GLORY_OF_THE_SNOW, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW);
        addFeature("add_brown_shelf_fungus", WWBiomeTags.HAS_BROWN_SHELF_FUNGUS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED);
        addFeature("add_red_shelf_fungus", WWBiomeTags.HAS_RED_SHELF_FUNGUS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED);
        addFeature("add_flowering_lily_pads", WWBiomeTags.HAS_FLOWERING_WATER_LILY, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY);
        addFeature("add_cattails", WWBiomeTags.HAS_CATTAIL, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL);
        addFeature("add_algae", BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_5);
        addFeature("add_rare_algae_patch", WWBiomeTags.HAS_ALGAE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE);
        addFeature("add_seeding_dandelions", WWBiomeTags.HAS_SEEDING_DANDELION, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION);
        addFeature("add_milkweed", WWBiomeTags.HAS_MILKWEED, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED);
        addFeature("add_plains_flowers", WWBiomeTags.HAS_PLAINS_FLOWERS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_PLAINS);
        addFeature("add_forest_berry_patch", WWBiomeTags.HAS_BERRY_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST);
    }

    private static void addTrees() {
        addFeature("add_fallen_birch", WWBiomeTags.HAS_FALLEN_BIRCH_TREES, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_PLACED);
        addFeature("add_fallen_spruce", BiomeTags.IS_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED);
        addFeature("add_fallen_oak_and_spruce", WWBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED);
        addFeature("add_fallen_oak_and_birch", WWBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED);
        addFeature("add_wild_trees", WWBiomeTags.HAS_WILD_TREES, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK);
        addFeature("add_short_spruce", WWBiomeTags.HAS_SHORT_SPRUCE, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_SPRUCE_PLACED);
    }

    private static void addMushrooms() {
        addFeature("add_huge_red_mushrooms", WWBiomeTags.HAS_HUGE_RED_MUSHROOMS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_RED_MUSHROOM_PLACED);
        addFeature("add_mushrooms", WWBiomeTags.HAS_MUSHROOMS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MUSHROOM_PLACED);
        addFeature("add_huge_brown_mushrooms", WWBiomeTags.HAS_BROWN_MUSHROOMS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BROWN_MUSHROOM_PLACED);
        addFeature("add_swamp_mushrooms", WWBiomeTags.HAS_SWAMP_MUSHROOMS, GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_MUSHROOMS_SWAMP);
    }

    private static void addFeature(String name, RegistryObject<Biome> biome, GenerationStep.Decoration step, Holder<PlacedFeature>... features) {
        MODIFIERS.put(new ResourceLocation(WilderWild.MOD_ID, name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(BIOME_REGISTRY.getOrCreateHolderOrThrow(biome.getKey())), featureSet(features), step));
    }

    private static void addFeature(String name, TagKey<Biome> tagKey, GenerationStep.Decoration step, Holder<PlacedFeature> features) {
        MODIFIERS.put(new ResourceLocation(WilderWild.MOD_ID, name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(new HolderSet.Named<>(BIOME_REGISTRY, tagKey), featureSet(features), step));
    }

    private static HolderSet<PlacedFeature> featureSet(Holder<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(holder -> PLACED_FEATURES.getOrCreateHolderOrThrow(holder.unwrapKey().get())).collect(Collectors.toList()));
    }

}