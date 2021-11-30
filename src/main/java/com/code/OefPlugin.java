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

import java.io.*;

@Slf4j
@PluginDescriptor(
	name = "Oof Sound"
)
public class OefPlugin extends Plugin {

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

	public OefPlugin() {
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		clip.close();
	}



	@Subscribe
	public void onAnimationChanged(AnimationChanged animationChanged)
	{
		if (client.getLocalPlayer().getHealthRatio() != 0) return;
		if (client.getLocalPlayer().getAnimation() != AnimationID.DEATH) return;

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			if (config.death())
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
		if (actor == client.getLocalPlayer())
		{
			if (client.getGameState() == GameState.LOGGED_IN)
			{
				if (config.damage())
				{
					playSound();
				}
			}
		}
	}

	private void playSound()
	{
		//if the clip is already open and we want a new one
		if(clip != null)
		{
			if(clip.getMicrosecondPosition() == clip.getMicrosecondLength())
			{
				clip.close();
			}
		}

		File soundFile = new File("src/main/resources/MinecraftOefSound.wav");

		if(!tryToLoadFile(soundFile)) return;

		oefCount++;

		//volume
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float volumeValue = config.volume() - 100;

		volume.setValue(volumeValue);
		clip.loop(0);
	}

	private boolean tryToLoadFile(File soundFile)
	{
		if (soundFile.exists())
		{
			try (InputStream fileStream = new BufferedInputStream(new FileInputStream(soundFile));
				 AudioInputStream sound = AudioSystem.getAudioInputStream(fileStream))
			{
				clip = AudioSystem.getClip();
				clip.open(sound);
				return true;
			}
			catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
			{
				log.warn("Could not load the file: ", e);
			}
		}
		return false;
	}


	@Provides
	OefConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OefConfig.class);
	}

}