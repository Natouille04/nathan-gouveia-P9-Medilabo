INSERT INTO patient (first_name, last_name, birth_date, genre, address, telephone)

SELECT * FROM (
    SELECT 'Test' AS first_name, 'TestNone' AS last_name, DATE '1966-12-31' AS birth_date, 'F' AS genre, '1 Brookside St' AS address, '100-222-3333' AS telephone UNION ALL
    SELECT 'Test', 'TestBorderline', DATE '1945-06-24', 'M', '2 High St', '200-333-4444' UNION ALL
    SELECT 'Test', 'TestInDanger', DATE '2004-06-18', 'M', '3 Club Road', '300-444-5555' UNION ALL
    SELECT 'Test', 'TestEarlyOnset', DATE '2002-06-28', 'F', '4 Valley Dr', '400-555-6666'
) AS seed

WHERE NOT EXISTS (SELECT 1 FROM patient);