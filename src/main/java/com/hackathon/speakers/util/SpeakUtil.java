package com.hackathon.speakers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeakUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpeakUtil.class);
    private static final String FESTIVAL = "echo \"%s\" | festival --tts";
    private static final String ESPEAK = "espeak -ven+f3 -k5 -s150 \"%s\"";

    public static void speak(String text) {
        festival(text);
    }

    private static void festival(String text) {
        try {
            String command = String.format(FESTIVAL, text);
            logger.info(command);
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }
    
    private static void espeak(String text) {
        try {
            String command = String.format(ESPEAK, text);
            logger.info(command);
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }
}
