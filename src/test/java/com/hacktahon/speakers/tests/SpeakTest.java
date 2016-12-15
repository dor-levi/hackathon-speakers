package com.hacktahon.speakers.tests;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.hackathon.speakers.util.SpeakUtil;

public class SpeakTest {
    
    @Test
    public void testSimpleSpeak() {
        try {
            SpeakUtil.speak("Testing speaking capabilities");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
