package org.lulu.csye6225.assignment2.model;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.validation.constraints.NotNull;

@DynamoDBTable(tableName = "Student")
public class Student {
    @NotNull private String studentId;
    private String name;
    @NotNull private String email;
    private Set<String> courses;
    @NotNull private String programId;

    public Student() {

    }

    public Student(String id, String name, String email, String programId){
        this.studentId = id;
        this.name = name;
        this.email = email;
        this.programId = programId;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "Courses")
    public Set<String> getCourses() {
        return courses;
    }

    public void setCourses(Set<String> courses) {
        this.courses = courses;
    }

    @DynamoDBAttribute(attributeName = "ProgramId")
    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }
}
