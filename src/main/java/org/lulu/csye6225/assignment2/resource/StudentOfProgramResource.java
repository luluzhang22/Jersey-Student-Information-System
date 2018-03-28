package org.lulu.csye6225.assignment2.resource;

import org.lulu.csye6225.assignment2.model.Student;
import org.lulu.csye6225.assignment2.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentOfProgramResource {
    StudentService studentService = new StudentService();

    @GET
    public List<Student> getStudents(@PathParam("programId") String programId){
        return studentService.getAllStudentByProgram(programId);
    }

    @POST
    public Student addStudent(@PathParam("programId") String programId, Student student){
        return studentService.addStudent(programId,student);
    }

    @PUT
    @Path("/{studentId}")
    public Student updateStudent(@PathParam("programId") String programId, @PathParam("studentId") String studentId, Student student){
        student.setStudentId(studentId);
        return studentService.updateStudent(programId, student);
    }

    @DELETE
    @Path("/{studentId}")
    public void deleteStudent(@PathParam("programId") String programId, @PathParam("studentId") String studentId){
        studentService.removeStudent(programId, studentId);
    }

    @GET
    @Path("/{studentId}")
    public Student getStudent(@PathParam("programId") String programId, @PathParam("studentId") String studentId){
        return studentService.getStudentByProgram(programId, studentId);
    }
}
