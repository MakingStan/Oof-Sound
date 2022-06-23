package com.code;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Oof Sound")
public interface OefConfig extends Config
{

	enum SoundToPlay {
		oof,
		Spongebob
	}
	@ConfigItem(
		keyName = "Death",
		name = "Death",
		description = "Make an oof sound when you die!",
		position = 4
	)
	default boolean death()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Damage",
			name = "Damage",
			description = "Make an oof sound when you take damage!",
			position = 3
	)
	default boolean damage()
	{
		return true;
	}

	@ConfigItem(
			keyName= "whichSoundToPlay",
			name = "Which Sound",
			description = "Choose which sound to play.",
			position = 1
	)
	default SoundToPlay whichSoundToPlay()
	{
		return SoundToPlay.oof;
	}
	@ConfigItem(
			keyName = "Overlay",
			name = "Overlay",
			description = "Toggle the overlay.",
			position = 5
	)
	default boolean overlay()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Volume",
			name = "Volume",
			description = "At what volume you want to play the oof sounds.",
			position = 2
	)
	default int volume()
	{
		return 80;
	}
}
