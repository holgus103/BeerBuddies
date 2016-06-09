
var dbCommon = require('./common.js');
module.exports = {
    
    registerNewUser: function(username, password, mail){
        dbCommon.handleInsert('INSERT INTO "BeerBuddy".PROFILES(USERNAME,EMAIL,PASSWORD) VALUES($1::VARCHAR,$2::VARCHAR,$3::VARCHAR)',[username.toString(), mail.toString(), password.toString()]);
    },
     
    loginUser: function(username, password){
         
    },
     
    updateLocation: function(){
         
    }    
}


