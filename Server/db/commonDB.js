// external dependencies
var pg = require('pg');
// glob module
var glob = require('../commonGlobal');

module.exports = {
    connectionString: "postgres://beerbuddy:qwe123@localhost/beerbuddies",
    
    /**
     * Handles a SQL query either INSERT or SELECT
     * @param {type} query
     * @param {type} values
     * @param {type} callback
     * @returns {undefined}
     */
    handleQuery: function(query, values, callback){
        var client = new pg.Client(this.connectionString);
        client.connect(function(err) {
            client.query(query, values, function(err, result) {
                if(err) {
                  glob.logError(err);
                }
                client.end();
                if(callback != null)
                    callback(result == null ? null : result.rows, err);
            });            
        });
    },
    
}

