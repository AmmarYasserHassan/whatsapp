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

-- Procedure to add a new user
CREATE OR REPLACE FUNCTION insert_user(mobile_number VARCHAR(20), display_name VARCHAR(100), 
    display_picture TEXT, user_status TEXT, verification_code UUID)
RETURNS void AS $$
BEGIN
  INSERT INTO users
    (mobile_number ,display_name , display_picture, user_status, verification_code )
   VALUES
    (mobile_number , display_name , display_picture , user_status, verification_code);
END;
$$ LANGUAGE plpgsql;
