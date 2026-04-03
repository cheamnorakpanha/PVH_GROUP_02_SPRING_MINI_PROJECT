
CREATE TABLE IF NOT EXISTS app_users(
    app_user_id   UUID PRIMARY KEY,
    username VARCHAR(40) NOT NULL,
    email VARCHAR(40) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    level INT NOT NULL,
    xp INT NOT NULL,
    profile_image VARCHAR(255),
    is_verified   BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS achievements (
                                            achievement_id UUID PRIMARY KEY ,
                                            title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    badge VARCHAR(100),
    xp_required INT NOT NULL
    );


CREATE TABLE IF NOT EXISTS app_user_achievements (
                                                     app_user_achievement_id UUID PRIMARY KEY,
                                                     app_user_id   UUID NOT NULL,
                                                     achievement_id UUID NOT NULL,
                                                     CONSTRAINT fk_app_users FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_achievement FOREIGN KEY (achievement_id) REFERENCES achievements (achievement_id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS habits (
                                      habit_id UUID PRIMARY KEY,
                                      title VARCHAR(50) NOT NULL ,
    description VARCHAR(255),
    frequency VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    app_user_id UUID NOT NULL,
    CONSTRAINT fk_app_users FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS habit_logs (
                                          habit_log_id UUID PRIMARY KEY,
                                          log_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          status VARCHAR(100),
    xp_earned INT,
    habit_id UUID NOT NULL,
    CONSTRAINT fk_habits FOREIGN KEY (habit_id) REFERENCES habits (habit_id) ON DELETE CASCADE ON UPDATE CASCADE
    );