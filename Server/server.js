// external dependencies
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
// profile module
var profiles = require('./db/profiles.js');
var meetings = require('./db/meetings.js');
var global = require('./commonGlobal.js');

app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true }));

app.use(function(req, res, next){
    if(req.body.username == null || req.body.password == null){
        // no credentials supplied
        res.send("FORBIDDEN!")
        res.end();
    }
    else{
        if(req.url == global.routes.REGISTER){
            next();
        }
        else{
            profiles.authenticate(req.body.username, req.body.password,
            function(rows, err){
                 if(rows.length > 0){
                    req.profileid = rows[0].profileid;
                    next();
                }
                else{
                    res.send("FORBIDDEN!")
                    res.end();
                }
             });
        }
    }
})

/**
 * Updates user location
 */
app.post(global.routes.UPDATE_LOCATION, function(req,res){
        profiles.updateLocation(req.profileid,req.body.longitude, req.body.latitude,
        function(rows, err){
            res.send(JSON.stringify(global.OkResponse));
        });
});
/**
 * Returns a list of buddies
 */
app.post(global.routes.GET_BUDDIES, function(req,res){
       profiles.getProfilesByDistance(req.profileid, req.body.distance,
       function(rows, err){
           res.send(JSON.stringify(rows));
       }); 
});
/**
 * Registers a new user
 */
app.post(global.routes.REGISTER,function(req, res){
        profiles.registerNewUser(req.body.username, req.body.password, req.body.email, 
        function(rows, err){
            res.send(JSON.stringify(global.OkResponse));
        });
});

app.post(global.routes.CREATE_MEETING, function(req, res){
        meetings.createMeeting(req.profileid, req.body.meetingStart, req.body.meetingStop, req.body.meetingType,
        function(rows, err){
            res.send(JSON.stringify(global.OkResponse));
        });
});

app.post(global.routes.GET_MEETINGS, function(req, res){
        meetings.getMeetings(req.profileid, req.body.distance, 
        function(rows, err){
            res.send(JSON.stringify(rows));
        });
});

app.post(global.routes.JOIN_MEETING, function(req, res){
        meetings.joinMeeting(req.profileid, req.body.meetingId,
        function(rows, err){
            global.sendAck(res, err);
        });
});

app.post

app.listen(3000);
