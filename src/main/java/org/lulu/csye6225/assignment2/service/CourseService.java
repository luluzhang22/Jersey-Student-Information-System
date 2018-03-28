package org.lulu.csye6225.assignment2.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.lulu.csye6225.assignment2.database.DynamoDBInit;
import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.model.Professor;
import org.lulu.csye6225.assignment2.model.Program;
import org.lulu.csye6225.assignment2.model.Student;
import org.lulu.csye6225.assignment2.lambda.LambdaFunctionHandler;

import java.util.*;

public class CourseService {
    private static DynamoDBMapper mapper = DynamoDBInit.getMapper();

    public List<Course> getAllCourse(){
        return new ArrayList<>(mapper.scan(Course.class, new DynamoDBScanExpression()));
    }

    public List<Course> getAllCourse(String programId){
        Program p = mapper.load(Program.class, programId);
        if(p != null && p.getCourses() != null){
            List<Course> courses = new ArrayList<>();
            for(String courseId : p.getCourses()){
                courses.add(mapper.load(Course.class, courseId));
            }
            return courses;
        }
        return null;
    }

    public Course getCourse(String id){
        return mapper.load(Course.class, id);
    }

    public Course getCourse(String programId, String id){
        Program p = mapper.load(Program.class, programId);
        if(p != null && p.getCourses() != null && p.getCourses().contains(id)){
            return mapper.load(Course.class, id);
        }
        return null;
    }

    public void addCourse(Course course){
        mapper.save(course);
    }

    public Course addCourse(String programId, Course course){
        if(mapper.load(Course.class, course.getCourseId()) != null){
            System.out.println("courseId - " + course.getCourseId() + " is already exist!");
            return null;
        }
        //add course
        course.setProgramId(programId);
        addCourse(course);

        //update program
        Program p = mapper.load(Program.class, programId);
        if(p.getCourses() == null)
            p.setCourses(new HashSet<String>());
        p.getCourses().add(course.getCourseId());
        mapper.save(p);

        //create a new topic for this course
        new LambdaFunctionHandler().addTopic(course.getCourseId());
        return course;
    }

    public Course updateCourse(Course course){
        Course c = mapper.load(Course.class, course.getCourseId());
        c.setName(course.getName());
        c.setRoster(course.getRoster());
        c.setBoard(course.getBoard());
        mapper.save(c);
        return c;
    }

    public Course updateCourse(String programId, Course course){
        Program p = mapper.load(Program.class, programId);
        if(p != null || p.getCourses() != null && p.getCourses().contains(course.getCourseId())){
            return updateCourse(course);
        }
        return null;
    }

    private void removeCourseFromStudents(Course course){
        if(course.getStudents() != null){
            for(String studentId : course.getStudents()){
                Student s = mapper.load(Student.class, studentId);
                if(s != null && s.getCourses() != null) {
                    s.getCourses().remove(course.getCourseId());
                    if (s.getCourses().size() == 0)
                        s.setCourses(null);
                    mapper.save(s);
                }
            }
        }
    }

    private void removeCourseFromProfessor(Course course){
        Professor p = mapper.load(Professor.class, course.getProfessorId());
        if(p != null && p.getCourses() != null){
            p.getCourses().remove(course.getCourseId());
            if(p.getCourses().size() == 0){
                p.setCourses(null);
            }
            mapper.save(p);
        }
    }

    public void removeCourse(Course course){
        removeCourseFromStudents(course);
        removeCourseFromProfessor(course);

        mapper.delete(course);
    }

    public void removeCourse(String programId, String id){
        Program p = mapper.load(Program.class, programId);
        if(p != null && p.getCourses() != null && p.getCourses().contains(id)){
            removeCourse(mapper.load(Course.class, id));
            p.getCourses().remove(id);
            if(p.getCourses().size() == 0)
                p.setCourses(null);
            mapper.save(p);
        }
    }

    public List<Student> getStudents(String courseId){
        Course course = mapper.load(Course.class, courseId);
        List<Student> list = new ArrayList<>();
        if(course != null && course.getStudents() != null){
            for(String studentId : course.getStudents()){
                list.add(mapper.load(Student.class, studentId));
            }
        }
        return list;
    }

    public Student getStudent(String courseId, String studentId){
        Course c = mapper.load(Course.class, courseId);
        if(c != null && c.getStudents() != null && c.getStudents().contains(studentId)){
            return mapper.load(Student.class, studentId);
        }
        return null;
    }

    //could course add students??
    public Set<String> addStudent(String courseId, String studentId){
        Course c = mapper.load(Course.class, courseId);
        Student s = mapper.load(Student.class, studentId);
        if(c != null && s != null && (c.getStudents() == null || !c.getStudents().contains(studentId))) {
            if (c.getStudents() == null) {
                c.setStudents(new HashSet<String>());
            }
            if (s.getCourses() == null) {
                s.setCourses(new HashSet<String>());
            }
            c.getStudents().add(studentId);
            s.getCourses().add(courseId);
            mapper.save(s);
            mapper.save(c);
            new LambdaFunctionHandler().subscribe(courseId, s.getEmail());
        }
        return c.getStudents();
    }

    public void removeStudent(String courseId, String studentId){
        Course c = mapper.load(Course.class, courseId);
        if(c != null && c.getStudents() != null && c.getStudents().contains(studentId)){
            c.getStudents().remove(studentId);
            if(c.getStudents().size() == 0){
                c.setStudents(null);
            }
            mapper.save(c);

            Student s = mapper.load(Student.class, studentId);
            s.getCourses().remove(courseId);
            if(s.getCourses().size() == 0){
                s.setCourses(null);
            }
            mapper.save(s);
        }
    }
}
