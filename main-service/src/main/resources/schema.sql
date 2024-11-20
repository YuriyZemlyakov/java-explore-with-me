CREATE TABlE IF NOT EXISTS user_table (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS category (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  CONSTRAINT pk_cat PRIMARY KEY (id),
  CONSTRAINT UQ_cat UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS event (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
 annotation VARCHAR(2000) NOT NULL,
 category_id BIGINT NOT NULL,
 confirmed_requests BIGINT,
 created_on TIMESTAMP,
 description VARCHAR(7000),
 event_date TIMESTAMP NOT NULL,
 user_id BIGINT NOT NULL,
 location_lat DECIMAL NOT NULL,
 location_lon DECIMAL NOT NULL,
 paid BOOLEAN NOT NULL,
 participant_limit BIGINT,
 published_on TIMESTAMP,
 request_moderation BOOLEAN,
 state VARCHAR(255),
 title VARCHAR(120) NOT NULL,
 views BIGINT,
 FOREIGN KEY (category_id) REFERENCES category(id),
 FOREIGN KEY (user_id) REFERENCES user_table(id),
 CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS participation (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  event BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  status VARCHAR(120),
  created TIMESTAMP,
  CONSTRAINT pk_part PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user_table(id)
);

CREATE TABLE IF NOT EXISTS compilation (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  title VARCHAR(50) NOT NULL,
  pinned BOOLEAN,
  CONSTRAINT pk_comp PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event_compilation (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  compilation_id BIGINT NOT NULL,
  event_id BIGINT NOT NULL,
  CONSTRAINT pk_ev_comp PRIMARY KEY (id),
  FOREIGN KEY (compilation_id) REFERENCES compilation(id),
  FOREIGN KEY (event_id) REFERENCES event(id)
);