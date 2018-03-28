package org.lulu.csye6225.assignment1.resource;

import org.lulu.csye6225.assignment1.model.Course;
import org.lulu.csye6225.assignment1.model.Student;
import org.lulu.csye6225.assignment1.service.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/courses")
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
    public Course getCourse(@PathParam("courseId") long id){
        return courseService.getCourse(id);
    }

    @GET
    @Path("/{courseId}/students")
    public List<Student> getStudents(@PathParam("courseId") long id){
        return courseService.getStudents(id);
    }

    @POST
    @Path("/{courseId}/students/{studentId}")
    public Student addStudent(@PathParam("courseId") long id, @PathParam("studentId") long studentId){
        return courseService.addStudent(id, studentId);
    }

    @GET
    @Path("/{courseId}/students/{studentId}")
    public Student getStudent(@PathParam("courseId") long id, @PathParam("studentId") long studentId){
        return courseService.getStudent(id,studentId);
    }

    @DELETE
    @Path("/{courseId}/students/{studentId}")
    public void removeStudent(@PathParam("courseId") long id, @PathParam("studentId") long studentId){
        courseService.removeStudent(id,studentId);
    }

    @Path("/{courseId}/lectures")
    public LectureOfCourseResource getLectureResource(@PathParam("courseId") long id){
        return new LectureOfCourseResource();
    }
}
