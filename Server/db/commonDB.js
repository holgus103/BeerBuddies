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
                    callback(result.rows);
            });            
        });
    },
    
    /**
     * Provides credential check and executes the callback on success
     * @param {type} username 
     * @param {type} password
     * @param {type} callback
     * @returns {undefined}
     */
    authenticate: function(username, password, callback){
        var client = new pg.Client(this.connectionString);
        client.connect(function(err){
            client.query('SELECT * from "BeerBuddy".PROFILES where username = $1::VARCHAR and password = $2::VARCHAR',[username,password],
            function(err,result){
               if(err){
                   glob.logError(err);
               } 
               client.end();
               if(callback != null && result.rowCount !=0)
                    callback(result.rows);
            });
        });
    }
}

