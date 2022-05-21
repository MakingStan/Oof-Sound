package com.code;

import com.google.inject.Provides;

import javax.inject.Inject;
import javax.sound.sampled.*;

import lombok.SneakyThrows;
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
import java.net.URL;

@Slf4j
@PluginDescriptor(
	name = "Oof Sound"
)
public class OefPlugin extends Plugin {

	String soundFilePath = "MinecraftOefSound.wav";
	String soundFilePath2 = "SpongebobDisaSound.wav";

	public static int oofCount = 0;


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

	private void playSound() {
		if (clip != null) {
			clip.close();
		}

		if (config.whichSoundToPlay() == OefConfig.SoundToPlay.OofSound) {
			/* fix for not working in a jar */
			Class c = null;
			AudioInputStream soundFileAudioInputStream = null;
			try {
				c = Class.forName("com.code.OefPlugin");
				URL url = c.getClassLoader().getResource(soundFilePath);
				soundFileAudioInputStream = AudioSystem.getAudioInputStream(url);
			} catch (ClassNotFoundException | UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}

			if (soundFileAudioInputStream == null) return;
			if (!tryToLoadFile(soundFileAudioInputStream)) return;

			oofCount++;

			//volume
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float volumeValue = config.volume() - 100;

			volume.setValue(volumeValue);
			clip.loop(0);
		} else if (config.whichSoundToPlay() == OefConfig.SoundToPlay.SpongebobSound) {
			Class c = null;
			AudioInputStream soundFileAudioInputStream = null;
			try {
				c = Class.forName("com.code.OefPlugin");
				URL url = c.getClassLoader().getResource(soundFilePath2);
				soundFileAudioInputStream = AudioSystem.getAudioInputStream(url);
			} catch (ClassNotFoundException | UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}

			if (soundFileAudioInputStream == null) return;
			if (!tryToLoadFile(soundFileAudioInputStream)) return;

			oofCount++;

			//volume
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float volumeValue = config.volume() - 100;

			volume.setValue(volumeValue);
			clip.loop(0);
		}
	}

	private boolean tryToLoadFile(AudioInputStream sound)
	{
		try
		{
			clip = AudioSystem.getClip();
			clip.open(sound);
			return true;
		} catch (LineUnavailableException | IOException e) {log.warn("Could not load the file: ", e);}
		return false;
	}


	@Provides
	OefConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OefConfig.class);
	}

}
