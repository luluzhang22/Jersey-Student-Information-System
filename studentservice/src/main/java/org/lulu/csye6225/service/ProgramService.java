package org.lulu.csye6225.service;

import org.lulu.csye6225.database.DatabaseClass;
import org.lulu.csye6225.model.Course;
import org.lulu.csye6225.model.Program;
import org.lulu.csye6225.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramService {
    private Map<Long, Program> programs = DatabaseClass.getPrograms();
    private static long generateId = DatabaseClass.getPrograms().size();

    public List<Program> getAllProgram(){
        return new ArrayList<>(programs.values());
    }

    public Program getProgram(long id){
        return programs.get(id);
    }

    public Program addProgram(String programName){
        Program p = new Program(++generateId, programName);
        programs.put(p.getProgramId(), p);
        return p;
    }

    public Program updateProgram(long id, String programName){
        if(id <= 0){
            return null;
        }
        if(programs.containsKey(id)) {
            programs.get(id).setName(programName);
            for(Course c : programs.get(id).getCourses().values()){
                c.setProgramName(programName);
            }
            for (Student s : programs.get(id).getStudents().values()){
                s.setProgramName(programName);
            }
        }
        return programs.get(id);
    }

    public Program removeProgram(long id){
        if(!programs.containsKey(id))
            return null;
        CourseService courseService = new CourseService();
        for(long key : programs.get(id).getCourses().keySet()){
            courseService.removeCourse(key);
        }
        return programs.remove(id);
    }
}
