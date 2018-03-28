package org.lulu.csye6225.assignment1.resource;

import org.lulu.csye6225.assignment1.model.Course;
import org.lulu.csye6225.assignment1.service.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseOfProgramResource {
    CourseService courseService = new CourseService();

    @GET
    public List<Course> getCourses(@PathParam("programId") long programId){
        return courseService.getAllCourse(programId);
    }

    @POST
    public Course addCourse(@PathParam("programId") long programId, Course course){
        return courseService.addCourse(programId, course);
    }

    @PUT
    @Path("/{courseId}")
    public Course updateCourse(@PathParam("programId") long programId, @PathParam("courseId") long courseId, Course course){
        course.setCourseId(courseId);
        return courseService.updateCourse(programId, course);
    }

    @DELETE
    @Path("/{courseId}")
    public void deleteCourse(@PathParam("programId") long programId, @PathParam("courseId") long courseId){
        courseService.removeCourse(programId, courseId);
    }

    @GET
    @Path("/{courseId}")
    public Course getCourse(@PathParam("programId") long programId, @PathParam("courseId") long courseId){
        return courseService.getCourse(programId, courseId);
    }
}
