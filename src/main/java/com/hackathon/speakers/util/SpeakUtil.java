package com.hackathon.speakers.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class SpeakUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpeakUtil.class);
    private static final String FESTIVAL = "echo \"%s\" | festival --tts";
    private static final String ESPEAK = "espeak -ven+f3 -k5 -s150 \"%s\"";
    private static final String ESPEAK_FILE = "espeak -ven+f3 -k5 -s150 -f %s";
    private static final int MAX_LENGTH = 150;

    public static void speak(String text, String userName) {
        espeakFile(text, userName);
    }

    private static void festival(String text, String userName) {
        try {
            String actualText = safeText(text);
            doSpeak(String.format(FESTIVAL, actualText), actualText, userName);
        } catch (Exception e) {
            logger.error("Failed to festival.", e);
        }
    }

    private static void espeak(String text, String userName) {
        try {
            String actualText = safeText(text);
            doSpeak(String.format(ESPEAK, actualText), actualText, userName);
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }

    private static void espeakFile(String text, String userName) {
        try {
            String actualText = safeText(text);
            String filePath = FileUtil.writeToFile(actualText);

            if (filePath == null) {
                logger.warn("Unable to create temp file");
            }

            doSpeak(String.format(ESPEAK_FILE, filePath), actualText, userName);
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

    private static void doSpeak(String command, String text, String userName) throws InterruptedException, IOException {
        logger.info("{} going to say {}.", userName, text);

        OSUtil.runCommand(command);
    }
}
