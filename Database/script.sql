
CREATE SEQUENCE "BeerBuddy".profiles_profileid_seq;

CREATE TABLE "BeerBuddy".Profiles (
                ProfileID BIGINT NOT NULL DEFAULT nextval('"BeerBuddy".profiles_profileid_seq'),
                password VARCHAR(64) NOT NULL,
                username VARCHAR(30) NOT NULL,
                email VARCHAR(50) NOT NULL,
                CONSTRAINT profileid PRIMARY KEY (ProfileID)
);


ALTER SEQUENCE "BeerBuddy".profiles_profileid_seq OWNED BY "BeerBuddy".Profiles.ProfileID;

CREATE TABLE "BeerBuddy".Rating (
                RatingID VARCHAR NOT NULL,
                ProfileID BIGINT NOT NULL,
                AuthorID BIGINT NOT NULL,
                type SMALLINT NOT NULL,
                timePosted TIMESTAMP NOT NULL,
                value SMALLINT NOT NULL,
                comment VARCHAR(2000) NOT NULL,
                CONSTRAINT ratingid PRIMARY KEY (RatingID)
);


CREATE TABLE "BeerBuddy".Messages (
                MessageID VARCHAR NOT NULL,
                RecipentID BIGINT NOT NULL,
                SenderID BIGINT NOT NULL,
                time TIMESTAMP NOT NULL,
                content VARCHAR(500) NOT NULL,
                CONSTRAINT messageid PRIMARY KEY (MessageID)
);


CREATE TABLE "BeerBuddy".Locations (
                ProfileID BIGINT NOT NULL,
                updateTime TIMESTAMP NOT NULL,
                longitude DOUBLE PRECISION NOT NULL,
                latitude DOUBLE PRECISION NOT NULL
);


CREATE SEQUENCE "BeerBuddy".meetings_meetingid_seq;

CREATE TABLE "BeerBuddy".Meetings (
                MeetingID BIGINT NOT NULL DEFAULT nextval('"BeerBuddy".meetings_meetingid_seq'),
                OwnerID BIGINT NOT NULL,
                meetingStart TIMESTAMP NOT NULL,
                meetingEnd TIMESTAMP NOT NULL,
                type SMALLINT NOT NULL,
                CONSTRAINT meetingsid PRIMARY KEY (MeetingID)
);


ALTER SEQUENCE "BeerBuddy".meetings_meetingid_seq OWNED BY "BeerBuddy".Meetings.MeetingID;

CREATE TABLE "BeerBuddy".MeetingReview (
                MeetingReviewID VARCHAR NOT NULL,
                PosterID BIGINT NOT NULL,
                MeetingID BIGINT NOT NULL,
                comment VARCHAR(2000) NOT NULL,
                value SMALLINT NOT NULL,
                timePosted TIMESTAMP NOT NULL,
                CONSTRAINT meetingreviewid PRIMARY KEY (MeetingReviewID)
);


CREATE TABLE "BeerBuddy".ProfilesToMeetings (
                MeetingID BIGINT NOT NULL,
                ProfileID BIGINT NOT NULL,
                time TIMESTAMP NOT NULL
);


ALTER TABLE "BeerBuddy".ProfilesToMeetings ADD CONSTRAINT profiles_profilestomeetings_fk
FOREIGN KEY (ProfileID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".Locations ADD CONSTRAINT profiles_locations_fk
FOREIGN KEY (ProfileID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".Messages ADD CONSTRAINT profiles_messages_fk
FOREIGN KEY (SenderID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".Messages ADD CONSTRAINT profiles_messages_fk1
FOREIGN KEY (RecipentID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".Rating ADD CONSTRAINT profiles_rating_fk
FOREIGN KEY (ProfileID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".Rating ADD CONSTRAINT profiles_rating_fk1
FOREIGN KEY (AuthorID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".Meetings ADD CONSTRAINT profiles_meetings_fk
FOREIGN KEY (OwnerID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".MeetingReview ADD CONSTRAINT profiles_meetingreview_fk
FOREIGN KEY (PosterID)
REFERENCES "BeerBuddy".Profiles (ProfileID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".ProfilesToMeetings ADD CONSTRAINT meetings_profilestomeetings_fk
FOREIGN KEY (MeetingID)
REFERENCES "BeerBuddy".Meetings (MeetingID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE "BeerBuddy".MeetingReview ADD CONSTRAINT meetings_meetingreview_fk
FOREIGN KEY (MeetingID)
REFERENCES "BeerBuddy".Meetings (MeetingID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;