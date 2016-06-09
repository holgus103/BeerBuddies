var pg = require('pg');
module.exports = {
    connectionString: "postgres://beerbuddy:qwe123@localhost/beerbuddies",
    /**
     * Hanldes a simple insert
     * query - sql query
     * values - array of values to be parsed into the sql string
     */
    handleInsert: function(query, values){
        var client = new pg.Client(this.connectionString);
        client.connect(function(err) {
            client.query(query,values, function(err, result) {
                if(err) {
                  return err
                }
                client.end();
                return null;
            });            
        });
    }
}

