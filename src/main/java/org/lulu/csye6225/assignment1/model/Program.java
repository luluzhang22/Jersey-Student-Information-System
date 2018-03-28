package org.lulu.csye6225.assignment1.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
    private long programId;
    private String name;
    @JsonIgnore
    private Map<Long, Course> courses = new HashMap<>();
    @JsonIgnore
    private Map<Long, Student> students = new HashMap<>();

    public Program(){

    }

    public Program(long programId, String name){
        this.programId = programId;
        this.name = name;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Course> getCourses() {
        return courses;
    }

    @JsonGetter("courses")
    public List<String> coursesToJson(){
        List<String> list = new ArrayList<>();
        for(Course c : courses.values()){
            list.add(c.getName());
        }
        return list;
    }

    @JsonGetter("students")
    public List<String> studentsToJson(){
        List<String> list = new ArrayList<>();
        for(Student s : students.values())
            list.add(s.getName());
        return list;
    }

    public void setCourses(Map<Long, Course> courses) {
        this.courses = courses;
    }

    public Map<Long, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<Long, Student> students) {
        this.students = students;
    }
}
