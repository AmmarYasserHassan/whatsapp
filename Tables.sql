-- create database
CREATE DATABASE IF NOT EXISTS whatsapp;

-- use created database
USE whatsapp;

-- Users table
CREATE TABLE IF NOT EXISTS USERS (
 id SERIAL NOT NULL,
 mobile_number VARCHAR(20),
 display_name VARCHAR(100) NOT NULL,
 display_picture TEXT,
 user_status TEXT NOT NULL DEFAULT 'Available',
 verified BOOLEAN NOT NULL DEFAULT FALSE,
 verification_code UUID,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
 PRIMARY KEY (mobile_number)
);

-- chats table
CREATE TABLE IF NOT EXISTS CHATS (
 id SERIAL,
 mobile_number VARCHAR(20) NOT NULL,
 is_archived BOOLEAN NOT NULL DEFAULT FALSE,
 is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
 PRIMARY KEY (id),
 FOREIGN KEY (mobile_number) REFERENCES USERS (mobile_number)
);

-- group chats table
CREATE TABLE IF NOT EXISTS GROUP_CHATS (
 id SERIAL,
 group_name VARCHAR(100) NOT NULL,
 display_picture TEXT,
 created_by VARCHAR(20) NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
 PRIMARY KEY (id),
 FOREIGN KEY (created_by) REFERENCES USERS (mobile_number)
);

-- group chats members table
CREATE TABLE IF NOT EXISTS GROUP_CHAT_MEMBERS (
 id SERIAL,
 group_chat_id INT NOT NULL,
 mobile_number VARCHAR(20) NOT NULL,
 is_archived BOOLEAN NOT NULL DEFAULT FALSE,
 is_admin BOOLEAN NOT NULL DEFAULT FALSE,
 join_at TIMESTAMP WITH TIME ZONE NOT NULL,
 left_at TIMESTAMP WITH TIME ZONE,
 PRIMARY KEY (id),
 FOREIGN KEY (group_chat_id) REFERENCES GROUP_CHATS (id),
 FOREIGN KEY (mobile_number) REFERENCES USERS (mobile_number)
);

-- blocked users table
CREATE TABLE IF NOT EXISTS BLOCKED (
 id SERIAL,
 blocker_mobile_number VARCHAR(20) NOT NULL,
 blocked_mobile_number VARCHAR(20) NOT NULL,
 PRIMARY KEY (id),
 FOREIGN KEY (blocker_mobile_number) REFERENCES USERS (mobile_number),
 FOREIGN KEY (blocked_mobile_number) REFERENCES USERS (mobile_number)
);

-- reported users table
 CREATE TABLE IF NOT EXISTS REPORTED (
 id SERIAL,
 reporter_mobile_number VARCHAR(20) NOT NULL,
 reported_mobile_number VARCHAR(20) NOT NULL,
 PRIMARY KEY (id),
 FOREIGN KEY (reporter_mobile_number) REFERENCES USERS (mobile_number),
 FOREIGN KEY (reported_mobile_number) REFERENCES USERS (mobile_number)
);