// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');

module.exports = {
    /**
     * 
     * @param {type} profileId
     * @param {type} start
     * @param {type} end
     * @param {type} type
     * @param {type} callback
     * @returns {undefined}
     */
    createMeeting: function(profileId, start, end, type, callback){
        dbCommon.handleQuery('INSERT INTO "BeerBuddy".MEETINGS(ownerid, meetingstart, meetingend, type) VALUES($1::int, $2::timestamp, $3::timestamp, $4::smallint)',
        [profileId, new Date(start).toGMTString(), new Date(end).toGMTString(), type],
        callback);
    },
    
    /**
     * 
     * @param {type} profileId
     * @param {type} callback
     * @returns {undefined}
     */
    getOpenMeetingsForUser: function(profileId, callback){
        dbCommon.handleQuery('SELECT * FROM "BeerBuddy".MEETINGS WHERE profileId = $1::int AND meetingend > now()',
        [profileId, ],
        callback);
    },
    
    /**
     * 
     * @param {type} profileId
     * @param {type} distance
     * @param {type} callback
     * @returns {undefined}
     */
    getMeetings: function(profileId, distance, callback){
        dbCommon.handleQuery(
                    'SELECT meetingid, longitude, latitude, meetingstart, meetingend, type FROM "BeerBuddy".locations l JOIN "BeerBuddy".meetings m ON l.profileId = m.ownerId WHERE l.isCurrent = \'1\' \n\
                    AND @(l.longitude - (select longitude FROM "BeerBuddy".locations l WHERE l.isCurrent = \'1\' AND l.profileId = $1::int)) < $2::float8\n\
                    AND @(latitude - (select latitude FROM "BeerBuddy".locations l WHERE l.isCurrent = \'1\' AND l.profileID = $1::int)) < $2::float8\n\
                    AND meetingend > now() \n\
                    AND m.ownerId != $1::int',
                    [profileId, distance],
                    callback);
    },
    
    joinMeeting: function(profileId, meetingId, callback){
        dbCommon.handleQuery(
                'INSERT INTO "BeerBuddy".profilestomeetings(meetingId, profileId) VALUES($1::int, $2::int)',
                [meetingId, profileId],
                callback);
    }
    
    
}

