/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class AmbienceAndMiscConfig {

	public static final Config<AmbienceAndMiscConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			AmbienceAndMiscConfig.class,
			configPath("misc", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@EntrySyncData("modifyAdvancements")
	public boolean modifyAdvancements = true;

	@EntrySyncData(value = "cloudMovement", behavior = SyncBehavior.UNSYNCABLE)
	public boolean cloudMovement = true;

	@EntrySyncData(value = "windParticles", behavior = SyncBehavior.UNSYNCABLE)
	public boolean windParticles = true;

	@EntrySyncData(value = "windDisturbanceParticles", behavior = SyncBehavior.UNSYNCABLE)
	public boolean windDisturbanceParticles = true;

	@EntrySyncData(value = "particleWindMovement", behavior = SyncBehavior.UNSYNCABLE)
	public int particleWindMovement = 100;

	@EntrySyncData("fireworkWindMovement")
	public int fireworkWindMovement = 100;

	@CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	public static AmbienceAndMiscConfig get() {
		return get(false);
	}

	public static AmbienceAndMiscConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static AmbienceAndMiscConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public double getParticleWindIntensity() {
		return ((double) this.particleWindMovement) * 0.01;
	}

	public double getFireworkWindIntensity() {
		return ((double) this.fireworkWindMovement) * 0.01;
	}

	public static class BiomeAmbienceConfig {
		@EntrySyncData(value = "deepDarkAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean deepDarkAmbience = true;

		@EntrySyncData(value = "dripstoneCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean dripstoneCavesAmbience = true;

		@EntrySyncData(value = "lushCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean lushCavesAmbience = true;
	}

	public static class BiomeMusicConfig {
		@EntrySyncData(value = "wilderForestMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderForestMusic = true;
	}
}