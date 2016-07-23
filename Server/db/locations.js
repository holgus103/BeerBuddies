// glob module
var glob = require('../commonGlobal');
// dbCommon module
var dbCommon = require('./commonDB');

module.exports = {
        /**
         * 
         * @param {type} profileId
         * @param {type} callback
         * @returns {undefined}
         */
        setLocationsAsOutdated: function(profileId, callback){
             dbCommon.handleQuery(
                    'UPDATE "BeerBuddy".LOCATIONS SET isCurrent = \'0\' WHERE "BeerBuddy".LOCATIONS.profileId = $1::int AND isCurrent = \'1\'',
                    [profileId],
                    callback);
        },
        
        /**
         * 
         * @param {type} profileId
         * @param {type} longitude
         * @param {type} latitude
         * @param {type} callback
         * @returns {undefined}
         */
        addNewProfileLocation: function(profileId, longitude, latitude, callback){
             dbCommon.handleQuery(
                    'INSERT INTO "BeerBuddy".LOCATIONS(profileId, longitude, latitude,isCurrent) VALUES($1::int, $2::float8, $3::float8, \'1\')',
                    [profileId, longitude, latitude],
                    callback);
        }
}