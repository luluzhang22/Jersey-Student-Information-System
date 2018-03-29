package org.lulu.csye6225.assignment2.lambda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lulu.csye6225.assignment1.model.Course;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.Topic;

public class AnnouncementLambdaFunctionHandler implements RequestHandler<DynamodbEvent, String> {

	private static AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).withRegion(Regions.US_WEST_2).build();
	private static AmazonDynamoDB DDB_CLIENT = AmazonDynamoDBClientBuilder.standard()
			.withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).withRegion(Regions.US_WEST_2).build();
	private static DynamoDB ddb = new DynamoDB(DDB_CLIENT);
	private static final String TOPIC_PREFIX = "NotificationForCourse-";

	@Override
	public String handleRequest(DynamodbEvent input, Context context) {
		String output = "";
		if (input.getRecords() != null) {
			for (DynamodbStreamRecord record : input.getRecords()) {
				if (record == null)
					continue;

				// get attributes from dynamodbEvent
				Map<String, AttributeValue> attributes = record.getDynamodb().getNewImage();
				if (attributes != null) {
					Item course = ddb.getTable("Course").getItem("Id", attributes.get("CourseId").getS());
					Item professor = ddb.getTable("Professor").getItem("Id", attributes.get("ProfessorId").getS());
					
					StringBuilder sb = new StringBuilder();
					sb.append("An Announcement for course \"");
					sb.append(course.getString("Id"));
					sb.append("-");
					sb.append(course.getString("Name"));
					sb.append("\" from Professor ");
					sb.append(professor.getString("Name"));
					sb.append(".\n");
					sb.append("Title: \n");
					sb.append(attributes.get("Title").getS());
					sb.append("\nContent: \n");
					sb.append(attributes.get("Content").getS());
					output = sb.toString();
					context.getLogger().log("output: " + output);
					sendEmailNotification(course.getString("TopicArn"), output);
				}
			}
		}

		return output;
	}

	private void sendEmailNotification(String arn, String message) {
		PublishRequest publishRequest = new PublishRequest(arn, message);
		SNS_CLIENT.publish(publishRequest);
	}

	public String addTopic(String courseId) {
		CreateTopicRequest createTopicRequest = new CreateTopicRequest(TOPIC_PREFIX + courseId);
		CreateTopicResult createTopicResult = SNS_CLIENT.createTopic(createTopicRequest);
		String topicArn = createTopicResult.getTopicArn();
		System.out.println("create a topic: " + topicArn);
		return topicArn;
	}
	
	public void removeTopic(String topicArn) {
		DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicArn);
		SNS_CLIENT.deleteTopic(deleteTopicRequest);
		//get request id for DeleteTopicRequest from SNS metadata
		System.out.println("DeleteTopicRequest - " + SNS_CLIENT.getCachedResponseMetadata(deleteTopicRequest));
	}

	public void subscribe(String topicArn, String email) {
		SNS_CLIENT.subscribe(new SubscribeRequest(topicArn, "email", email));
		System.out.println("a new subscribe for topic: {email: " + email + ", topicArn: " + topicArn);
	}
}