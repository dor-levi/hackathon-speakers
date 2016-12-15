package com.hackathon.speakers;

import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HackathonSpeakersConfiguration extends Configuration {
    private boolean enableCors;

    @NotEmpty
    private String corsOrigins;

    @JsonProperty
    public boolean isEnableCors() {
        return enableCors;
    }

    @JsonProperty
    public void setEnableCors(boolean enableCors) {
        this.enableCors = enableCors;
    }

    @JsonProperty
    public String getCorsOrigins() {
        return corsOrigins;
    }

    @JsonProperty
    public void setCorsOrigins(String corsOrigins) {
        this.corsOrigins = corsOrigins;
    }
}
