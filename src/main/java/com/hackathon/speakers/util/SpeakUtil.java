package com.hackathon.speakers.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeakUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpeakUtil.class);
    private static final String FESTIVAL = "echo \"%s\" | festival --tts";
    private static final String ESPEAK = "espeak -ven+f3 -k5 -s150 \"%s\"";

    public static void speak(String text) {
        espeak(text);
    }

    private static void festival(String text) {
        try {
            runCommand(String.format(ESPEAK, text));
        } catch (Exception e) {
            logger.error("Failed to festival.", e);
        }
    }

    private static void espeak(String text) {
        try {
            runCommand(String.format(ESPEAK, text));
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }

    private static void runCommand(String command) throws InterruptedException, IOException {
        logger.debug("About to run - " + command);
        
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
