package org.lulu.csye6225.database;

import org.lulu.csye6225.model.Course;
import org.lulu.csye6225.model.Lecture;
import org.lulu.csye6225.model.Program;
import org.lulu.csye6225.model.Student;

import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {

    private static Map<Long, Student> students = new HashMap<>();
    private static Map<Long, Course> courses = new HashMap<>();
    private static Map<Long, Lecture> lectures = new HashMap<>();
    private static Map<Long, Program> programs = new HashMap<>();

    static {

        Program p1 = new Program(1L, "Computer Science");
        Program p2 = new Program(2L, "Information System");

        Course c1 = new Course(1L, "CSYE6225-Cloud Computing", "Lulu Zhang", "board", p1.getName());
        Course c2 = new Course(2L, "INFO6250-Web Tools", "Cass", "board balabala", p2.getName());

        Student s1 = new Student(1L, "Lulu Zhang", "image1", p1.getName());
        Student s2 = new Student(2L, "Cass", "image2", p2.getName());

        Lecture l1 = new Lecture(1L, "lecture 1 for course 1", "note for lecture 1", "material for lecture 1", c1.getName());
        Lecture l2 = new Lecture(2L, "lecture 1 for course 2", "note for lecture 1", "material for lecture 1", c2.getName());

        lectures.put(1L, l1);
        lectures.put(2L, l2);

        c1.getLectures().put(l1.getLectureId(), l1);
        c2.getLectures().put(l2.getLectureId(), l2);

        s1.getCourses().put(c1.getCourseId(), c1);
        s1.getCourses().put(c2.getCourseId(), c2);
        s2.getCourses().put(c1.getCourseId(), c1);

        students.put(1L, s1);
        students.put(2L, s2);

        c1.getStudents().put(s1.getStudentId(), s1);
        c1.getStudents().put(s2.getStudentId(), s2);
        c2.getStudents().put(s1.getStudentId(), s1);
        c2.getStudents().put(s2.getStudentId(), s2);

        courses.put(1L, c1);
        courses.put(2L, c2);
        p1.getCourses().put(c1.getCourseId(), c1);
        p2.getCourses().put(c2.getCourseId(), c2);
        p1.getStudents().put(s1.getStudentId(), s1);
        p2.getStudents().put(s2.getStudentId(), s2);
        programs.put(1L, p1);
        programs.put(2L, p2);
    }

    public static Map<Long, Student> getStudents(){
        return students;
    }

    public static Map<Long, Course> getCourses(){
        return courses;
    }

    public static Map<Long, Lecture> getLectures() {
        return lectures;
    }

    public static Map<Long, Program> getPrograms() {
        return programs;
    }
}
