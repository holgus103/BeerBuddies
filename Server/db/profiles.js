// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');

module.exports = {
    /**
     * Registers a new user 
     * @param {type} username
     * @param {type} password
     * @param {type} mail
     * @returns {undefined}
     */
    registerNewUser: function(username, password, mail, callback){
        dbCommon.handleQuery(
                'INSERT INTO "BeerBuddy".PROFILES(USERNAME,EMAIL,PASSWORD) VALUES($1::VARCHAR,$2::VARCHAR,$3::VARCHAR)',
                [username.toString(), mail.toString(), password.toString()],
                callback);
    },
    /**
     * Sets other user locations as outdated and adds new location that is set to current
     * @param {type} username
     * @param {type} password
     * @param {type} longitude
     * @param {type} latitude
     * @returns {undefined}
     */      
    updateLocation: function(username, password, longitude, latitude, callback){
        dbCommon.authenticate(username, password, function(rows){
            dbCommon.handleQuery(
                    'UPDATE "BeerBuddy".LOCATIONS SET isCurrent = \'0\' WHERE "BeerBuddy".LOCATIONS.profileId = $1::int AND isCurrent = \'1\'',
                    [rows[0].profileid],
                    null);
            dbCommon.handleQuery(
                    'INSERT INTO "BeerBuddy".LOCATIONS(profileId, longitude, latitude, updateTime,isCurrent) VALUES($1::int, $2::float8, $3::float8, $4::timestamp, \'1\')',
                    [rows[0].profileid, longitude, latitude, new Date()],
                    callback);
        });
    },
    /**
     * Returns all budies with the current distance square
     * @param {type} username
     * @param {type} password
     * @param {type} distance
     * @returns {undefined}
     */
    getBuddies: function(username, password, distance, callback){
        dbCommon.authenticate(username, password, function(rows){
            dbCommon.handleQuery(
                    'SELECT longitude, latitude, username FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileId!= $1::int \n\
                    AND @(longitude - (select longitude FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileId = $1::int)) < $2::float8\n\
                    AND @(latitude - (select latitude FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileID = $1::int)) < $2::float8',
                    [rows[0].profileid, distance],
                    callback
                    );
        });
    }
    
}


