// external dependencies
var express = require('express');
var app = express();
// profile module
var profiles = require('./db/profiles.js');


app.get('/',function(req,res){
	//profiles.loginUser("juzek","elohaselo");
        res.send("not available");
});
/**
 * Updates user location
 */
app.get('/updateLocation', function(req,res){
        profiles.updateLocation('juzek','asdasdasd', 2.0, 2.0);
        res.send("ok");
});
/**
 * Returns a list of buddies
 */
app.get('/getBuddies', function(req,res){
       profiles.getBuddies('juzek','elohaselo',0.11,
       function(rows){
           res.send(JSON.stringify(rows));
       }); 
});
/**
 * Registers a new user
 */
app.get('/register/:username/:password:/:email',function(req,res){
        profiles.registerNewUser(req.params.username, req.params.password, req.params.email);
        res.send("registered");
});

app.get('/message', function(){
        
});
/**
 * Archaic method for testing, returning some dummy location data
 */
app.get('/points',function(req,res){
	res.send(JSON.stringify(points));
	console.log(req.ip);
});

/**
 * Dummy locations for /points
 * @type Array
 */
points = [
{Latitude: -8.84928155, Longitude: -168.52554438},
{Latitude: 28.80520298, Longitude: 147.79418732},
{Latitude:  -64.66575023,Longitude: -84.83002003},
{Latitude:    -4.53252636,Longitude:    124.45929767},
{Latitude:    -22.76276729,Longitude:    -40.91128354},
{Latitude:    28.86433628,Longitude:    151.0774363},
{Latitude:    -38.63893465,Longitude:    -134.89773345},
{Latitude:    -32.69093763,Longitude:    172.93461251},
{Latitude:    26.58051194,Longitude:    115.76938385},
{Latitude:    -75.83502531,Longitude:    -128.226293}];


app.listen(3000);
