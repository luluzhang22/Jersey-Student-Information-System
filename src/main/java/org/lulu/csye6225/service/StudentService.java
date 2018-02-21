package org.lulu.csye6225.service;

import org.lulu.csye6225.database.DatabaseClass;
import org.lulu.csye6225.model.Course;
import org.lulu.csye6225.model.Program;
import org.lulu.csye6225.model.Student;

import java.util.*;

public class StudentService {
    private Map<Long, Student> students = DatabaseClass.getStudents();
    private Map<Long, Course> courses = DatabaseClass.getCourses();
    private Map<Long, Program> programs = DatabaseClass.getPrograms();
    private static long generateId = DatabaseClass.getStudents().size();

    public List<Student> getAllStudent(){
        return new ArrayList<>(students.values());
    }

    public List<Student> getAllStudentByProgram(long programId){
        if(programs.get(programId) == null)
            return null;
        return new ArrayList<>(programs.get(programId).getStudents().values());
    }

    public Student getStudentByProgram(long programId, long id){
        if(programs.get(programId) == null)
            return null;
        return programs.get(programId).getStudents().get(id);
    }

    public Student getStudent(long id){
        return students.get(id);
    }

    public Student addStudent(Student student){
        student.setStudentId(++generateId);
        students.put(student.getStudentId(), student);
        return student;
    }

    public Student addStudent(long programId, Student student){
        student.setProgramName(programs.get(programId).getName());
        addStudent(student);
        programs.get(programId).getStudents().put(student.getStudentId(), student);
        return student;
    }

    public Student updateStudent(Student student){
        if(student.getStudentId() <= 0){
            return null;
        }
        //should not change programName
        students.put(student.getStudentId(), student);
        return student;
    }

    public Student updateStudent(long programId, Student student){
        student.setProgramName(programs.get(programId).getName());
//        student.setCourses(new ArrayList<String>());
        if(updateStudent(student) == null)
            return null;
        programs.get(programId).getStudents().put(student.getStudentId(), student);
        return student;
    }

    public Student removeStudent(long id){
        if(!students.containsKey(id))
            return null;
        for(long courseId : students.get(id).getCourses().keySet()){
            courses.get(courseId).getStudents().remove(id);
        }
        return students.remove(id);
    }

    public Student removeStudent(long programId, long id){
        if(removeStudent(id) == null)
            return null;
        return programs.get(programId).getStudents().remove(id);
    }

    public List<Course> getCourses(long id){
        if(students.get(id) == null)
            return null;
        return new ArrayList<>(students.get(id).getCourses().values());
    }

    public Course getCourse(long id, long courseId){
        if(students.get(id) == null)
            return null;
        return students.get(id).getCourses().get(courseId);
    }

    public Student removeCourse(long id, long courseId){
        if(students.get(id) == null)
            return null;
        Course c = students.get(id).getCourses().get(courseId);
        if(c != null) {
            c.getStudents().remove(id);
            students.get(id).getCourses().remove(courseId);
        }
        return students.get(id);
    }

    public List<Course> addCourse(long studentId, long courseId){
        Student s = students.get(studentId);
        Course c = courses.get(courseId);
        if(!s.getCourses().containsKey(courseId)){
            s.getCourses().put(courseId,c);
            c.getStudents().put(s.getStudentId(),s);
        }
        return getCourses(studentId);
    }
}
