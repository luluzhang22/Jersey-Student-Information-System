package org.lulu.csye6225.assignment1.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"studentId", "name", "imageUrl", "programName", "courses"})
public class Student {
    private long studentId;
    private String name;
    private String imageUrl;

    @JsonIgnore
    private Map<Long, Course> courses = new HashMap<>();
    private String programName;

    public Student(){

    }

    public Student(long studentId, String name, String imageUrl, String programName){
        this.studentId = studentId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.programName = programName;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<Long, Course> getCourses() {
        return courses;
    }

    public void setCourses(Map<Long, Course> courses) {
        this.courses = courses;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @JsonGetter("courses")
    public List<String> coursesToJson(){
        List<String> list = new ArrayList<>();
        for(Course c : courses.values()){
            list.add(c.getName());
        }
        return list;
    }
}
