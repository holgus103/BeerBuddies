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
        [profileId, start, end, type],
        callback);
    },
    
    /**
     * 
     * @param {type} profileId
     * @param {type} callback
     * @returns {undefined}
     */
    getOpenMeetingsForUser: function(profileId, callback){
        dbCommon.handleQuery('SELECT * FROM "BeerBuddy".MEETINGS WHERE profileId = $1::int AND end > $2::timestamp',
        [profileId, new Date()],
        callback);
    },
    /**
     * 
     * @returns {undefined}
     */
    getMeetingsByDistance: function(){
        dbCommon.handleQuery(
                    'SELECT longitude, latitude, start, end, type FROM "BeerBuddy".locations l JOIN "BeerBuddy".meetings m ON l.profileId = m.ownerId WHERE l.isCurrent = \'1\' AND l.profileId!= $1::int \n\
                    AND @(longitude - (select longitude FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileId = $1::int)) < $2::float8\n\
                    AND @(latitude - (select latitude FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileID = $1::int)) < $2::float8',
                    [rows[0].profileid, distance],
                    callback
                    );
    }
    
    
}

