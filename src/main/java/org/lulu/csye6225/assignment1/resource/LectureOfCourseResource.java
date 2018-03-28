package org.lulu.csye6225.assignment1.resource;

import org.lulu.csye6225.assignment1.model.Lecture;
import org.lulu.csye6225.assignment1.service.LectureService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LectureOfCourseResource {
    LectureService lectureService = new LectureService();

    @GET
    public List<Lecture> getLectures(@PathParam("courseId") long courseId){
        return lectureService.getLectures(courseId);
    }

    @POST
    public Lecture addLectures(@PathParam("courseId") long courseId, Lecture lecture){
        return lectureService.addLecture(courseId, lecture);
    }

    @GET
    @Path("/{lectureId}")
    public Lecture getLecture(@PathParam("courseId") long courseId, @PathParam("lectureId") long lectureId){
        return lectureService.getLecture(courseId, lectureId);
    }

    @PUT
    @Path("/{lectureId}")
    public Lecture updateLecture(@PathParam("courseId") long courseId, @PathParam("lectureId") long lectureId, Lecture lecture){
        lecture.setLectureId(lectureId);
        return lectureService.updateLecture(courseId, lecture);
    }

    @DELETE
    @Path("/{lectureId}")
    public void deleteLecture(@PathParam("courseId") long courseId, @PathParam("lectureId") long lectureId){
        lectureService.deleteLecture(courseId, lectureId);
    }
}
