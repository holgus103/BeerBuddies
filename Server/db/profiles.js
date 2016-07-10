// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');
// location module
var locations = require('./locations');

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
            locations.setLocationsAsOutdated(rows[0].profileid, callback);
            locations.addNewProfileLocation(rows[0].profileid, longitude, latitude, callback);
        });
    },
    
    /**
     * Returns all budies with the current distance square
     * @param {type} username
     * @param {type} password
     * @param {type} distance
     * @returns {undefined}
     */
    getProfilesByDistance: function(username, password, distance, callback){
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


