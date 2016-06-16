var glob = require('../commonGlobal');
var dbCommon = require('./commonDB');
module.exports = {
    /**
     * Registers a new user 
     * @param {type} username
     * @param {type} password
     * @param {type} mail
     * @returns {undefined}
     */
    registerNewUser: function(username, password, mail){
        dbCommon.handleQuery(
                'INSERT INTO "BeerBuddy".PROFILES(USERNAME,EMAIL,PASSWORD) VALUES($1::VARCHAR,$2::VARCHAR,$3::VARCHAR)',
                [username.toString(), mail.toString(), password.toString()],
                null);
    },
    /**
     * Sets other user locations as outdated and adds new location that is set to current
     * @param {type} username
     * @param {type} password
     * @param {type} longitude
     * @param {type} latitude
     * @returns {undefined}
     */      
    updateLocation: function(username, password, longitude, latitude){
        dbCommon.authenticate(username, password, function(rows){
            dbCommon.handleQuery(
                    'UPDATE "BeerBuddy".LOCATIONS SET isCurrent = \'0\' WHERE "BeerBuddy".LOCATIONS.profileId = $1::int AND isCurrent = \'1\'',
                    [rows[0].profileid],
                    null);
            dbCommon.handleQuery(
                    'INSERT INTO "BeerBuddy".LOCATIONS(profileId, longitude, latitude, updateTime,isCurrent) VALUES($1::int, $2::float8, $3::float8, $4::timestamp, \'1\')',
                    [rows[0].profileid, longitude, latitude, new Date()],
                    null);
        });
    },
    /**
     * 
     * @param {type} username
     * @param {type} password
     * @param {type} distance
     * @returns {undefined}
     */
    getBuddies: function(username, password, distance){
        dbCommon.authenticate(username, password, function(rows){
            dbCommon.handleQuery(
                    'SELECT * '
                    );
        });
    }
    
}


