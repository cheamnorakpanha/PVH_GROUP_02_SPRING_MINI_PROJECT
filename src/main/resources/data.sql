INSERT INTO app_users
VALUES
( gen_random_uuid(),'dine', 'diixnew@gmail.com', '$2b$10$EAnYH9ozomFQGwsBV9a1dOt.wxo/1vtY/xNz7HfyafFpzmyimvvRq',1,0, 'https://static.wikia.nocookie.net/pororo-fanon/images/8/82/Pororo.png/revision/latest?cb=20220225043217', false,NOW());

DELETE FROM app_users WHERE username = 'endy'