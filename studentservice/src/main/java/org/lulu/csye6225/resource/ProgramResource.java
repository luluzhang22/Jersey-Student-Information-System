package org.lulu.csye6225.resource;

import org.lulu.csye6225.model.Program;
import org.lulu.csye6225.service.ProgramService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("programs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {
    ProgramService programService = new ProgramService();

    @GET
    public List<Program> getPrograms(){
        return programService.getAllProgram();
    }

    @POST
    public Program addProgram(String programName){
        return programService.addProgram(programName);
    }

    @PUT
    @Path("/{programId}")
    public Program updateProgram(@PathParam("programId") long id, String programName){
        return programService.updateProgram(id,programName);
    }

    @DELETE
    @Path("/{programId}")
    public void deleteProgram(@PathParam("programId") long id){
        programService.removeProgram(id);
    }

    @GET
    @Path("/{programId}")
    public Program getProgram(@PathParam("programId") long id){
        return programService.getProgram(id);
    }

    @Path("/{programId}/courses")
    public CourseOfProgramResource getCourseResource(){
        return new CourseOfProgramResource();
    }

    @Path("/{programId}/students")
    public StudentOfProgramResource getStudentResource(){
        return new StudentOfProgramResource();
    }
}
