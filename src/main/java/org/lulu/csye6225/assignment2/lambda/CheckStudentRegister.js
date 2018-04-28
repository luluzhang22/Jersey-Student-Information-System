var AWS = require("aws-sdk");
exports.handler = (event, context, callback) => {
     AWS.config.update({
      region: "us-west-2"
    });
    
    const docClient = new AWS.DynamoDB.DocumentClient();
    const params = {
        TableName: 'Student',
        Key: {
            Id: event.studentId
        }
    }
    
    //find student table to get student info and then check if student has enrolled courses
    docClient.get(params, function(err, data) {
        if(err){
            console.log("error to get student Info");
        }else{
            event.enrolled = 0;
            if(data && data.Item){
                //get student info
                const student = data.Item;
                event.student = student.Name;
                if(student.Courses.values.length > 0){
                    event.enrolled = 1;
                    event.courseNumber = student.Courses.values.length;
                }
            }
            callback(null, event);
        }
    })
    
};