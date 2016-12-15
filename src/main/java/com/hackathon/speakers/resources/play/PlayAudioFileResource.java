package com.hackathon.speakers.resources.play;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.hackathon.speakers.data.play.PlayFileRequest;

@Path("/hackathon/v1/play/audio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class PlayAudioFileResource {
    private static final Logger logger = LoggerFactory.getLogger(PlayAudioFileResource.class);

    private static final String MUSIC_PATH = "/home/pi/Music/";

    @GET
    @Timed
    public Response get(@QueryParam("text") String text) {
        try {
            playFile(text);

            return Response.ok("ok").build();
        } catch (Exception e) {
            logger.error("Could not reply to ping request", e);
            return Response.serverError().entity("Could not reply to ping request").build();
        }
    }

    @POST
    @Timed
    public Response post(PlayFileRequest request) {
        try {
            playFile(request.text);

            return Response.ok("ok").build();
        } catch (Exception e) {
            logger.error("Could not reply to ping request", e);
            return Response.serverError().entity("Could not reply to ping request").build();
        }
    }

    private void playFile(String filename) {
        try {
            Runtime.getRuntime().exec("omxplayer -o local " + MUSIC_PATH + filename);
        } catch (IOException e) {
            logger.error("Failed playing file ({})", filename);
        }
    }
}
