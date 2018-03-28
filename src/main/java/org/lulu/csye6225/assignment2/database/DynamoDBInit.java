package org.lulu.csye6225.assignment2.database;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import java.util.ArrayList;

public class DynamoDBInit {
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider()).withRegion(Regions.US_WEST_2).build();
    //	private static DynamoDB dynamoDB = new DynamoDB(client);
    private static DynamoDBMapper mapper = new DynamoDBMapper(client);

    static {
        createTable("Student", 3L, 3L, "Id", ScalarAttributeType.S, null, null);
        createTable("Program", 3L, 3L, "Id", ScalarAttributeType.S, null, null);
        createTable("Course", 3L, 3L, "Id", ScalarAttributeType.S, null, null);
        createTable("Professor", 3L, 3L, "Id", ScalarAttributeType.S, null, null);
        createTable("Announcement", 3L, 3L, "Id", ScalarAttributeType.S, null, null);
    }

    private static void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits, String hashKeyName, ScalarAttributeType hashKeyType, String rangeKeyName, ScalarAttributeType rangeKeyType) {
        try {

            ArrayList<KeySchemaElement> keySchema = new ArrayList<>();
            keySchema.add(new KeySchemaElement()
                    .withAttributeName(hashKeyName)
                    .withKeyType(KeyType.HASH));

            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
            attributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName(hashKeyName)
                    .withAttributeType(hashKeyType));

            if (rangeKeyName != null) {
                keySchema.add(new KeySchemaElement()
                        .withAttributeName(rangeKeyName)
                        .withKeyType(KeyType.RANGE));
                attributeDefinitions.add(new AttributeDefinition()
                        .withAttributeName(rangeKeyName)
                        .withAttributeType(rangeKeyType));
            }

            CreateTableRequest request = new CreateTableRequest()
                    .withTableName(tableName)
                    .withKeySchema(keySchema)
                    .withAttributeDefinitions(attributeDefinitions)
                    .withProvisionedThroughput(new ProvisionedThroughput()
                            .withReadCapacityUnits(readCapacityUnits)
                            .withWriteCapacityUnits(writeCapacityUnits));

            TableUtils.createTableIfNotExists(client, request);
            TableUtils.waitUntilActive(client, tableName);

        } catch (Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    public static DynamoDBMapper getMapper() {
        return mapper;
    }
}
