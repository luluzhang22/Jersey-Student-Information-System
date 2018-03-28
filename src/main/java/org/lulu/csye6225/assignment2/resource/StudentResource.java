package org.lulu.csye6225.assignment2.resource;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.model.Student;
import org.lulu.csye6225.assignment2.service.StudentService;

@Path("/ddb/students")
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
    public Student getStudent(@PathParam("studentId") String id){
        return studentService.getStudent(id);
    }

    @GET
    @Path("{studentId}/courses")
    public List<Course> getCourses(@PathParam("studentId") String id){
        return studentService.getCourses(id);
    }

    @POST
    @Path("{studentId}/courses/{courseId}")
    public Set<String> addCourse(@PathParam("studentId") String id, @PathParam("courseId") String courseId){
        return studentService.addCourse(id,courseId);
    }

    @GET
    @Path("{studentId}/courses/{courseId}")
    public Course getCourse(@PathParam("studentId") String id, @PathParam("courseId") String courseId){
        return studentService.getCourse(id, courseId);
    }

    @DELETE
    @Path("{studentId}/courses/{courseId}")
    public void removeCourse(@PathParam("studentId") String id, @PathParam("courseId") String courseId){
        studentService.removeCourse(id, courseId);
    }
}
