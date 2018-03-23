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

-- Procedure to archieve a chat given the mobile number and the id of the chat
CREATE OR REPLACE FUNCTION archive_chat(user_number VARCHAR(20),intended_chat_id INT)
RETURNS void AS $$
BEGIN
       INSERT INTO archived_chats(chat_id,user_number)

       VALUES  (intended_chat_id,user_number);

 END;
$$ LANGUAGE plpgsql;

-- Procedure to archieve a group chat given the mobile number and the id of the chat
CREATE OR REPLACE FUNCTION archive_group_chat(user_number VARCHAR(20),intended_chat_id INT)
RETURNS void AS $$
BEGIN
       UPDATE group_chats SET is_archived = TRUE
       
       WHERE  created_by LIKE user_number AND  id = intended_chat_id;

 END;
$$ LANGUAGE plpgsql;

-- Procedure to start a chat given 2 mobile numbers
CREATE OR REPLACE FUNCTION start_chat(user_number1 VARCHAR(20),user_number2 VARCHAR(20))
RETURNS void AS $$
BEGIN
      INSERT INTO chats (first_mobile_number,second_mobile_number)
      VALUES (user_number1,user_number2);

END;
$$ LANGUAGE plpgsql;

-- Procedure used by an admin to remove another admin from a group chat given both mobile numbers and the chat id
CREATE OR REPLACE FUNCTION remove_admin_from_a_group_chat(admin_number VARCHAR(20),intended_chat_id INT, removed_admin_number VARCHAR(20))
RETURNS void AS $$
     BEGIN
    IF EXISTS (SELECT * FROM group_chat_members 

                WHERE

                mobile_number LIKE admin_number AND is_admin = TRUE AND  group_chat_id = intended_chat_id)
THEN
UPDATE group_chat_members SET is_admin = FALSE WHERE group_chat_id = intended_chat_id AND mobile_number LIKE removed_admin_number ;
END IF;
END;
$$ LANGUAGE plpgsql;

-- Procedure to delete a group chat given a mobile number and teh chat id
CREATE OR REPLACE FUNCTION delete_group_chat (user_number VARCHAR(20),intended_chat_id INT)

 			RETURNS void AS $$
BEGIN
           DELETE FROM group_chat_members 

           WHERE mobile_number LIKE user_number AND group_chat_id = intended_chat_id ;

      
END;
$$ LANGUAGE plpgsql;



-- Procedure to delete a group chat using a mobile number and the chat id
CREATE OR REPLACE FUNCTION delete_a_group_chat (user_number VARCHAR(20),intended_chat_id INT)
RETURNS void AS $$
BEGIN
IF EXISTS(SELECT * FROM group_chat_members 
          WHERE group_chat_id LIKE intended_chat_id AND mobile_number LIKE user_number AND is_admin = TRUE)
THEN

DELETE FROM group_chat_members WHERE group_chat_id = intended_chat_id;

DELETE FROM group_chats WHERE id = intended_chat_id AND created_by LIKE user_number;
END IF;

END;
$$ LANGUAGE plpgsql;


-- Procedure to add members to a group chat given the admin's mobile number, the chat id and an array of the users' mobile numbers
CREATE OR REPLACE FUNCTION add_members_to_a_group_chat(user_number VARCHAR(20),intended_chat_id INT,member_numbers VARCHAR(20)[])
RETURNS void AS $$

BEGIN
  IF EXISTS( SELECT * FROM group_chat_members 
  	          WHERE mobile_number LIKE user_number AND is_admin = TRUE AND group_chat_id = intended_chat_id
  	        )
  THEN
   for i in array_lower(member_numbers, 1)..array_upper(member_numbers, 1) loop
     execute 'INSERT INTO group_chat_members (group_chat_id,mobile_number)
     VALUES (intended_chat_id,member_numbers[i])';
   END loop;
END IF;
   

END;
$$ language plpgsql;

-- Procedure add admins to a group given the current admin's mobile number, the chat id and an array of numbers to add as admins
CREATE OR REPLACE FUNCTION add_admins_to_a_group_chat(user_number VARCHAR(20),intended_chat_id INT,member_numbers VARCHAR(20)[])
RETURNS void AS $$
BEGIN
  IF EXISTS( SELECT * FROM group_chat_members 
  	          WHERE mobile_number LIKE user_number AND is_admin = TRUE AND group_chat_id = intended_chat_id
  	        )
  THEN
   for i in array_lower(member_numbers, 1)..array_upper(member_numbers, 1) loop
     execute 'INSERT INTO group_chat_members (group_chat_id,mobile_number,is_admin)
     VALUES (intended_chat_id,member_numbers[i],TRUE)';
   END loop;

   END IF;

END;
$$ language plpgsql;

-- Procedure to remove members from group chat given the admin's id, the chat id, and an array of the user's mobile numbers
CREATE OR REPLACE FUNCTION remove_members_from_a_group_chat (user_number VARCHAR(20),intended_chat_id INT,member_numbers VARCHAR(20)[])
RETURNS void AS $$
BEGIN
IF EXISTS( SELECT * FROM group_chat_members 
  	          WHERE mobile_number LIKE user_number AND is_admin = TRUE AND group_chat_id = intended_chat_id
  	        )
THEN

   for i in array_lower(member_numbers, 1)..array_upper(member_numbers, 1) loop
     execute 'DELETE FROM group_chat_members WHERE group_chat_id LIKE intended_chat_id AND mobile_number LIKE member_numbers[i]';
   END loop;
END IF;
END;
$$ LANGUAGE plpgsql;

-- Procedure to create a group chat given the name of the group, a display picture and the number of the creator to be an admin

CREATE OR REPLACE FUNCTION create_group_chat(group_chat_name VARCHAR(20),display_picture_new TEXT, user_number VARCHAR(20))
RETURNS void AS $$
BEGIN
     
 INSERT INTO group_chats (group_name,display_picture,created_by)
 VALUES (group_chat_name,display_picture_new,user_number) RETURNING id;
 
 INSERT INTO group_chat_members (mobile_number,is_admin,group_chat_id)
 VALUES (user_number,true,id);

END;
$$ LANGUAGE plpgsql;