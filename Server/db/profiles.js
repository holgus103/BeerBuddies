// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');
// location module
var locations = require('./locations');

module.exports = {
    /**
     * Provides credential check and executes the callback on success
     * @param {type} username 
     * @param {type} password
     * @param {type} callback
     * @returns {undefined}
     */
    authenticate: function(username, password, callback){
        dbCommon.handleQuery('SELECT * from "BeerBuddy".PROFILES where username = $1::VARCHAR and password = $2::VARCHAR',[username, password],
        function(rows){
            callback(rows);
        })

    },
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
    updateLocation: function(profileid, longitude, latitude, callback){
            locations.setLocationsAsOutdated(profileid, function(){
                locations.addNewProfileLocation(profileid, longitude, latitude, callback);
            });
    },
    
    /**
     * Returns all budies with the current distance square
     * @param {type} username
     * @param {type} password
     * @param {type} distance
     * @returns {undefined}
     */
    getProfilesByDistance: function(profileid, distance, callback){
            dbCommon.handleQuery(
                    'SELECT longitude, latitude, username FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileId!= $1::int \n\
                    AND @(longitude - (select longitude FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileId = $1::int)) < $2::float8\n\
                    AND @(latitude - (select latitude FROM "BeerBuddy".locations l JOIN "BeerBuddy".profiles p ON l.profileId = p.profileId WHERE l.isCurrent = \'1\' AND p.profileID = $1::int)) < $2::float8',
                    [profileid, distance],
                    callback
                    );
    }
    
}


