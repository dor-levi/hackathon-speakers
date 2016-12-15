package com.hackathon.speakers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class SpeakUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpeakUtil.class);
    private static final String FESTIVAL = "echo \"%s\" | festival --tts";
    private static final String ESPEAK = "espeak -ven+f3 -k5 -s150 \"%s\"";
    private static final String ESPEAK_FILE = "espeak -ven+f3 -k5 -s150 -f %s";
    private static final int MAX_LENGTH = 150;

    public static void speak(String text) {
        espeakFile(text);
    }

    private static void festival(String text) {
        try {
            OSUtil.runCommand(String.format(ESPEAK, safeText(text)));
        } catch (Exception e) {
            logger.error("Failed to festival.", e);
        }
    }

    private static void espeak(String text) {
        try {
            OSUtil.runCommand(String.format(ESPEAK, safeText(text)));
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }

    private static void espeakFile(String text) {
        try {
            String filePath = FileUtil.writeToFile(safeText(text));

            if (filePath == null) {
                logger.warn("Unable to create temp file");
            }

            OSUtil.runCommand(String.format(ESPEAK_FILE, filePath));
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }

    private static String safeText(String text) {
        if (Strings.isNullOrEmpty(text)) {
            return "";
        } else if (text.length() <= MAX_LENGTH) {
            return text;
        } else {
            return text.substring(0, MAX_LENGTH);
        }
    }
}
