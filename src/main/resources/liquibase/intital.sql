-- ===========================
-- USERS
-- ===========================
CREATE TABLE users (
       id BIGSERIAL PRIMARY KEY,
       username VARCHAR(100) NOT NULL UNIQUE,
       email VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       created BIGINT,
       created_by VARCHAR(100),
       updated BIGINT,
       updated_by VARCHAR(100)
);

-- ===========================
-- CATEGORY
-- ===========================
CREATE TABLE category (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      type INT,
      created BIGINT,
      created_by VARCHAR(100),
      updated BIGINT,
      updated_by VARCHAR(100)
);

-- ===========================
-- MONTH BUDGET
-- ===========================
CREATE TABLE month_budget (
      id BIGSERIAL PRIMARY KEY,
      month_year VARCHAR(20) NOT NULL,
      extra_increase BIGINT,
      increase_reason VARCHAR(255),
      total BIGINT,
      remain BIGINT,
      user_id BIGINT , -- REFERENCES users(id)
      created BIGINT,
      created_by VARCHAR(100),
      updated BIGINT,
      updated_by VARCHAR(100)
);

-- ===========================
-- MONEY TRANSACTION
-- ===========================
CREATE TABLE money_transaction (
       id BIGSERIAL  NOT NULL,
       amount BIGINT NOT NULL,
       name VARCHAR(255)  NOT NULL,
       type INT,
       dd_mm_yyyy VARCHAR(20),
       month_id BIGINT , --REFERENCES month_budget(id)
       user_id BIGINT NOT NULL,
       category_id BIGINT , --REFERENCES category(id)
       created BIGINT,
       created_by VARCHAR(100),
       updated BIGINT,
       updated_by VARCHAR(100)
) PARTITION BY HASH (user_id);

CREATE TABLE money_transaction_part0 PARTITION OF money_transaction
    FOR VALUES WITH (MODULUS 4, REMAINDER 0);

CREATE TABLE money_transaction_part1 PARTITION OF money_transaction
    FOR VALUES WITH (MODULUS 4, REMAINDER 1);

CREATE TABLE money_transaction_part2 PARTITION OF money_transaction
    FOR VALUES WITH (MODULUS 4, REMAINDER 2);

CREATE TABLE money_transaction_part3 PARTITION OF money_transaction
    FOR VALUES WITH (MODULUS 4, REMAINDER 3);


CREATE INDEX idx_mt_part0_user_id ON money_transaction_part0 (user_id);
CREATE INDEX idx_mt_part1_user_id ON money_transaction_part1 (user_id);
CREATE INDEX idx_mt_part2_user_id ON money_transaction_part2 (user_id);
CREATE INDEX idx_mt_part3_user_id ON money_transaction_part3 (user_id);

-- ===========================
-- PAYABLE / RECEIVABLE
-- ===========================
CREATE TABLE payable_receivable (
        id BIGSERIAL PRIMARY KEY,
        start_date BIGINT,
        end_date BIGINT,
        payed BOOLEAN DEFAULT false,
        source VARCHAR(255),
        type INT,
        user_id BIGINT , --REFERENCES users(id)
        created BIGINT,
        created_by VARCHAR(100),
        updated BIGINT,
        updated_by VARCHAR(100)
);

-- ===========================
-- MONTH SPEND
-- ===========================
CREATE TABLE month_spend (
         id BIGSERIAL PRIMARY KEY,
         month_year VARCHAR(20) NOT NULL,
         budget_id BIGINT , --REFERENCES month_budget(id)
         cash BIGINT,
         digital BIGINT,
         last_month_spend BIGINT,
         user_id BIGINT , --REFERENCES users(id)
         created BIGINT,
         created_by VARCHAR(100),
         updated BIGINT,
         updated_by VARCHAR(100)
);

-- ===========================
-- MONTH BALANCE
-- ===========================
CREATE TABLE month_balance (
       id BIGSERIAL PRIMARY KEY,
       month_year VARCHAR(20) NOT NULL,
       cash_current BIGINT,
       cash_start BIGINT,
       digital_current BIGINT,
       digital_start BIGINT,
       user_id BIGINT , --REFERENCES users(id)
       created BIGINT,
       created_by VARCHAR(100),
       updated BIGINT,
       updated_by VARCHAR(100)
);