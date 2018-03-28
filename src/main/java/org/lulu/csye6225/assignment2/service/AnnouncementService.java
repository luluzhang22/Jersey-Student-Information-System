package org.lulu.csye6225.assignment2.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.lulu.csye6225.assignment2.database.DynamoDBInit;
import org.lulu.csye6225.assignment2.model.Announcement;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementService {
    private static DynamoDBMapper mapper = DynamoDBInit.getMapper();

    public List<Announcement> getAllAnnouncement(){
        return new ArrayList<>(mapper.scan(Announcement.class, new DynamoDBScanExpression()));
    }

    public Announcement getAnnouncement(String id){
        return mapper.load(Announcement.class, id);
    }
}
