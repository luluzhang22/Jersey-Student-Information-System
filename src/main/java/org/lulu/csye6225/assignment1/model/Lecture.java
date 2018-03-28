package org.lulu.csye6225.assignment1.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"lectureId", "name", "note", "material", "course"})
public class Lecture {
    private long lectureId;
    private String name;
    private String note;
    private String material;

    private String course;

    public Lecture(){

    }

    public Lecture(long lectureId, String name, String note, String material, String course){
        this.lectureId = lectureId;
        this.name = name;
        this.note = note;
        this.material = material;
        this.course = course;
    }

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
