# Jersey-Student-Information-System

## NEU18Spring-CSYE6225

**ElasticBeanstalk URL**

http://jerseystudentinform-luluzhang-env.us-west-2.elasticbeanstalk.com

## Assignmet 1
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

1. programs
    1. /programs    GET, POST.
    2. /programs/{programId}    PUT, DELETE, GET
    3. /programs/{programId}/courses    GET, POST
    4. /programs/{programId}/courses/{courseId}     GET, PUT, DELETE
    5. /programs/{programId}/students   GET, POST
    6. /programs/{programId}/students/{studentId}   GET, PUT, DELETE
2. student
    1. /students    GET
    2. /students/{studentId}    GET
    3. /students/{studentId}/courses    GET
    4. /students/{studentId}/courses/{courseId}     GET, DELETE, POST
3. courses
    1. /courses     GET
    2. /courses/{courseId}      GET
    3. /courses/{courseId}/students     GET
    4. /courses/{courseId}/students/{studentId}     GET, DELETE, POST
    5. /courses/{courseId}/lectures     GET, POST
    6. /courses/{courseId}/lectures/{lectureId}     GET, PUT, DELETE


## Assignment 2

**Requirement:**
1. You will create DynamoDb tables for the Entities defined as part of Assignment 1. 
At the bare minimum you should have a table for 
    1. Students
    2. Courses
    3. Announcements
    4. Professors
2. All your resource creation will now happen directly on the tables. i.e. you will rewrite the ResourceFiles to persist in DDb
3. Students will be enrolled in courses, and each course will have a associated Professor
4. If the professor creates a announcement, all students in the course will get a email. 
5. To build the notification pipeline, you will use AWS lambdas, and the lambdas will trigger once a “Announcement” object is created. The lambda will look at the professor creating the Announcement, identify the course they are teaching, identify the students taking the course and email them the Announcement.Text. For simplicity assume that all courses are active. 

**URIs:**

Different from URIs in the assignment1: add 'ddb/' before all URIs

1. programs
    1. /ddb/programs    GET, POST.
        1. POST body example for programs: {
                              	"name": "Computer Science"
                              }
    2. /ddb/programs/{programId}    PUT, DELETE, GET
    3. /ddb/programs/{programId}/courses    GET, POST
        1. POST body example for courses: {
                                          	"courseId": "CS1234",
                                          	"name": "Algorithm",
                                          	"roster" : "lulu",
                                          	"board": "board for CS1234"
                                          }
    4. /ddb/programs/{programId}/courses/{courseId}     GET, PUT, DELETE
    5. /ddb/programs/{programId}/students   GET, POST
        1. POST body example for students: {
                                           	"studentId": "001810001",
                                           	"name": "Lulu Zhang",
                                           	"email" : "zhang_lulu@outlook.com"
                                           }
    6. /ddb/programs/{programId}/students/{studentId}   GET, PUT, DELETE
2. student
    1. /ddb/students    GET
    2. /ddb/students/{studentId}    GET
    3. /ddb/students/{studentId}/courses    GET
    4. /ddb/students/{studentId}/courses/{courseId}     GET, DELETE, POST
3. courses
    1. /ddb/courses     GET
    2. /ddb/courses/{courseId}      GET
    3. /ddb/courses/{courseId}/students     GET
    4. /ddb/courses/{courseId}/students/{studentId}     GET, DELETE, POST
4. professor
    1. /ddb/professors      GET, POST
        a. POST body example for professor: {
                                            	"name": "Ross"
                                            }
    2. /ddb/professors/{professorId}    PUT, GET, DELETE
    3. /ddb/professors/{professorId}/courses       GET
    4. /ddb/professors/{professorId}/courses/{courseId}     POST, GET, DELETE
    5. /ddb/professors/{professorId}/courses/{courseId}/announcements       GET, POST
        a. POST body example for announcement: {
                                               	"title": "First class time",
                                               	"content": "6:30 PM, Friday"
                                               }
    6. /ddb/professors/{professorId}/courses/{courseId}/announcements/{announcementId}      GET
5. announcement
    1. /ddb/announcements       GET
    2. /ddb/announcements/{announcementId}       GET