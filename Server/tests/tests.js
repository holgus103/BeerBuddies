var meetings = require('./../db/meetings');
var assert = require('chai').assert;

describe("MeetingTests", function(){

    it("getMeetingsTest", function(){
        assert.notEqual(meetings.getMeetings(4, 10, null),null)
    })
});