package org.lulu.csye6225.assignment2.resource;

import org.lulu.csye6225.assignment2.model.Program;
import org.lulu.csye6225.assignment2.service.ProgramService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ddb/programs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {
    ProgramService programService = new ProgramService();

    @GET
    public List<Program> getPrograms(){
        return programService.getAllProgram();
    }

    @POST
    public Program addProgram(Program program){
        return programService.addProgram(program);
    }

    @PUT
    @Path("/{programId}")
    public Program updateProgram(@PathParam("programId") String id, Program program){
        return programService.updateProgram(id,program);
    }

    @DELETE
    @Path("/{programId}")
    public void deleteProgram(@PathParam("programId") String id){
        programService.removeProgram(id);
    }

    @GET
    @Path("/{programId}")
    public Program getProgram(@PathParam("programId") String id){
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
