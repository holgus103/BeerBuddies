// external dependencies
var express = require('express');
var app = express();
// profile module
var profiles = require('./db/profiles.js');
var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true }));

app.use(function(req, res, next){
    if(req.body.username == null || req.body.password == null){
        // no credentials supplied
        res.send("FORBIDDEN!")
        res.end();
    }
    else{
         profiles.authenticate(req.body.username, req.body.password,
         function(rows){
             if(rows.length > 0){
                req.profileid = rows[0].profileid;
                next();
            }
            else{
                res.send("FORBIDDEN!")
                res.end();
            }
         })
    }
})
app.get('/',function(req,res){
	//profiles.loginUser("juzek","elohaselo");
        res.send("not available");
});
/**
 * Updates user location
 */
app.post('/updateLocation', function(req,res){
        profiles.updateLocation(req.profileid,req.body.longitude, req.body.latitude, function(){
            res.send(JSON.stringify(rows[0]));
        });
});
/**
 * Returns a list of buddies
 */
app.post('/getBuddies', function(req,res){
       profiles.getProfilesByDistance(req.profileid, req.body.distance,
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
