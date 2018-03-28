package org.lulu.csye6225.assignment2.resource;

import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.model.Student;
import org.lulu.csye6225.assignment2.service.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("ddb/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
    CourseService courseService = new CourseService();

    @GET
    public List<Course> getCourses(){
        return courseService.getAllCourse();
    }

    @GET
    @Path("/{courseId}")
    public Course getCourse(@PathParam("courseId") String id){
        return courseService.getCourse(id);
    }

    @GET
    @Path("/{courseId}/students")
    public List<Student> getStudents(@PathParam("courseId") String id){
        return courseService.getStudents(id);
    }

    @POST
    @Path("/{courseId}/students/{studentId}")
    public Set<String> addStudent(@PathParam("courseId") String id, @PathParam("studentId") String studentId){
        return courseService.addStudent(id, studentId);
    }

    @GET
    @Path("/{courseId}/students/{studentId}")
    public Student getStudent(@PathParam("courseId") String id, @PathParam("studentId") String studentId){
        return courseService.getStudent(id,studentId);
    }

    @DELETE
    @Path("/{courseId}/students/{studentId}")
    public void removeStudent(@PathParam("courseId") String id, @PathParam("studentId") String studentId){
        courseService.removeStudent(id,studentId);
    }
}
