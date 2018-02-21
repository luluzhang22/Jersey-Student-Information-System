package org.lulu.csye6225.service;

import org.lulu.csye6225.database.DatabaseClass;
import org.lulu.csye6225.model.Course;
import org.lulu.csye6225.model.Program;
import org.lulu.csye6225.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseService {
    private Map<Long, Program> programs = DatabaseClass.getPrograms();
    private Map<Long, Course> courses = DatabaseClass.getCourses();
    private Map<Long, Student> students = DatabaseClass.getStudents();
    private static long generateId = DatabaseClass.getCourses().size();

    public List<Course> getAllCourse(){
        return new ArrayList<>(courses.values());
    }

    public List<Course> getAllCourse(long programId){
        return new ArrayList<>(programs.get(programId).getCourses().values());
    }

    public Course getCourse(long id){
        return courses.get(id);
    }

    public Course getCourse(long programId, long id){
        Program program = programs.get(programId);
        if(program == null)
            return null;
        return program.getCourses().get(id);
    }

    public Course addCourse(Course course){
        course.setCourseId(++generateId);
        courses.put(course.getCourseId(), course);
        return course;
    }

    public Course addCourse(long programId, Course course){
        course.setProgramName(programs.get(programId).getName());
        addCourse(course);
        programs.get(programId).getCourses().put(course.getCourseId(), course);
        return course;
    }

    public Course updateCourse(Course course){
        if(course.getCourseId() <= 0){
            return null;
        }
        Course c = courses.get(course.getCourseId());
        c.setName(course.getName());
        c.setRoster(course.getRoster());
        c.setBoard(course.getBoard());
        return c;
    }

    public Course updateCourse(long programId, Course course){
        if(programs.get(programId) != null && programs.get(programId).getCourses().containsKey(course.getCourseId())){
            return updateCourse(course);
        }
        return null;
    }

    public Course removeCourse(long id){
        if(courses.containsKey(id)){
            for(long studentKey : courses.get(id).getStudents().keySet()){
                students.get(studentKey).getCourses().remove(id);
            }
        }
        return courses.remove(id);
    }

    public Course removeCourse(long programId, long id){
        if(removeCourse(id) == null)
            return null;
        programs.get(programId).getCourses().remove(id);
        return getCourse(programId,id);
    }

    public List<Student> getStudents(long courseId){
        return new ArrayList<>(courses.get(courseId).getStudents().values());
    }

    public Student getStudent(long courseId, long studentId){
        return courses.get(courseId).getStudents().get(studentId);
    }

    public Student addStudent(long courseId, long studentId){
        Student s = students.get(studentId);
        if(s != null && !courses.get(courseId).getStudents().containsKey(studentId)){
            courses.get(courseId).getStudents().put(studentId,s);
            s.getCourses().put(courseId, courses.get(courseId));
        }
        return s;
    }

    public void removeStudent(long courseId, long studentId){
        Course course = courses.get(courseId);
        if(course != null){
            if(course.getStudents().containsKey(studentId)){
                Student s = students.get(studentId);
                course.getStudents().remove(studentId);
                s.getCourses().remove(courseId);
            }
        }
    }


}
