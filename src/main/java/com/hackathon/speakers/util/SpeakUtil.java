package com.hackathon.speakers.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class SpeakUtil {
    public enum SpeakType {
        ST_FESTIVAL, ST_ESPEAK, ST_ESPEAK_FILE;
    }

    private static final Logger logger = LoggerFactory.getLogger(SpeakUtil.class);
    private static final String FESTIVAL = "echo \"%s\" | festival --tts";
    private static final String ESPEAK = "espeak -ven+f3 -k5 -s150 \"%s\"";
    private static final String ESPEAK_FILE = "espeak -ven+f3 -k5 -s150 -f %s";
    private static final int MAX_LENGTH = 150;

    public static void speak(String text, String userName) {
        speak(text, userName, SpeakType.ST_ESPEAK_FILE);
    }

    public static void speak(String text, String userName, SpeakType speakType) {
        switch (speakType) {
        case ST_FESTIVAL:
            festival(text, userName);
            break;
        case ST_ESPEAK:
            espeak(text, userName);
            break;
        case ST_ESPEAK_FILE:
            espeakFile(text, userName);
            break;
        }
    }

    private static void festival(String text, String userName) {
        try {
            String actualText = getTextToSay(text, userName);

            if (Strings.isNullOrEmpty(actualText)) {
                return;
            }

            doSpeak(String.format(FESTIVAL, actualText), actualText);
        } catch (Exception e) {
            logger.error("Failed to festival.", e);
        }
    }

    private static void espeak(String text, String userName) {
        try {
            String actualText = getTextToSay(text, userName);

            if (Strings.isNullOrEmpty(actualText)) {
                return;
            }

            doSpeak(String.format(ESPEAK, actualText), actualText);
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }

    private static void espeakFile(String text, String userName) {
        try {
            String actualText = getTextToSay(text, userName);

            if (Strings.isNullOrEmpty(actualText)) {
                return;
            }

            String filePath = FileUtil.writeToFile(actualText);

            if (filePath == null) {
                logger.warn("Unable to create temp file");
            }

            doSpeak(String.format(ESPEAK_FILE, filePath), actualText);
        } catch (Exception e) {
            logger.error("Failed to espeak.", e);
        }
    }

    private static String getTextToSay(String text, String userName) {
        String safeTextStr = safeText(text);

        if (Strings.isNullOrEmpty(safeTextStr)) {
            return null;
        }

        String intro = userName.replaceAll("\\.", " ") + " says ";
        
        return intro + safeTextStr;
    }

    private static String safeText(String text) {
        if ((Strings.isNullOrEmpty(text)) || (text.length() <= MAX_LENGTH)) {
            return text;
        } else {
            return text.substring(0, MAX_LENGTH);
        }
    }

    private static void doSpeak(String command, String text) throws InterruptedException, IOException {
        logger.info(text);

        OSUtil.runCommand(command);
    }
}
