package org.lulu.csye6225.assignment1.service;

import org.lulu.csye6225.assignment1.database.DatabaseClass;
import org.lulu.csye6225.assignment1.model.Course;
import org.lulu.csye6225.assignment1.model.Lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LectureService {
    Map<Long, Course> courses = DatabaseClass.getCourses();
    private static long generateId = DatabaseClass.getCourses().size();

    public List<Lecture> getLectures(long courseId){
        return new ArrayList<>(courses.get(courseId).getLectures().values());
    }

    public Lecture addLecture(long courseId, Lecture lecture){
        lecture.setLectureId(++generateId);
        lecture.setCourse(courses.get(courseId).getName());
        courses.get(courseId).getLectures().put(generateId, lecture);
        return lecture;
    }

    public Lecture getLecture(long courseId, long lectureId){
        if(courses.get(courseId) == null)
            return null;
        return courses.get(courseId).getLectures().get(lectureId);
    }

    public Lecture updateLecture(long courseId, Lecture lecture){
        Course course = courses.get(courseId);
        if(course != null && course.getLectures().containsKey(lecture.getLectureId())){
            lecture.setCourse(course.getName());
            course.getLectures().put(lecture.getLectureId(), lecture);
            return lecture;
        }
        return null;
    }

    public void deleteLecture(long courseId, long lectureId){
        if(courses.get(courseId) != null)
            courses.get(courseId).getLectures().remove(lectureId);
    }
}
