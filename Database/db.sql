--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-07-25 01:30:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 8 (class 2615 OID 16997)
-- Name: BeerBuddy; Type: SCHEMA; Schema: -; Owner: beerbuddy
--

CREATE SCHEMA "BeerBuddy";


ALTER SCHEMA "BeerBuddy" OWNER TO beerbuddy;

SET search_path = "BeerBuddy", pg_catalog;

--
-- TOC entry 191 (class 1255 OID 17135)
-- Name: checkmeeting(); Type: FUNCTION; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE FUNCTION checkmeeting() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
	BEGIN
		IF(now() < (SELECT meetingend from "BeerBuddy".meetings WHERE meetingid = NEW.meetingid)) THEN
			RETURN NEW;
		ELSE
			RETURN NULL;
		END IF;
		
	END
$$;


ALTER FUNCTION "BeerBuddy".checkmeeting() OWNER TO beerbuddy;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 17039)
-- Name: locations; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE locations (
    profileid bigint NOT NULL,
    updatetime timestamp without time zone DEFAULT now() NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    iscurrent bit(1)
);


ALTER TABLE locations OWNER TO beerbuddy;

--
-- TOC entry 189 (class 1259 OID 17050)
-- Name: meetingreview; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE meetingreview (
    meetingreviewid character varying NOT NULL,
    posterid bigint NOT NULL,
    meetingid bigint NOT NULL,
    comment character varying(2000) NOT NULL,
    value smallint NOT NULL,
    timeposted timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE meetingreview OWNER TO beerbuddy;

--
-- TOC entry 188 (class 1259 OID 17044)
-- Name: meetings; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE meetings (
    meetingid bigint NOT NULL,
    ownerid bigint NOT NULL,
    meetingstart timestamp without time zone NOT NULL,
    meetingend timestamp without time zone NOT NULL,
    type smallint NOT NULL
);


ALTER TABLE meetings OWNER TO beerbuddy;

--
-- TOC entry 187 (class 1259 OID 17042)
-- Name: meetings_meetingid_seq; Type: SEQUENCE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE SEQUENCE meetings_meetingid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE meetings_meetingid_seq OWNER TO beerbuddy;

--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 187
-- Name: meetings_meetingid_seq; Type: SEQUENCE OWNED BY; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER SEQUENCE meetings_meetingid_seq OWNED BY meetings.meetingid;


--
-- TOC entry 182 (class 1259 OID 17001)
-- Name: messages; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE messages (
    senderid bigint NOT NULL,
    "time" timestamp without time zone DEFAULT now() NOT NULL,
    content character varying(500) NOT NULL,
    meetingid bigint
);


ALTER TABLE messages OWNER TO beerbuddy;

--
-- TOC entry 184 (class 1259 OID 17025)
-- Name: profiles; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE profiles (
    profileid bigint NOT NULL,
    password character varying(64) NOT NULL,
    username character varying(30) NOT NULL,
    email character varying(50) NOT NULL
);


ALTER TABLE profiles OWNER TO beerbuddy;

--
-- TOC entry 183 (class 1259 OID 17014)
-- Name: profiles_profileid_seq; Type: SEQUENCE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE SEQUENCE profiles_profileid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE profiles_profileid_seq OWNER TO beerbuddy;

--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 183
-- Name: profiles_profileid_seq; Type: SEQUENCE OWNED BY; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER SEQUENCE profiles_profileid_seq OWNED BY profiles.profileid;


--
-- TOC entry 190 (class 1259 OID 17058)
-- Name: profilestomeetings; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE profilestomeetings (
    meetingid bigint NOT NULL,
    profileid bigint NOT NULL,
    "time" timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE profilestomeetings OWNER TO beerbuddy;

--
-- TOC entry 185 (class 1259 OID 17031)
-- Name: rating; Type: TABLE; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TABLE rating (
    ratingid character varying NOT NULL,
    profileid bigint NOT NULL,
    authorid bigint NOT NULL,
    type smallint NOT NULL,
    timeposted timestamp without time zone DEFAULT now() NOT NULL,
    value smallint NOT NULL,
    comment character varying(2000) NOT NULL
);


ALTER TABLE rating OWNER TO beerbuddy;

--
-- TOC entry 2016 (class 2604 OID 17047)
-- Name: meetingid; Type: DEFAULT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY meetings ALTER COLUMN meetingid SET DEFAULT nextval('meetings_meetingid_seq'::regclass);


--
-- TOC entry 2013 (class 2604 OID 17028)
-- Name: profileid; Type: DEFAULT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY profiles ALTER COLUMN profileid SET DEFAULT nextval('profiles_profileid_seq'::regclass);


--
-- TOC entry 2158 (class 0 OID 17039)
-- Dependencies: 186
-- Data for Name: locations; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY locations (profileid, updatetime, longitude, latitude, iscurrent) FROM stdin;
\.


--
-- TOC entry 2161 (class 0 OID 17050)
-- Dependencies: 189
-- Data for Name: meetingreview; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY meetingreview (meetingreviewid, posterid, meetingid, comment, value, timeposted) FROM stdin;
\.


--
-- TOC entry 2160 (class 0 OID 17044)
-- Dependencies: 188
-- Data for Name: meetings; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY meetings (meetingid, ownerid, meetingstart, meetingend, type) FROM stdin;
411	1	2016-07-25 01:18:34.208803	2016-07-26 01:18:34.208803	0
1	1	2016-07-25 01:23:43.488547	2016-07-26 01:23:43.488547	0
2	1	2016-07-15 01:27:44.483504	2016-07-18 01:27:44.483504	0
\.


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 187
-- Name: meetings_meetingid_seq; Type: SEQUENCE SET; Schema: BeerBuddy; Owner: beerbuddy
--

SELECT pg_catalog.setval('meetings_meetingid_seq', 411, true);


--
-- TOC entry 2154 (class 0 OID 17001)
-- Dependencies: 182
-- Data for Name: messages; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY messages (senderid, "time", content, meetingid) FROM stdin;
\.


--
-- TOC entry 2156 (class 0 OID 17025)
-- Dependencies: 184
-- Data for Name: profiles; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY profiles (profileid, password, username, email) FROM stdin;
1	asd	asd	asd
\.


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 183
-- Name: profiles_profileid_seq; Type: SEQUENCE SET; Schema: BeerBuddy; Owner: beerbuddy
--

SELECT pg_catalog.setval('profiles_profileid_seq', 713, true);


--
-- TOC entry 2162 (class 0 OID 17058)
-- Dependencies: 190
-- Data for Name: profilestomeetings; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY profilestomeetings (meetingid, profileid, "time") FROM stdin;
1	1	2016-07-25 01:23:54.564507
1	1	2016-07-25 01:26:18.931339
\.


--
-- TOC entry 2157 (class 0 OID 17031)
-- Dependencies: 185
-- Data for Name: rating; Type: TABLE DATA; Schema: BeerBuddy; Owner: beerbuddy
--

COPY rating (ratingid, profileid, authorid, type, timeposted, value, comment) FROM stdin;
\.


--
-- TOC entry 2028 (class 2606 OID 17057)
-- Name: meetingreviewid; Type: CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY meetingreview
    ADD CONSTRAINT meetingreviewid PRIMARY KEY (meetingreviewid);


--
-- TOC entry 2026 (class 2606 OID 17049)
-- Name: meetingsid; Type: CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY meetings
    ADD CONSTRAINT meetingsid PRIMARY KEY (meetingid);


--
-- TOC entry 2021 (class 2606 OID 17030)
-- Name: profileid; Type: CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT profileid PRIMARY KEY (profileid);


--
-- TOC entry 2023 (class 2606 OID 17038)
-- Name: ratingid; Type: CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY rating
    ADD CONSTRAINT ratingid PRIMARY KEY (ratingid);


--
-- TOC entry 2019 (class 1259 OID 17116)
-- Name: fki_meetings_message_fk; Type: INDEX; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE INDEX fki_meetings_message_fk ON messages USING btree (meetingid);


--
-- TOC entry 2024 (class 1259 OID 17122)
-- Name: profiles_locations_fk; Type: INDEX; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE INDEX profiles_locations_fk ON locations USING btree (profileid);


--
-- TOC entry 2039 (class 2620 OID 17137)
-- Name: checkupdate; Type: TRIGGER; Schema: BeerBuddy; Owner: beerbuddy
--

CREATE TRIGGER checkupdate BEFORE INSERT ON profilestomeetings FOR EACH ROW EXECUTE PROCEDURE checkmeeting();


--
-- TOC entry 2036 (class 2606 OID 17106)
-- Name: meetings_meetingreview_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY meetingreview
    ADD CONSTRAINT meetings_meetingreview_fk FOREIGN KEY (meetingid) REFERENCES meetings(meetingid);


--
-- TOC entry 2030 (class 2606 OID 17111)
-- Name: meetings_message_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY messages
    ADD CONSTRAINT meetings_message_fk FOREIGN KEY (meetingid) REFERENCES meetings(meetingid);


--
-- TOC entry 2038 (class 2606 OID 17101)
-- Name: meetings_profilestomeetings_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY profilestomeetings
    ADD CONSTRAINT meetings_profilestomeetings_fk FOREIGN KEY (meetingid) REFERENCES meetings(meetingid);


--
-- TOC entry 2033 (class 2606 OID 17117)
-- Name: profiles_locations_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY locations
    ADD CONSTRAINT profiles_locations_fk FOREIGN KEY (profileid) REFERENCES profiles(profileid) ON DELETE CASCADE;


--
-- TOC entry 2035 (class 2606 OID 17096)
-- Name: profiles_meetingreview_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY meetingreview
    ADD CONSTRAINT profiles_meetingreview_fk FOREIGN KEY (posterid) REFERENCES profiles(profileid);


--
-- TOC entry 2034 (class 2606 OID 17128)
-- Name: profiles_meetings_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY meetings
    ADD CONSTRAINT profiles_meetings_fk FOREIGN KEY (ownerid) REFERENCES profiles(profileid) ON DELETE CASCADE;


--
-- TOC entry 2029 (class 2606 OID 17071)
-- Name: profiles_messages_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY messages
    ADD CONSTRAINT profiles_messages_fk FOREIGN KEY (senderid) REFERENCES profiles(profileid);


--
-- TOC entry 2037 (class 2606 OID 17061)
-- Name: profiles_profilestomeetings_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY profilestomeetings
    ADD CONSTRAINT profiles_profilestomeetings_fk FOREIGN KEY (profileid) REFERENCES profiles(profileid);


--
-- TOC entry 2031 (class 2606 OID 17081)
-- Name: profiles_rating_fk; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY rating
    ADD CONSTRAINT profiles_rating_fk FOREIGN KEY (profileid) REFERENCES profiles(profileid);


--
-- TOC entry 2032 (class 2606 OID 17086)
-- Name: profiles_rating_fk1; Type: FK CONSTRAINT; Schema: BeerBuddy; Owner: beerbuddy
--

ALTER TABLE ONLY rating
    ADD CONSTRAINT profiles_rating_fk1 FOREIGN KEY (authorid) REFERENCES profiles(profileid);


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 8
-- Name: BeerBuddy; Type: ACL; Schema: -; Owner: beerbuddy
--

REVOKE ALL ON SCHEMA "BeerBuddy" FROM PUBLIC;
REVOKE ALL ON SCHEMA "BeerBuddy" FROM beerbuddy;
GRANT ALL ON SCHEMA "BeerBuddy" TO beerbuddy;


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 186
-- Name: locations; Type: ACL; Schema: BeerBuddy; Owner: beerbuddy
--

REVOKE ALL ON TABLE locations FROM PUBLIC;
REVOKE ALL ON TABLE locations FROM beerbuddy;
GRANT ALL ON TABLE locations TO PUBLIC;


-- Completed on 2016-07-25 01:30:42

--
-- PostgreSQL database dump complete
--

