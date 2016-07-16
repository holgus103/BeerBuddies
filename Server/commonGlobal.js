
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
    routes: {
        UPDATE_LOCATION: '/updateLocation',
        REGISTER: '/register',
        GET_BUDDIES: '/getBuddies',
        SEND_MESSAGE: '/sendMessage'
    },
    OkResponse: {status: 1}
    
}