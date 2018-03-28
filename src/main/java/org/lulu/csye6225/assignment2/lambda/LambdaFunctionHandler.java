package org.lulu.csye6225.assignment2.lambda;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.*;

public class LambdaFunctionHandler implements RequestHandler<DynamodbEvent, String> {
//    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//            .withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).withRegion(Regions.US_WEST_2).build();
//    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    private static Map<String, String> courseTopics = new HashMap<>();
    private static final String TOPIC_PREFIX = "NotificationForCourse-";

    static {
        ListTopicsResult listTopicsResult = SNS_CLIENT.listTopics();
        String nextToken = listTopicsResult.getNextToken();
        List<Topic> topics = listTopicsResult.getTopics();

        // ListTopicResult contains only 100 topics hence use next token to get
        // next 100 topics.
        while (nextToken != null) {
            listTopicsResult = SNS_CLIENT.listTopics(nextToken);
            nextToken = listTopicsResult.getNextToken();
            topics.addAll(listTopicsResult.getTopics());
        }
        for(Topic p : topics){
            if(p.getTopicArn().contains(TOPIC_PREFIX)){
                courseTopics.put(p.getTopicArn().split(TOPIC_PREFIX)[1], p.getTopicArn());
            }
        }
    }

    @Override
    public String handleRequest(DynamodbEvent input, Context context) {
        String output = "";
        for (DynamodbStreamRecord record : input.getRecords()) {
            if (record == null)
                continue;

            //get attributes from dynamodbEvent
            Map<String, AttributeValue> attributes = record.getDynamodb().getNewImage();
            String courseId = attributes.get("CourseId").getS();
            output = "An Announcement for course - courseId: " + courseId
                    + " from professor - professorId: " + attributes.get("ProfessorId").getS()
                    + ". Title: " + attributes.get("Title").getS()
                    + ". Content: " + attributes.get("Content").getS();
            context.getLogger().log("output: " + output);

            sendEmailNotification(getTopicArn(courseId), output);
        }

        return output;
    }

    private void sendEmailNotification(String arn, String message) {
        PublishRequest publishRequest = new PublishRequest(arn, message);
        SNS_CLIENT.publish(publishRequest);
    }

    public void addTopic(String courseId){
        CreateTopicRequest createTopicRequest = new CreateTopicRequest(TOPIC_PREFIX + courseId);
        CreateTopicResult createTopicResult = SNS_CLIENT.createTopic(createTopicRequest);
        String topicArn = createTopicResult.getTopicArn();
        courseTopics.put(courseId, topicArn);
        System.out.println("create a topic: " + topicArn);
    }

    public void subscribe(String courseId, String email){
        SNS_CLIENT.subscribe(new SubscribeRequest(getTopicArn(courseId), "email", email));
        System.out.println("a new subscribe for topic: {email: " + email + ", courseId: " + courseId);
    }

    private String getTopicArn(String courseId){
        if(courseTopics.get(courseId) == null){
            addTopic(courseId);
        }
        return courseTopics.get(courseId);
    }
}
