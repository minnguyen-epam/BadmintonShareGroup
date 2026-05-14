-- ============================================================
-- UserAccount
-- ============================================================
CREATE TABLE user_account (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(100) NOT NULL,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    email        VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    password     VARCHAR(255) NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- ============================================================
-- GroupMember
-- ============================================================
CREATE TABLE group_member (
    id             UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    group_name     VARCHAR(100) NOT NULL,
    type_of_group  VARCHAR(50)  NOT NULL,
    group_category VARCHAR(50)  NOT NULL,
    owner_id       UUID         NOT NULL REFERENCES user_account(id),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX idx_group_member_owner_id ON group_member(owner_id);

-- ============================================================
-- Permission (role definitions)
-- ============================================================
CREATE TABLE permission (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    role        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(255)
);

INSERT INTO permission (role, description) VALUES
    ('GLOBAL_ADMIN', 'Full system-wide administration access'),
    ('GROUP_ADMIN',  'Administration access within an assigned group'),
    ('USER',         'Standard authenticated user access');
