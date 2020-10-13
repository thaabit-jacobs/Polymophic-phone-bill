create table if not exists beneficiaries(
id SERIAL PRIMARY KEY NOT NULL,
name TEXT NOT NULL,
number TEXT NOT NULL,
sms_total DECIMAL(6, 2) NOT NULL,
data_total DECIMAL(6, 2) NOT NULL,
phone_total DECIMAL(6, 2) NOT NULL,
total_bill DECIMAL(6, 2) NOT NULL
)