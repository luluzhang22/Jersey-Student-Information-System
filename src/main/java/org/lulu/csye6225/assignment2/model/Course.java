package org.lulu.csye6225.assignment2.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Set;

@DynamoDBTable(tableName = "Course")
public class Course {
    private String courseId;
    private String name;

    private Set<String> announcements ;
    private String roster;
    private String board;

    private Set<String> students;

    private String programId;
    private String professorId;
    
    private String topicArn;

    public Course(){

    }

    public Course(String courseId, String name, String roster, String board, String programId){
        this.courseId = courseId;
        this.name = name;
        this.roster = roster;
        this.board = board;
        this.programId = programId;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Annoucements")
    public Set<String> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<String> announcements) {
        this.announcements = announcements;
    }

    @DynamoDBAttribute(attributeName = "Roster")
    public String getRoster() {
        return roster;
    }

    public void setRoster(String roster) {
        this.roster = roster;
    }

    @DynamoDBAttribute(attributeName = "Board")
    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @DynamoDBAttribute(attributeName = "Students")
    public Set<String> getStudents() {
        return students;
    }

    public void setStudents(Set<String> students) {
        this.students = students;
    }

    @DynamoDBAttribute(attributeName = "ProgramId")
    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @DynamoDBAttribute(attributeName = "ProfessorId")
    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    @DynamoDBAttribute(attributeName = "TopicArn")
	public String getTopicArn() {
		return topicArn;
	}

	public void setTopicArn(String topicArn) {
		this.topicArn = topicArn;
	}
}
