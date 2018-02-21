package org.lulu.csye6225.resource;

import org.lulu.csye6225.model.Course;
import org.lulu.csye6225.model.Student;
import org.lulu.csye6225.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    StudentService studentService = new StudentService();

    @GET
    public List<Student> getStudents(){
        return studentService.getAllStudent();
    }

    @GET
    @Path("{studentId}")
    public Student getStudent(@PathParam("studentId") long id){
        return studentService.getStudent(id);
    }

    @GET
    @Path("{studentId}/courses")
    public List<Course> getCourses(@PathParam("studentId") long id){
        return studentService.getCourses(id);
    }

    @POST
    @Path("{studentId}/courses/{courseId}")
    public List<Course> addCourse(@PathParam("studentId") long id, @PathParam("courseId") long courseId){
        return studentService.addCourse(id,courseId);
    }

    @GET
    @Path("{studentId}/courses/{courseId}")
    public Course getCourse(@PathParam("studentId") long id, @PathParam("courseId") long courseId){
        return studentService.getCourse(id, courseId);
    }

    @DELETE
    @Path("{studentId}/courses/{courseId}")
    public void removeCourse(@PathParam("studentId") long id, @PathParam("courseId") long courseId){
        studentService.removeCourse(id, courseId);
    }
}
