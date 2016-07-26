
module.exports = {
    debug: true,
    /**
     * Logs an error if debug is enabled
     * @param {type} err
     * @returns {undefined}
     */
    logError: function(err){
        if(this.debug){
            console.log(err);
        }
    },
    sendAck: function(res, err){
        if(err){
            res.send(JSON.stringify(this.FailResponse));
        }
        else{
            res.send(JSON.stringify(this.OkResponse));
        }
    },
    routes: {
        UPDATE_LOCATION: '/updateLocation',
        REGISTER: '/register',
        GET_BUDDIES: '/getBuddies',
        SEND_MESSAGE: '/sendMessage',
        GET_MESSAGES: '/getMessages',
        CREATE_MEETING: '/createMeeting',
        GET_MEETINGS: '/getMeetings',
        JOIN_MEETING: '/joinMeeting'
    },
    OkResponse: {status: 1},
    FailResponse: {status: 0}
    
}