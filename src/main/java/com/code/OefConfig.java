package com.code;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Oof Sound")
public interface OefConfig extends Config
{

	enum SoundToPlay {
		OofSound,
		SpongebobSound
	}
	@ConfigItem(
		keyName = "Death",
		name = "Death",
		description = "Make an oof sound when you die!"
	)
	default boolean death()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Damage",
			name = "Damage",
			description = "Make an oof sound when you take damage!"
	)
	default boolean damage()
	{
		return true;
	}

	@ConfigItem(
			keyName= "whichSoundToPlay",
			name = "Which Sound",
			description = "Choose which sound to play."
	)
	default SoundToPlay whichSoundToPlay()
	{
		return SoundToPlay.OofSound;
	}
	@ConfigItem(
			keyName = "Overlay",
			name = "Overlay",
			description = "Toggle the overlay."
	)
	default boolean overlay()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Volume",
			name = "Volume",
			description = "At what volume you want to play the oof sounds."
	)
	default int volume()
	{
		return 80;
	}
}
