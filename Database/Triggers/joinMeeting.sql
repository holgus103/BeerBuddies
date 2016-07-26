-- Function: "BeerBuddy"."joinMeeting"()

-- DROP FUNCTION "BeerBuddy"."joinMeeting"();

CREATE OR REPLACE FUNCTION "BeerBuddy"."joinMeeting"()
  RETURNS trigger AS
$BODY$
DECLARE
	_rec record;
	BEGIN
		SELECT into _rec meetingend, ownerId from "BeerBuddy".meetings WHERE meetingid = NEW.meetingid;
		IF(_rec.meetingend > now() AND _rec.ownerId != NEW.profileId) THEN
			RETURN NEW;
		ELSE
			RETURN NULL;
		END IF;
		
	END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "BeerBuddy"."joinMeeting"()
  OWNER TO beerbuddy;
