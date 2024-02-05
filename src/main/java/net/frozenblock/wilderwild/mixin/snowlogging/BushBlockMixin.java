/*
 * Copyright 2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BushBlock.class)
public class BushBlockMixin extends Block {
	@Unique
	private static final IntegerProperty WILDERWILD$SNOW_LAYERS = RegisterProperties.SNOW_LAYERS;

	public BushBlockMixin(Properties properties) {
		super(properties);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || (SnowloggingUtils.isSnowlogged(state));
	}

	@Unique
	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WILDERWILD$SNOW_LAYERS);
	}

	@Unique
	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		boolean canBeReplacedWithSnow = SnowloggingUtils.canBeReplacedWithSnow(state, context);
		if (SnowloggingUtils.isSnowlogged(state)) {
			return canBeReplacedWithSnow;
		}
		return canBeReplacedWithSnow || super.canBeReplaced(state, context);
	}

	@Unique
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
		BlockState placementState = super.getStateForPlacement(context);
		if (placementState != null && SnowloggingUtils.supportsSnowlogging(placementState) && blockState.is(Blocks.SNOW)) {
			int layers = blockState.getValue(BlockStateProperties.LAYERS);
			if (layers <= 7) {
				placementState = placementState.setValue(WILDERWILD$SNOW_LAYERS, layers);
			}
		}
		return placementState;
	}

	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
		boolean snowlogged = SnowloggingUtils.isSnowlogged(state);
		BlockState stateWithoutSnow = snowlogged ? state.setValue(WILDERWILD$SNOW_LAYERS, 0) : state;
		super.destroy(level, pos, stateWithoutSnow);
		if (snowlogged) {
			level.setBlock(pos, SnowloggingUtils.getSnowEquivalent(state), Block.UPDATE_ALL);
		}
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		BlockState stateWithoutSnow = SnowloggingUtils.isSnowlogged(state) ? state.setValue(WILDERWILD$SNOW_LAYERS, 0) : state;
		super.playerDestroy(level, player, pos, stateWithoutSnow, blockEntity, stack);
	}

	/*
	@Unique
	@Override
	public SoundType getSoundType(BlockState state) {
		return wilderWild$isSnowlogged(state) ? Blocks.SNOW.getSoundType(wilderWild$getSnowEquivalent(state)) : super.getSoundType(state);
	}
	 */

	@Unique
	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.randomTick(state, level, pos, random);
		if (SnowloggingUtils.isSnowlogged(state)) {
			if (level.getBrightness(LightLayer.BLOCK, pos) > 11) {
				dropResources(SnowloggingUtils.getSnowEquivalent(state), level, pos);
				level.setBlock(pos, state.setValue(WILDERWILD$SNOW_LAYERS, 0), Block.UPDATE_ALL);
			}
		}
	}

	@ModifyVariable(method = "updateShape", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	public BlockState wilderWild$updateShape(BlockState instance, BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return SnowloggingUtils.onUpdateShape(instance, level, pos);
	}

}
