package com.hackathon.speakers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hackathon.speakers.util.thread.TaskExecutors;

public class AudioUtil {
    protected static final Logger logger = LoggerFactory.getLogger(AudioUtil.class);

    private static final String PLAYER_CMD = "omxplayer -o local %s";
    private static final String MUSIC_PATH = "/home/pi/Music/";

    public static void playFile(final String filename) {
        TaskExecutors.speak.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    OSUtil.runCommand(String.format(PLAYER_CMD, MUSIC_PATH + filename));
                } catch (Exception e) {
                    logger.error("Failed playing {}.", filename, e);
                }
            }
        });
    }
}
