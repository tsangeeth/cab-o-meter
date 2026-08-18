-- Demo operational data so the React screens have cabs, drivers, addresses, and requests.

INSERT INTO address (addr_line1, addr_line2, locality, city, state, landmark1, landmark2, employee_id)
SELECT 'No.10, 12th Avenue', 'Madagascar Street', 'Kingston Cross', 'Chennai', 'Tamil Nadu', 'near Atlantis', NULL, id
FROM employee WHERE emp_id = 'PC0001';

INSERT INTO address (addr_line1, addr_line2, locality, city, state, landmark1, landmark2, employee_id)
SELECT '14 Lake View Road', 'Block C', 'T. Nagar', 'Chennai', 'Tamil Nadu', 'opp. pond', NULL, id
FROM employee WHERE emp_id = 'PC0011';

INSERT INTO address (addr_line1, addr_line2, locality, city, state, landmark1, landmark2, employee_id)
SELECT '88 Marina Walk', NULL, 'Besant Nagar', 'Chennai', 'Tamil Nadu', 'near lighthouse', NULL, id
FROM employee WHERE emp_id = 'PC0013';

INSERT INTO address (addr_line1, addr_line2, locality, city, state, landmark1, landmark2, employee_id)
SELECT '21 Palm Grove', 'Flat 4B', 'Adyar', 'Chennai', 'Tamil Nadu', 'behind bakery', 'next to park', id
FROM employee WHERE emp_id = 'PC0014';

INSERT INTO address (addr_line1, addr_line2, locality, city, state, landmark1, landmark2, employee_id)
SELECT '5 Cross Cut Road', NULL, 'Saibaba Colony', 'Coimbatore', 'Tamil Nadu', 'near temple', NULL, id
FROM employee WHERE emp_id = 'PC0015';

INSERT INTO driver_info (driver_name, agency_name, license_no, license_expiry_date, contact_no, addr_line1, locality, city, state)
VALUES
    ('Arun Kumar', 'FastRide', 'TN09-2019-4411', DATE '2027-03-31', '9841001001', '12 Driver Lane', 'Guindy', 'Chennai', 'Tamil Nadu'),
    ('Meena Devi', 'FastRide', 'TN07-2020-1188', DATE '2028-01-15', '9841001002', '44 Depot Road', 'Ambattur', 'Chennai', 'Tamil Nadu'),
    ('Suresh Babu', 'CityCab', 'TN01-2018-9900', DATE '2026-12-20', '9841001003', '9 Stand Street', 'Tambaram', 'Chennai', 'Tamil Nadu');

INSERT INTO cab_info (registration_number, agency_name, model_name, make, capacity, make_year, rateperhr, fc_valid_until, default_driver_id)
SELECT 'TN09AB4411', 'FastRide', 'Innova', 'Toyota', 6, 2022, 450.0, DATE '2027-06-30', id FROM driver_info WHERE driver_name = 'Arun Kumar';

INSERT INTO cab_info (registration_number, agency_name, model_name, make, capacity, make_year, rateperhr, fc_valid_until, default_driver_id)
SELECT 'TN07CD1188', 'FastRide', 'Ertiga', 'Maruti', 6, 2023, 380.0, DATE '2027-09-30', id FROM driver_info WHERE driver_name = 'Meena Devi';

INSERT INTO cab_info (registration_number, agency_name, model_name, make, capacity, make_year, rateperhr, fc_valid_until, default_driver_id)
SELECT 'TN01EF9900', 'CityCab', 'Dzire', 'Maruti', 4, 2021, 280.0, DATE '2026-11-30', id FROM driver_info WHERE driver_name = 'Suresh Babu';

INSERT INTO cab_request (employee_id, created_by, start_date, login_time, end_date, logout_time, req_reason, isapproved, approver_id, appr_reason, reocurr_days)
SELECT e.id, e.emp_id, CURRENT_DATE + 1, TIME '09:00', CURRENT_DATE + 30, TIME '18:30',
       'Regular office commute', NULL, NULL, NULL, 'Mon,Tue,Wed,Thu,Fri'
FROM employee e WHERE e.emp_id = 'PC0014';

INSERT INTO cab_request (employee_id, created_by, start_date, login_time, end_date, logout_time, req_reason, isapproved, approver_id, appr_reason, reocurr_days)
SELECT e.id, e.emp_id, CURRENT_DATE + 2, TIME '08:30', CURRENT_DATE + 16, TIME '19:00',
       'Client workshop week', NULL, NULL, NULL, 'Mon,Tue,Wed,Thu,Fri'
FROM employee e WHERE e.emp_id = 'PC0024';

INSERT INTO cab_request (employee_id, created_by, start_date, login_time, end_date, logout_time, req_reason, isapproved, approver_id, appr_reason, reocurr_days)
SELECT e.id, e.emp_id, CURRENT_DATE - 10, TIME '09:15', CURRENT_DATE + 20, TIME '18:00',
       'Approved standing request', 'Y', 'PC0001', 'Team coverage approved', 'Mon,Wed,Fri'
FROM employee e WHERE e.emp_id = 'PC0014';

INSERT INTO cab_request (employee_id, created_by, start_date, login_time, end_date, logout_time, req_reason, isapproved, approver_id, appr_reason, reocurr_days)
SELECT e.id, e.emp_id, CURRENT_DATE - 5, TIME '10:00', CURRENT_DATE + 5, TIME '17:00',
       'Weekend support declined', 'N', 'PC0001', 'Use shuttle instead', 'Sat,Sun'
FROM employee e WHERE e.emp_id = 'PC0024';

INSERT INTO trip_plan (trip_date, trip_time, route_name, approx_trip_kms, approx_trip_cost, toll_charges, pickup_drop, cab_regd_number, driver_id)
SELECT CURRENT_DATE + 1, TIME '08:45', 'Adyar - OMR Office', 18, 720.0, 40.0, 'P', 'TN09AB4411', d.id
FROM driver_info d WHERE d.driver_name = 'Arun Kumar';

INSERT INTO trip_passengers (trip_plan_id, employee_id, employee_name, location, address, distance_in_kms, trip_sequence,
                             approved_manager_id, approved_manager_name, cost_centre, split_cost, team_name)
SELECT tp.id, 'PC0014', 'Mannix Buckley', 'Adyar', '21 Palm Grove, Adyar', 8, 1,
       'PC0001', 'Rahim Charles', '12345', 360.0, 'TEAM-A'
FROM trip_plan tp WHERE tp.route_name = 'Adyar - OMR Office';
