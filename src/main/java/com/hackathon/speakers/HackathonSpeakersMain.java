package com.hackathon.speakers;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.hackathon.speakers.health.SpeakHealthCheck;
import com.hackathon.speakers.resources.diag.PingStorageResource;
import com.hackathon.speakers.resources.play.PlayAudioFileResource;
import com.hackathon.speakers.resources.speak.SimpleSpeakResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HackathonSpeakersMain extends Application<HackathonSpeakersConfiguration> {
    public static void main(String[] args) throws Exception {
        new HackathonSpeakersMain().run(args);
    }

    @Override
    public String getName() {
        return "hackathon-storage";
    }

    @Override
    public void initialize(Bootstrap<HackathonSpeakersConfiguration> bootstrap) {

    }

    @Override
    public void run(HackathonSpeakersConfiguration configuration, Environment environment) {
        if (configuration.isEnableCors()) {
            enableCors(configuration, environment);
        }

        environment.jersey().register(new PingStorageResource());
        environment.jersey().register(new SimpleSpeakResource());
        environment.jersey().register(new PlayAudioFileResource());
        
        environment.healthChecks().register("speak", new SpeakHealthCheck());
    }

    private void enableCors(HackathonSpeakersConfiguration configuration, Environment environment) {
        FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", configuration.getCorsOrigins());
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
