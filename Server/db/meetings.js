// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');

module.exports = {
    createMeeting: function(profileId, start, end, type, callback){
        dbCommon.handleQuery('INSERT INTO "BeerBuddy".MEETINGS(ownerid, meetingstart, meetingend, type) VALUES($1::int, $2::timestamp, $3::timestamp, $4::smallint)',
        [profileId, start, end, type],
        callback);
    }
}

