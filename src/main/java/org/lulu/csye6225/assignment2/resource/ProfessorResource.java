package org.lulu.csye6225.assignment2.resource;

import org.lulu.csye6225.assignment2.model.Announcement;
import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.model.Professor;
import org.lulu.csye6225.assignment2.service.ProfessorService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/ddb/professors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessorResource {
    ProfessorService professorService = new ProfessorService();
    @GET
    public List<Professor> getStudents(){
        return professorService.getAllProfessor();
    }

    @POST
    public Professor addProfessor(Professor professor){
        return professorService.addProfessor(professor);
    }

    @PUT
    @Path("/{professorId}")
    public Professor updateProfessor(@PathParam("professorId") String id, Professor professor){
        professor.setProfessorId(id);
        return professorService.updateProfess(professor);
    }

    @GET
    @Path("{professorId}")
    public Professor getProfessor(@PathParam("professorId") String id){
        return professorService.getProfessor(id);
    }

    @DELETE
    @Path("/{professorId}")
    public void deleteProfessor(@PathParam("professorId") String id){
        professorService.removeProfessor(id);
    }

    @GET
    @Path("{professorId}/courses")
    public Set<String> getCourses(@PathParam("professorId") String id){

        return professorService.getCourses(id);
    }

    @POST
    @Path("{professorId}/courses/{courseId}")
    public Set<String> addCourse(@PathParam("professorId") String id, @PathParam("courseId") String courseId){
        return professorService.addCourse(id,courseId);
    }

    @GET
    @Path("{professorId}/courses/{courseId}")
    public Course getCourse(@PathParam("professorId") String id, @PathParam("courseId") String courseId){
        return professorService.getCourse(id, courseId);
    }

    @DELETE
    @Path("{professorId}/courses/{courseId}")
    public void removeCourse(@PathParam("professorId") String id, @PathParam("courseId") String courseId){
        professorService.removeCourse(id, courseId);
    }

    @GET
    @Path("{professorId}/courses/{courseId}/announcements")
    public List<Announcement> getAnnouncements(@PathParam("professorId") String id, @PathParam("courseId") String courseId){
        return professorService.getAnnouncements(id, courseId);
    }

    @POST
    @Path("{professorId}/courses/{courseId}/announcements/")
    public Set<String> addAnnouncement(@PathParam("professorId") String id, @PathParam("courseId") String courseId,Announcement announcement){
        return professorService.addAnnouncement(id,courseId, announcement);
    }

    @GET
    @Path("{professorId}/courses/{courseId}/announcements/{announcementId}")
    public Announcement getAnnouncement(@PathParam("professorId") String id, @PathParam("courseId") String courseId, @PathParam("announcementId") String announcementId){
        return professorService.getAnnouncement(id, courseId, announcementId);
    }
}
