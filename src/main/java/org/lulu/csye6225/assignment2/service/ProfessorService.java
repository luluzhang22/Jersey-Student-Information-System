package org.lulu.csye6225.assignment2.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.lulu.csye6225.assignment2.database.DynamoDBInit;
import org.lulu.csye6225.assignment2.model.Announcement;
import org.lulu.csye6225.assignment2.model.Course;
import org.lulu.csye6225.assignment2.model.Professor;

import java.util.*;

public class ProfessorService {
    private static DynamoDBMapper mapper = DynamoDBInit.getMapper();

    public static List<Professor> getAllProfessor(){
        return new ArrayList<>(mapper.scan(Professor.class, new DynamoDBScanExpression()));
    }

    public Professor getProfessor(String id){
        return mapper.load(Professor.class, id);
    }

    public Professor addProfessor(Professor professor){
        mapper.save(professor);
        return mapper.load(Professor.class, professor.getProfessorId());
    }

    public Professor updateProfess(Professor professor){
        Professor p = mapper.load(Professor.class, professor.getProfessorId());
        professor.setCourses(p.getCourses());
        mapper.save(professor);
        return professor;
    }

    public void removeProfessor(String id) {
        Professor p = mapper.load(Professor.class, id);
        if(p != null){
            if(p.getCourses() != null){
                for(String courseId : p.getCourses()){
                    Course c = mapper.load(Course.class, courseId);
                    c.setProfessorId("");
                    mapper.save(c);
                }
            }
            mapper.delete(p);
        }
    }

    public Set<String> getCourses(String id) {
        Professor p = mapper.load(Professor.class, id);
        if(p != null)
            return p.getCourses();
        return null;
    }

    public Set<String> addCourse(String id, String courseId) {
        Professor p = mapper.load(Professor.class, id);
        Course c = mapper.load(Course.class, courseId);
        if(p != null && c != null){
            if(p.getCourses() == null){
                p.setCourses(new HashSet<String>());
            }
            //if course already has a professor, cloud we just exchange the professor?????
            if(c.getProfessorId() != null){
                Professor professor = mapper.load(Professor.class, c.getProfessorId());
                professor.getCourses().remove(courseId);
                mapper.save(professor);
            }
            c.setProgramId(id);
            p.getCourses().add(courseId);
            mapper.save(c);
            mapper.save(p);
        }
        return p.getCourses();
    }

    public Course getCourse(String id, String courseId) {
        Professor p = mapper.load(Professor.class, id);
        if(p != null && p.getCourses() != null && p.getCourses().contains(courseId)){
            return mapper.load(Course.class, courseId);
        }
        return null;
    }

    public void removeCourse(String id, String courseId) {
        Professor p = mapper.load(Professor.class, id);
        if(p != null && p.getCourses() != null && p.getCourses().contains(courseId)){
            p.getCourses().remove(courseId);
            if(p.getCourses() == null){
                p.setCourses(new HashSet<String>());
            }

            //remove professor from the course
            Course c = mapper.load(Course.class, courseId);
            if(c != null && c.getProfessorId() != null && c.getProfessorId().equals(id)){
                c.setProfessorId("");
                mapper.save(c);
            }

            mapper.save(p);
        }
    }

    public List<Announcement> getAnnouncements(String id, String courseId) {
        Professor p = mapper.load(Professor.class, id);
        Course c = mapper.load(Course.class, courseId);
        if(p != null && c != null && p.getCourses() != null && p.getCourses().contains(courseId)){
            if(c.getAnnouncements() != null){
                List<Announcement> list = new ArrayList<>();
                for(String aId : c.getAnnouncements()){
                    list.add(mapper.load(Announcement.class, aId));
                }
                return list;
            }
        }
        return null;
    }

    public Set<String> addAnnouncement(String id, String courseId, Announcement announcement) {
        Professor p = mapper.load(Professor.class, id);
        Course c = mapper.load(Course.class, courseId);
        if(p != null && c != null && p.getCourses() != null && p.getCourses().contains(courseId)){
            announcement.setCourseId(courseId);
            announcement.setProfessorId(id);
            mapper.save(announcement);

            if(c.getAnnouncements() == null){
                c.setAnnouncements(new HashSet<String>());
            }
            c.getAnnouncements().add(announcement.getId());
            mapper.save(c);
            return mapper.load(Course.class, courseId).getAnnouncements();
        }
        return null;
    }

    public Announcement getAnnouncement(String id, String courseId, String announcementId) {
        Professor p = mapper.load(Professor.class, id);
        Course c = mapper.load(Course.class, courseId);
        if(p != null && c != null
                && p.getCourses() != null && p.getCourses().contains(courseId)
                && c.getAnnouncements() != null && c.getAnnouncements().contains(announcementId)) {
            return mapper.load(Announcement.class, announcementId);
        }
        return null;
    }
}
