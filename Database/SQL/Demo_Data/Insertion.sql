
----------------------------- USERS -------------------------------

-- User 1
INSERT INTO USERS 
VALUES (
	DEFAULT,
	'01000000001',
	'Ahmed',
	'https://images-na.ssl-images-amazon.com/images/M/MV5BMjQyODg5Njc4N15BMl5BanBnXkFtZTgwMzExMjE3NzE@._V1_UY268_CR1,0,182,268_AL_.jpg',
	'I didn''t change, I just grew up. You should try it once.',
	DEFAULT,
	'6637753c-0e8b-11e8-ba89-0ed5f89f718b',
	DEFAULT
);

-- User 2
INSERT INTO USERS 
VALUES (
	DEFAULT,
	'01000000002',
	'Mohamed',
	'https://images-na.ssl-images-amazon.com/images/M/MV5BYzc4ODgyZmYtMGFkZC00NGQyLWJiMDItMmFmNjJiZjcxYzVmXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_UX182_CR0,0,182,268_AL_.jpg',
	'I love buying new things but I hate spending money',
	DEFAULT,
	'66377884-0e8b-11e8-ba89-0ed5f89f718b',
	DEFAULT
);

-- User 3
INSERT INTO USERS 
VALUES (
	DEFAULT,
	'01000000003',
	'Kamal',
	'https://images-na.ssl-images-amazon.com/images/M/MV5BYzc4ODgyZmYtMGFkZC00NGQyLWJiMDItMmFmNjJiZjcxYzVmXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_UX182_CR0,0,182,268_AL_.jpg',
	'Life is too short. Don''t waste it reading my WhatsApp status',
	DEFAULT,
	'66377ac8-0e8b-11e8-ba89-0ed5f89f718b',
	DEFAULT
);

-- User 4
INSERT INTO USERS 
VALUES (
	DEFAULT,
	'01000000004',
	'Ibraheem',
	'https://images-na.ssl-images-amazon.com/images/M/MV5BYzc4ODgyZmYtMGFkZC00NGQyLWJiMDItMmFmNjJiZjcxYzVmXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_UX182_CR0,0,182,268_AL_.jpg',
	'I''m so good at sleeping, I can do it with my eyes CLOSED',
	DEFAULT,
	'66377ce4-0e8b-11e8-ba89-0ed5f89f718b',
	DEFAULT
);


------------------------------- CHATS -------------------------------

-- Chat 1
INSERT INTO CHATS 
VALUES (
	DEFAULT,
	'01000000004',
	TRUE,
	DEFAULT,
	DEFAULT
);

-- Chat 2
INSERT INTO CHATS 
VALUES (
	DEFAULT,
	'01000000002',
	DEFAULT,
	TRUE,
	DEFAULT
);


----------------------------- GROUP_CHATS -------------------------------

-- Group Chat 1
INSERT INTO GROUP_CHATS 
VALUES (
	1,
	'Friends',
	'https://images-na.ssl-images-amazon.com/images/M/MV5BMTg4NzEyNzQ5OF5BMl5BanBnXkFtZTYwNTY3NDg4._V1._CR24,0,293,443_.jpg',
	'01000000001',
	DEFAULT
);


------------------------------- GROUP_CHAT_MEMBERS -------------------------------

-- Member 1
INSERT INTO GROUP_CHAT_MEMBERS 
VALUES (
	DEFAULT,
	1,
	'01000000001',
	DEFAULT,
	TRUE,
	NOW(),
	NULL
);

-- Member 2
INSERT INTO GROUP_CHAT_MEMBERS 
VALUES (
	DEFAULT,
	1,
	'01000000002',
	DEFAULT,
	FALSE,
	NOW(),
	NULL
);


------------------------------- BLOCKED -------------------------------

-- Blocked 1
INSERT INTO BLOCKED 
VALUES (
	DEFAULT,
	'01000000002',
	'01000000004'
);


------------------------------- REPORTED -------------------------------

-- Reported 1
INSERT INTO REPORTED 
VALUES (
	DEFAULT,
	'01000000001',
	'01000000003'
);


------------------------------- For Testing -------------------------------

-- SELECT * FROM USERS;
-- DELETE FROM USERS;
