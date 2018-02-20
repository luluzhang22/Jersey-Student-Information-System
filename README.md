# Jersey-Student-Information-System
NEU18Spring-CSYE6225

Requirement:
. StudentInformationSystem 
     - The system manages Students and Programs.
     - Every Program has Courses
     - Every Course has Lectures, and each lecture will have notes, and associated material
     - Every Course will have a board
     - Every Course will have a roster 
     - Every Course has enrolled Students
     - Every Student has information in the system 
          - Name        
          - StudentId
          - image     
          - courses enrolled
          - program name
. When thinking of the URIs - think of using names. 
. Step 1 - Design the RESTful API first. 
      - Identify the nouns in the API
     - /classes/{courseId}  eg. /classes/csye6225
     - understand the relationship between the objects 
     - And then illustrate the relationship between the Resources
. Step 2 - Identify the GET, POST, PUT, or DELETE operations for each API
. Step 3 - Implement the APIs in code. 
. Step 4 - Deploy using ElasticBeanstalk
. Your Resources are all going to stay in Memory. 

ElasticBeanstalk URL: jerseystudentinform-luluzhang-env.us-west-2.elasticbeanstalk.com

URIs:
/programs GET, POST
/programs/{programId} PUT, DELETE, GET
/programs/{programId}/courses GET, POST
/programs/{programId}/courses/{courseId} GET, PUT, DELETE
/programs/{programId}/students GET, POST
/programs/{programId}/students/{studentId} GET, PUT, DELETE

/students GET
/students/{studentId} GET
/students/{studentId}/courses GET
/students/{studentId}/courses/{courseId} GET, DELETE, POST

/courses GET
/courses/{courseId} GET
/courses/{courseId}/students GET
/courses/{courseId}/students/{studentId} GET, DELETE, POST
/courses/{courseId}/lectures GET, POST
/courses/{courseId}/lectures/{lectureId} GET, PUT, DELETE
