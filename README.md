# Molveno-Lake-Resort

#### Available Pages:

* http://localhost:8080/ _General homepage_
* http://localhost:8080/public/rooms/room-overview _Overview of rooms (shows no of available rooms for selected dates)_
* http://localhost:8080/public/rooms/book-room _Booking page (after selecting rooms)_
* http://localhost:8080/admin _Admin portal to add/remove/edit guests, rooms and bookings_
* http://localhost:8080/h2-console/ _h2 database console_  
Driver class: org.h2.Driver  
JDBC URL: jdbc:h2:mem:molveno  
Username: molveno

Mustache is used for front-end templates (header/footer), not the base .html files.
To run the application, make sure all maven projects are imported through the pom.xml file.
