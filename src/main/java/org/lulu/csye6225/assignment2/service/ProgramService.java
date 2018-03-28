package org.lulu.csye6225.assignment2.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.lulu.csye6225.assignment2.database.DynamoDBInit;
import org.lulu.csye6225.assignment2.model.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramService {
    private static DynamoDBMapper mapper = DynamoDBInit.getMapper();

    public List<Program> getAllProgram(){
        return new ArrayList<>(mapper.scan(Program.class, new DynamoDBScanExpression()));
    }

    public Program getProgram(String id){
        return mapper.load(Program.class, id);
    }

    public Program addProgram(Program program){
        Program p = new Program(program.getName());
        mapper.save(p);
        return mapper.load(Program.class, p.getProgramId());
    }

    public Program updateProgram(String id, Program program){
        Program oldProgram = mapper.load(Program.class, id);
        if(oldProgram != null){
            oldProgram.setName(program.getName());
            mapper.save(oldProgram);
            return mapper.load(Program.class, id);
        }
        return null;
    }

    public void removeProgram(String id){
        Program p = mapper.load(Program.class, id);
        if(p == null)
            return;
        mapper.delete(p);
        //?????delete courses & students
    }
}
