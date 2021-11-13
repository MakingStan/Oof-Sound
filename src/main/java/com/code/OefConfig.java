package com.code;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import javax.swing.*;

@ConfigGroup("Oef Sound")
public interface OefConfig extends Config
{
	@ConfigItem(
		keyName = "Death",
		name = "Death",
		description = "Make an oef sound when you die!"
	)
	default boolean death()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Damage",
			name = "Damage",
			description = "Make an oef sound when you take damage!"
	)
	default boolean damage()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Roblox/Minecraft oef!",
			name = "Roblox/Minecraft oef!",
			description = "Toggle the Roblox/Minecraft oef!"
	)
	default boolean getTypeOfSound()
	{
		return true; //roblox oef is false, minecraft is true
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
}
