CREATE TABLE drivers(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    license BOOLEAN NOT NULL
);

CREATE TABLE cars(
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE arends(
    id SERIAL PRIMARY KEY,
    drivers_id INTEGER REFERENCES drivers(id),
    cars_id INTEGER REFERENCES cars(id)
);