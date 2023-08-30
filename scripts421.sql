ALTER TABLE student ADD CONSTRAINT student_age_constr CHECK (age > 16);

ALTER TABLE student ADD CONSTRAINT unique_student_name UNIQUE (name);
ALTER TABLE student ADD CONSTRAINT non_null_student_name CHECK (name IS NOT NULL);

ALTER TABLE faculty ADD CONSTRAINT unique_name_color UNIQUE (name, color);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;