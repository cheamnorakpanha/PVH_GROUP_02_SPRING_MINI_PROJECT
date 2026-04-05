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

INSERT INTO achievements (achievement_id, title, description, badge, xp_required)
VALUES (gen_random_uuid(), 'First Habit Completed', 'Award when a user complete their first habit.', 'first_habit_badge.png', 50)

INSERT INTO app_user_achievements (app_user_achievement_id, app_user_id, achievement_id)
VALUES (gen_random_uuid(),'4ee30473-7003-42df-ad21-7b90fbbdbe83' ,'800ebe70-e880-42dc-ad16-ee899a942c0e')