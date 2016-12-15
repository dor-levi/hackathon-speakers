package com.hackathon.speakers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioUtil {
    private static final Logger logger = LoggerFactory.getLogger(AudioUtil.class);
    
    private static final String PLAYER_CMD = "omxplayer -o local %s";
    private static final String MUSIC_PATH = "/home/pi/Music/";

    public static void playFile(String filename) {
        try {
            OSUtil.runCommand(String.format(PLAYER_CMD, MUSIC_PATH + filename));
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }
}
