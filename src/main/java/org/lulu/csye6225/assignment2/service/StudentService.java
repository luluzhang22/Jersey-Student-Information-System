package org.lulu.csye6225.assignment2.service;

import java.util.*;

import org.lulu.csye6225.assignment2.database.DynamoDBInit;
import org.lulu.csye6225.assignment2.lambda.AnnouncementLambdaFunctionHandler;
import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.model.Program;
import org.lulu.csye6225.assignment2.model.Student;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

public class StudentService {
    private static DynamoDBMapper mapper = DynamoDBInit.getMapper();

    public List<Student> getAllStudent(){
        return new ArrayList<>(mapper.scan(Student.class, new DynamoDBScanExpression()));
    }

    public List<Student> getAllStudentByProgram(String programId){
        Program p = mapper.load(Program.class, programId);
        if(p != null && p.getStudents() != null){
            List<Student> students = new ArrayList<>();
            for(String studentId : p.getStudents()){
                students.add(mapper.load(Student.class, studentId));
            }
            return students;
        }
        return null;
    }

    public Student getStudentByProgram(String programId, String id){
        Program p = mapper.load(Program.class, programId);
        if(p == null || p.getStudents() == null || !p.getStudents().contains(id))
            return null;
        return mapper.load(Student.class, id);
    }

    public Student getStudent(String id){
        return mapper.load(Student.class, id);
    }

    public Student addStudent(Student student){
        if(student.getStudentId() == null || student.getStudentId().isEmpty()){
            System.out.println("add a student must have a student Id!!");
            return null;
        }
        Student s = new Student(student.getStudentId(), student.getName(), student.getEmail(), student.getProgramId());
        mapper.save(s);
        return mapper.load(Student.class, s.getStudentId());
    }

    public Student addStudent(String programId, Student student){
        Program p = mapper.load(Program.class, programId);
        if(p != null){
            student.setProgramId(programId);
            if(addStudent(student) != null) {
                if(p.getStudents() == null){
                    p.setStudents(new HashSet<String>());
                }
                p.getStudents().add(student.getStudentId());
                mapper.save(p);
                return student;
            }
        }
        return null;
    }

    public Student updateStudent(Student student){
        Student s = mapper.load(Student.class, student.getStudentId());
        student.setCourses(s.getCourses());
        //if email is updated, do we need to re-subscribe the SNS topic??

        mapper.save(student);
        return student;
    }

    public Student updateStudent(String programId, Student student){
        Program p = mapper.load(Program.class, programId);
        if(p != null && p.getStudents() != null && p.getStudents().contains(student.getStudentId())){
            student.setProgramId(programId);
            return updateStudent(student);
        }
        return null;
    }

    public void removeStudent(Student student){
        for(String courseId : student.getCourses()){
            Course c = mapper.load(Course.class, courseId);
            if(c != null && c.getStudents() != null){
                c.getStudents().remove(student.getStudentId());
                if(c.getStudents().size() == 0)
                    c.setStudents(null);
                mapper.save(c);
            }
        }
        mapper.delete(student);
    }

    public void removeStudent(String programId, String id){
        Program p = mapper.load(Program.class, programId);
        if(p != null && p.getStudents() != null && p.getStudents().contains(id)){
            removeStudent(mapper.load(Student.class, id));
            p.getStudents().remove(id);
            if(p.getStudents().size() == 0){
                p.setStudents(null);
            }
            mapper.save(p);
        }
    }

    public List<Course> getCourses(String id){
        Student s = mapper.load(Student.class, id);
        if(s != null && s.getCourses() == null)
            return null;
        List<Course> list = new ArrayList<>();
        for(String courseId : s.getCourses()){
            list.add(mapper.load(Course.class, courseId));
        }
        return list;
    }

    public Course getCourse(String id, String courseId){
        Student s = mapper.load(Student.class, id);
        if(s != null && s.getCourses() != null && s.getCourses().contains(courseId)){
            return mapper.load(Course.class, courseId);
        }
        return null;
    }

    public Student removeCourse(String id, String courseId){
        Student s = mapper.load(Student.class, id);
        if(s != null && s.getCourses() != null && s.getCourses().contains(courseId)){
            Course c = mapper.load(Course.class, courseId);
            c.getStudents().remove(id);
            if(c.getStudents().size() == 0)
                c.setStudents(null);
            mapper.save(c);

            s.getCourses().remove(courseId);
            if(s.getCourses().size() == 0)
                s.setCourses(null);
            mapper.save(s);
        }
        return mapper.load(Student.class, id);
    }

    //could students choose courses that not in their program????
    public Set<String> addCourse(String studentId, String courseId){
        Student s = mapper.load(Student.class, studentId);
        Course c = mapper.load(Course.class, courseId);
        if(s != null && c != null ){
            if(s.getCourses() == null){
                s.setCourses(new HashSet<String>());
            }
            if(c.getStudents() == null){
                c.setStudents(new HashSet<String>());
            }
            s.getCourses().add(courseId);
            c.getStudents().add(studentId);
            mapper.save(s);
            mapper.save(c);
            new AnnouncementLambdaFunctionHandler().subscribe(courseId, s.getEmail());
        }
        return s.getCourses();
    }
}
