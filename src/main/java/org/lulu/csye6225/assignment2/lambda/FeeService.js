exports.handler = (event, context, callback) => {
    // TODO implement
    const oneCourseFee = 6000;
    event.fee = event.courseNumber * oneCourseFee;
    callback(null, event);
};