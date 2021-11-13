package com.code;

import com.google.inject.Provides;

import javax.inject.Inject;
import javax.sound.sampled.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.io.File;
import java.io.IOException;

@Slf4j
@PluginDescriptor(
	name = "Oef Sound"
)
public class OefPlugin extends Plugin
{

	public static int oefCount = 0;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Client client;

	@Inject
	private OefConfig config;

	@Inject
	private OefOverlay overlay;



	public Clip clip;
	private AudioInputStream input;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged animationChanged)
	{
		if(client.getLocalPlayer().getHealthRatio() != 0) return;
		if(client.getLocalPlayer().getAnimation() != AnimationID.DEATH) return;
		
		if(client.getGameState() == GameState.LOGGED_IN )
		{
			if(config.death())
			{
				System.out.println("Death");

				playSound();
			}
		}
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied hitsplatApplied)
	{
		Actor actor = hitsplatApplied.getActor();
		if(actor == client.getLocalPlayer())
		{
			if(client.getGameState() == GameState.LOGGED_IN)
			{
				if(config.damage())
				{
					playSound();
				}
			}
		}
	}


	private void playSound() {
		try
		{
			oefCount++;
			AudioInputStream soundFile;
			Clip clip = AudioSystem.getClip();

			if(config.getTypeOfSound())
			{
				soundFile = AudioSystem.getAudioInputStream(new File( "src/main/resources/MinecraftOefSound.wav"));
			}
			else
			{
				soundFile = AudioSystem.getAudioInputStream(new File( "src/main/resources/RobloxOefSound.wav"));
			}


			clip.open(soundFile);
			clip.loop(0);
		}
		catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex)
		{
			ex.printStackTrace();
		}

	}


	@Provides
	OefConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OefConfig.class);
	}
}
