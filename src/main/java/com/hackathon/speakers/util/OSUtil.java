package com.hackathon.speakers.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSUtil {
    private static final Logger logger = LoggerFactory.getLogger(OSUtil.class);
    
    public static void runCommand(String command) throws InterruptedException, IOException {
        logger.debug("About to run - " + command);

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
