# Jersey-Student-Information-System
NEU18Spring-CSYE6225


**ElasticBeanstalk URL**
 
jerseystudentinform-luluzhang-env.us-west-2.elasticbeanstalk.com


**Requirement:**

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

**URIs:**

/programs GET, POST.

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
