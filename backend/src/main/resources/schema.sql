-- Optional schema reference for production migration tooling.
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    years_experience INT NOT NULL,
    preferred_domains VARCHAR(255),
    salary_expectation NUMERIC(12,2),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_users_experience ON users(years_experience);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES roles(id),
    PRIMARY KEY(user_id, role_id)
);

CREATE TABLE IF NOT EXISTS user_location_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    preferred_cities VARCHAR(255) NOT NULL,
    work_type VARCHAR(20) NOT NULL,
    willing_to_relocate BOOLEAN NOT NULL,
    max_commute_radius_km INT NOT NULL,
    preferred_states VARCHAR(255) NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_location_state_city ON user_location_preferences(preferred_states, preferred_cities);

CREATE TABLE IF NOT EXISTS resumes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    file_name VARCHAR(200) NOT NULL,
    content_type VARCHAR(30) NOT NULL,
    file_size_bytes BIGINT NOT NULL,
    storage_path VARCHAR(255) NOT NULL,
    ai_parsed BOOLEAN NOT NULL DEFAULT FALSE,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS jobs (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    company VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    required_skills VARCHAR(255),
    min_experience INT,
    salary_min NUMERIC(12,2),
    salary_max NUMERIC(12,2),
    work_type VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS job_locations (
    id BIGSERIAL PRIMARY KEY,
    job_id BIGINT NOT NULL REFERENCES jobs(id),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_job_location_state_city ON job_locations(state, city);

CREATE TABLE IF NOT EXISTS applications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    job_id BIGINT NOT NULL REFERENCES jobs(id),
    status VARCHAR(30) NOT NULL,
    applied_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    match_score DOUBLE PRECISION NOT NULL,
    location_score DOUBLE PRECISION NOT NULL,
    UNIQUE(user_id, job_id)
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    subject VARCHAR(150) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    sent BOOLEAN NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
