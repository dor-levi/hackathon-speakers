package com.hackathon.speakers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeakUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpeakUtil.class);
    private static final String ESPEAK = "espeak -ven+f3 -k5 -s150 \"%s\"";

    public static void speak(String text) {
        espeak(text);
    }

    private static void espeak(String text) {
        try {
            Runtime.getRuntime().exec(String.format(ESPEAK, text));
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }
}
