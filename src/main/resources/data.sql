INSERT INTO app_users
VALUES (gen_random_uuid(), 'dine', 'diixnew@gmail.com', '$2b$10$EAnYH9ozomFQGwsBV9a1dOt.wxo/1vtY/xNz7HfyafFpzmyimvvRq',
        1, 0, 'https://static.wikia.nocookie.net/pororo-fanon/images/8/82/Pororo.png/revision/latest?cb=20220225043217',
        false, NOW());

INSERT INTO habits (habit_id, title, description, frequency, is_active, app_user_id)
VALUES (gen_random_uuid(), 'Evening Walk', 'Walk for 30 minutes in the evening', 'daily', true,
        '495a0683-8216-4ee3-8f09-94b6b4d6bb58'),
       (gen_random_uuid(), 'Practice English', 'Spend 20 minutes improving English skills', 'daily', true,
        '495a0683-8216-4ee3-8f09-94b6b4d6bb58');

TRUNCATE TABLE app_users CASCADE;