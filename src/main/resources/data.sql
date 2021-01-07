INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,1,1,0,0,1,849,'101','singleRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,1,1,0,0,1,849,'102','singleRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,1,1,0,0,1,849,'103','singleRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,2,1,0,1,0,1199,'104','doubleRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,2,1,0,1,0,1199,'105','doubleRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,2,1,0,1,0,1199,'106','doubleRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,4,1,1,1,2,1649,'107','familyRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,4,1,1,1,2,1649,'108','familyRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,4,1,1,1,2,1649,'109','familyRoom');
INSERT INTO room (available, disabled_room, no_of_adults, no_of_children, number_of_baby_beds, number_of_double_beds, number_of_single_beds, price, room_number, room_type)
VALUES (1,0,8,2,1,3,2,2399,'110','penthouse');

INSERT INTO guest (address, birth_date, city, mail, name, passport_nr, phone)
VALUES ('Main Street 99', '1992-01-30', 'New York', 'janedoe@mail.com', 'Jane Doe', 'AB9381B39', '31612345678');
INSERT INTO guest (address, birth_date, city, mail, name, passport_nr, phone)
VALUES ('Dorpsstraat 83', '1990-02-10', 'Dordrecht', 'jjanssen@email.com', 'Jan Janssen', 'KH9274027', '31 72 5712345');
INSERT INTO guest (address, birth_date, city, mail, name, passport_nr, phone)
VALUES ('Ellsworth Summit', '1959-01-09', 'Howemouth', 'nicwiley@email.com', 'Nicholas Wiley', 'IT392K382', '042 2934813');
INSERT INTO guest (address, birth_date, city, mail, name, passport_nr, phone)
VALUES ('Victor Plains 391', '1978-05-19', 'Gwenborough', 'ervinh@email.com', 'Ervin Howell', 'ABE382915', '010 9320 592');
INSERT INTO guest (address, birth_date, city, mail, name, passport_nr, phone)
VALUES ('Hager Mall', '1993-11-29', 'Corkshire', 'patleb@email.com', 'Patricia Lebsack', 'IW938G913', '063 298 492143');

INSERT INTO booking (check_in_date, check_out_date, status, total_guests, guestid, invoice_id)
VALUES ('2020-02-20', '2020-02-23', 0, 1, 1, null);
INSERT INTO booking (check_in_date, check_out_date, status, total_guests, guestid, invoice_id)
VALUES ('2020-02-20', '2020-02-24', 0, 2, 2, null);
INSERT INTO booking (check_in_date, check_out_date, status, total_guests, guestid, invoice_id)
VALUES ('2020-02-26', '2020-02-28', 0, 4, 3, null);
INSERT INTO booking (check_in_date, check_out_date, status, total_guests, guestid, invoice_id)
VALUES ('2020-02-23', '2020-02-26', 0, 7, 4, null);
INSERT INTO booking (check_in_date, check_out_date, status, total_guests, guestid, invoice_id)
VALUES ('2020-02-23', '2020-02-25', 0, 6, 5, null);

INSERT INTO booked_rooms (booking_id, room_id)
VALUES (1, 1);
INSERT INTO booked_rooms (booking_id, room_id)
VALUES (2, 4);
INSERT INTO booked_rooms (booking_id, room_id)
VALUES (3, 7);
INSERT INTO booked_rooms (booking_id, room_id)
VALUES (4, 9);
INSERT INTO booked_rooms (booking_id, room_id)
VALUES (5, 5);
INSERT INTO booked_rooms (booking_id, room_id)
VALUES (5, 7);

--SELECT molveno.booked_rooms.booking_id, molveno.booked_rooms.room_id, molveno.booking.check_in_date, molveno.booking.check_out_date, molveno.room.room_type
--FROM molveno.room
--JOIN molveno.booked_rooms ON molveno.room.id = molveno.booked_rooms.room_id
--JOIN molveno.booking ON molveno.booked_rooms.booking_id = molveno.booking.id;