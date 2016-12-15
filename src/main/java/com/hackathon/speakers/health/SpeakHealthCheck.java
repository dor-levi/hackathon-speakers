package com.hackathon.speakers.health;

import com.codahale.metrics.health.HealthCheck;
import com.hackathon.speakers.util.SpeakUtil;

public class SpeakHealthCheck extends HealthCheck {

    public SpeakHealthCheck() {
    }

    @Override
    protected Result check() {
        try {
            SpeakUtil.speak("Speak health check");
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("Problem with speak");
        }
    }
}
