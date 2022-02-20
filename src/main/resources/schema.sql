DROP TABLE airplane IF EXISTS;
DROP TABLE airplane_cost_estimates IF EXISTS;
DROP TABLE car IF EXISTS;
DROP TABLE car_cost_estimates IF EXISTS;
DROP TABLE cost_estimates IF EXISTS;

CREATE TABLE car
(
    id IDENTITY,
    brand VARCHAR2(150),
    model VARCHAR2(200),
    year_of_issue INT,
    power DOUBLE,
    PRIMARY KEY (id)
);

CREATE TABLE car_cost_estimates
(
    car_id BIGINT NOT NULL,
    cost_estimates_id BIGINT NOT NULL
);

CREATE TABLE airplane
(
    id IDENTITY,
    brand VARCHAR2(150),
    model VARCHAR2(200),
    year_of_issue INT,
    fuel_capacity INT,
    manufacturer VARCHAR2(200),
    seats INT, PRIMARY KEY (id)
);

CREATE TABLE airplane_cost_estimates
(
    airplane_id BIGINT NOT NULL,
    cost_estimates_id BIGINT NOT NULL
);

CREATE TABLE cost_estimates
(
    id IDENTITY,
    estimates_value DEC(20),
    estimates_date DATE,
    PRIMARY KEY (id)
);

ALTER TABLE airplane_cost_estimates
    ADD CONSTRAINT UK_airplane_cost_estimates
        UNIQUE (cost_estimates_id);

ALTER TABLE airplane_cost_estimates
    ADD CONSTRAINT FK1_airplane_cost_estimates
        FOREIGN KEY (cost_estimates_id)
            REFERENCES cost_estimates;

ALTER TABLE airplane_cost_estimates
    ADD CONSTRAINT FK2_airplane_cost_estimates
        FOREIGN KEY (airplane_id)
            REFERENCES airplane;

ALTER TABLE car_cost_estimates
    ADD CONSTRAINT UK_car_cost_estimates
        UNIQUE (cost_estimates_id);

ALTER TABLE car_cost_estimates
    ADD CONSTRAINT FK1_car_cost_estimates
        FOREIGN KEY (cost_estimates_id)
            REFERENCES cost_estimates;

ALTER TABLE car_cost_estimates
    ADD CONSTRAINT FK2_car_cost_estimates
        FOREIGN KEY (car_id) REFERENCES car;