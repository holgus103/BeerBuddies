// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');

module.exports = {
        /**
         * Adds a new message for a meeting
         * @param {type} sender
         * @param {type} recipient
         * @param {type} body
         * @param {type} callback
         * @returns {undefined}
         */
        sendMessage: function(profileId, meetindId, body, callback){
            dbCommon.handleQuery('INSERT INTO "BeerBuddy".MESSAGES(senderid, "time", content, meetingid) VALUES($1::int, $2::timestamp, $3::varchar, $4::int)',
            [profileId, meetindId, body, new Date()],
            callback);
            
        },
        
        /**
         * Returns messages for a meeting newer than date
         * @param {type} meetingId
         * @param {type} date
         * @param {type} callback
         * @returns {undefined}
         */
        getMessagesForMeeting: function(meetingId, date, callback){
            dbCommon.handleQuery('SELECT * FROM "BeerBuddy".MESSAGES WHERE meetingId = $1::int AND "time" > $2::timestamp',
            [meetingId, date],
            callback);
        }
}