exports.handler = (event, context, callback) => {
    // TODO implement
    
    callback(null, "Bill of student - " + event.student + ' :' + event.fee);
};