package org.lulu.csye6225.model;


import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"courseId", "name", "programName", "lectures", "roster", "board", "students"})
public class Course {
    private long courseId;
    private String name;

    @JsonIgnore
    private Map<Long, Lecture> lectures = new HashMap<>();
    private String roster;
    private String board;

    @JsonIgnore
    private Map<Long, Student> students = new HashMap<>();

//    private String students

    private String programName;

    public Course(){

    }

    public Course(long courseId, String name, String roster, String board, String programName){
        this.courseId = courseId;
        this.name = name;
        this.roster = roster;
        this.board = board;
        this.programName = programName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Map<Long, Lecture> lectures) {
        this.lectures = lectures;
    }

    @JsonGetter("lectures")
    public List<String> lectureToJson(){
        List<String> list = new ArrayList<>();
        for(Lecture l : lectures.values()){
            list.add(l.getName());
        }
        return list;
    }

    public String getRoster() {
        return roster;
    }

    public void setRoster(String roster) {
        this.roster = roster;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }


    public Map<Long, Student> getStudents() {
        return students;
    }

    @JsonGetter("students")
    public List<String> studentsToJson(){
        List<String> list = new ArrayList<>();
        for(Student s : students.values()){
            list.add(s.getName());
        }
        return list;
    }

    public void setStudents(Map<Long, Student> students) {
        this.students = students;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
}
