
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
    }
}