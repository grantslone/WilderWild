package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.block.SculkJawBlock;
import net.frozenblock.wilderwild.block.entity.SculkEchoerPhase;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class RegisterBlocks {
    // HOLLOW LOGS
    public static final Block HOLLOWED_OAK_LOG = registerBlock("hollowed_oak_log", createHollowedLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_SPRUCE_LOG = registerBlock("hollowed_spruce_log", createHollowedLogBlock(MapColor.SPRUCE_BROWN, MapColor.BROWN), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_ACACIA_LOG = registerBlock("hollowed_acacia_log", createHollowedLogBlock(MapColor.ORANGE, MapColor.STONE_GRAY), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_JUNGLE_LOG = registerBlock("hollowed_jungle_log", createHollowedLogBlock(MapColor.DIRT_BROWN, MapColor.SPRUCE_BROWN), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_BIRCH_LOG = registerBlock("hollowed_birch_log", createHollowedLogBlock(MapColor.PALE_YELLOW, MapColor.OFF_WHITE), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_DARK_OAK_LOG = registerBlock("hollowed_dark_oak_log", createHollowedLogBlock(MapColor.BROWN, MapColor.BROWN), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_MANGROVE_LOG = registerBlock("hollowed_mangrove_log", createHollowedLogBlock(MapColor.RED, MapColor.SPRUCE_BROWN), ItemGroup.BUILDING_BLOCKS);

    // SCULK
    public static final Block SCULK_ECHOER = registerBlock("sculk_echoer", new SculkEchoerBlock(AbstractBlock.Settings.of(Material.SCULK, MapColor.CYAN).strength(3.0F, 3.0F).sounds(BlockSoundGroup.SCULK_SHRIEKER).luminance((state) -> {
        return 0;
    }).emissiveLighting((state, world, pos) -> {
        return SculkEchoerBlock.getPhase(state) == SculkEchoerPhase.ACTIVE;
    }), 1), ItemGroup.REDSTONE);
    public static final Block SCULK_JAW = registerBlock("sculk_jaw",
            new SculkJawBlock(AbstractBlock.Settings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK)),
            ItemGroup.REDSTONE);
    public static final Block SCULK_BONE = registerBlock("sculk_bone",
            new PillarBlock(AbstractBlock.Settings.of(Material.STONE,
                    MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(BlockSoundGroup.SCULK_CATALYST)),ItemGroup.DECORATIONS);

    // FLOWERS
    public static final Block DATURA = registerBlock("datura",
            new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0f).nonOpaque()), ItemGroup.DECORATIONS);
    public static final Block CATTAIL = registerBlock("cattail",
            new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0f).nonOpaque()), ItemGroup.DECORATIONS);
    public static final Block POTTED_CARNATION = registerBlockWithoutBlockItem("potted_carnation",
            new FlowerPotBlock(RegisterBlocks.CARNATION,
    FabricBlockSettings.copy(Blocks.POTTED_DANDELION).nonOpaque()), ItemGroup.DECORATIONS);
    public static final Block CARNATION = registerBlock("carnation",
            new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 12,
                    FabricBlockSettings.copy(Blocks.DANDELION).strength(0.0f).nonOpaque()), ItemGroup.DECORATIONS);

    private static Block registerBlockWithoutBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, name), block);
    }

    private static BlockItem registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new HollowedLogBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> {
            return state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor;
        }).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    }


    public static void RegisterBlocks() {
        WilderWild.LOGGER.info("Registering Blocks for " + WilderWild.MOD_ID);
    }
}