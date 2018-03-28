package org.lulu.csye6225.assignment2.resource;

import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.service.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseOfProgramResource {
    CourseService courseService = new CourseService();

    @GET
    public List<Course> getCourses(@PathParam("programId") String programId){
        return courseService.getAllCourse(programId);
    }

    @POST
    public Course addCourse(@PathParam("programId") String programId, Course course){
        return courseService.addCourse(programId, course);
    }

    @PUT
    @Path("/{courseId}")
    public Course updateCourse(@PathParam("programId") String programId, @PathParam("courseId") String courseId, Course course){
        course.setCourseId(courseId);
        return courseService.updateCourse(programId, course);
    }

    @DELETE
    @Path("/{courseId}")
    public void deleteCourse(@PathParam("programId") String programId, @PathParam("courseId") String courseId){
        courseService.removeCourse(programId, courseId);
    }

    @GET
    @Path("/{courseId}")
    public Course getCourse(@PathParam("programId") String programId, @PathParam("courseId") String courseId){
        return courseService.getCourse(programId, courseId);
    }
}
