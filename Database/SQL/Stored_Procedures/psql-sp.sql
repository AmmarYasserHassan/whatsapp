-- Procedure to report a user
CREATE OR REPLACE FUNCTION report_user(reporter_mobile_number VARCHAR(70), reported_mobile_number VARCHAR(70))
RETURNS void AS $$
BEGIN
  INSERT INTO reported VALUES (reporter_mobile_number, reported_mobile_number);
END;
$$ LANGUAGE plpgsql;

-- Procedure to block a user
CREATE OR REPLACE FUNCTION block_user(blocker_mobile_number VARCHAR(70), blocked_mobile_number VARCHAR(70))
RETURNS void AS $$
BEGIN
  INSERT INTO blocked VALUES (blocker_mobile_number, blocked_mobile_number);
END;
$$ LANGUAGE plpgsql;

-- Procedure to unblock a user
CREATE OR REPLACE FUNCTION unblock_user(blocker VARCHAR(70), blocked VARCHAR(70))
RETURNS void AS $$
BEGIN
  DELETE FROM blocked 
  WHERE blocker_mobile_number LIKE blocker AND blocked_mobile_number LIKE blocked;
END;
$$ LANGUAGE plpgsql;

-- Procedure to add a new user
CREATE OR REPLACE FUNCTION insert_user(mobile_number VARCHAR(20), display_name VARCHAR(100), 
    display_picture TEXT, user_status TEXT, verification_code VARCHAR(10))
RETURNS void AS $$
BEGIN
  INSERT INTO users
    (mobile_number ,display_name , display_picture, user_status, verification_code )
   VALUES
    (mobile_number , display_name , display_picture , user_status, verification_code);
END;
$$ LANGUAGE plpgsql;

-- Procedure to update all the user data at once given that the mobile number
DROP FUNCTION update_user(character varying,character varying,text,text);
CREATE OR REPLACE FUNCTION update_user(user_number VARCHAR(20), display_name_new VARCHAR(100), 
    display_picture_new TEXT, user_status_new TEXT)
RETURNS void AS $$
BEGIN
  UPDATE users SET
    (display_name , display_picture, user_status)
   =
    (display_name_new , display_picture_new , user_status_new)
   WHERE
     mobile_number LIKE user_number;
       
END;
$$ LANGUAGE plpgsql;

-- Procedure to update the user's name given that the mobile number
DROP FUNCTION update_user_name(character varying,character varying);
CREATE OR REPLACE FUNCTION update_user_name(user_number VARCHAR(20), display_name_new VARCHAR(100))
RETURNS void AS $$
BEGIN
  UPDATE users SET
    (display_name)
   =
    (display_name_new)
   WHERE
     mobile_number LIKE user_number; 
       
END;
$$ LANGUAGE plpgsql;

-- Procedure to update the user's display_picture given that the mobile number
DROP FUNCTION update_user_picture(character varying,text);
CREATE OR REPLACE FUNCTION update_user_picture(user_number VARCHAR(20), display_picture_new TEXT)
RETURNS void AS $$
BEGIN
  UPDATE users SET
    display_picture
   =
    display_picture_new
   WHERE
     mobile_number LIKE user_number;
       
END;
$$ LANGUAGE plpgsql;

-- Procedure to update the user's user_status given that the mobile number
DROP FUNCTION update_user_status(character varying,text);
CREATE OR REPLACE FUNCTION update_user_status(user_number VARCHAR(20), user_status_new TEXT)
RETURNS void AS $$
BEGIN
  UPDATE users SET
    user_status
   =
    user_status_new
   WHERE
     mobile_number LIKE user_number;
       
END;
$$ LANGUAGE plpgsql;

-- Procedure to verify a user given that the mobile number
DROP FUNCTION verify_user(character varying);
CREATE OR REPLACE FUNCTION verify_user(user_number VARCHAR(20))
RETURNS void AS $$
BEGIN
  UPDATE users SET
    verified
   =
    TRUE
   WHERE
     mobile_number LIKE user_number;
       
END;
$$ LANGUAGE plpgsql;

-- Procedure to get the previous chats of user given that user_number
CREATE OR REPLACE FUNCTION get_chats(user_number VARCHAR(20)) 
RETURNS refcursor AS $$
  DECLARE
    ref refcursor;
  BEGIN
    OPEN ref FOR 
      SELECT * FROM chats 
      WHERE 
        mobile_number 
          LIKE 
        user_number;
    RETURN ref;
  END;
  $$ LANGUAGE plpgsql;