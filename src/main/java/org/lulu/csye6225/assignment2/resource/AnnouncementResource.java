package org.lulu.csye6225.assignment2.resource;

import org.lulu.csye6225.assignment2.model.Announcement;
import org.lulu.csye6225.assignment2.service.AnnouncementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ddb/announcements")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnnouncementResource {
    AnnouncementService announcementService = new AnnouncementService();

    @GET
    public List<Announcement> getAnnouncements(){
        return announcementService.getAllAnnouncement();
    }

    @GET
    @Path("{announcementId}")
    public Announcement getAnnouncement(@PathParam("announcementId") String id){
        return announcementService.getAnnouncement(id);
    }

}
