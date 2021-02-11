const roomBookingApi = "http://localhost:8080/api/bookings";
const roomListApi = "http://localhost:8080/api/rooms";

var today = new Date();
var ageCheckDate = new Date(today.setMonth(today.getMonth()-216));
//$.format.date(today,"dd-MM-yyyy");

var formValid = false;

var singleRoomList = [];
var doubleRoomList = [];
var familyRoomList = [];
var penthouseRoomList = [];
var sessionSelectedRoomArray;
var parsedCheckInDate;
var parsedCheckOutDate;
//sessionStorage.setItem("selectedRooms",JSON.stringify(sessionSelectedRoomArray));

$(document).ready(function() {
    initDatePickers();

    if (window.location.href.indexOf("room-overview") > -1) {
            sessionStorage.clear();
        };

    if (window.location.href.indexOf("book-room") > -1) {
            fillBookingForm();
        };

     $("#bookingConfirmedModal").on('hide.bs.modal', function() {
        sessionStorage.clear();
        emptyFields();
     });
});

// Button functions
$("#findRooms").click(function() {
    if (($("#formCheckInDate").val() != "") && ($("#formCheckOutDate").val() != "")) {
        getAllRoomLists();
        setAvailableRoomListSize();
    } else {
        $("#warningModalBody").html("Error")
        $("#warningModal").modal("show");
        setTimeout(function(){
            $("#warningModal").modal("hide");
        }, 60000);
    }
});

$(".add-room").click(function() {
    addRoomSelection($(this).attr('id'));
});

$("#userGender").on("click",function(){
    $('#userGender').val('Anders');
});

$("#newBookingButton").click(function() {
    if ((sessionStorage.getItem("selectedRooms") == "") || (sessionStorage.getItem("selectedRooms") == undefined || (sessionStorage.getItem("selectedRooms").length == 0) )) {
        $("#warningModalBody").html("You need to select at least one room.")
        $("#warningModal").modal("show");
        setTimeout(function(){
            $("#warningModal").modal("hide");
        }, 60000);
    } else {
        setSessionStorage();
        window.location = "http://localhost:8080/public/rooms/book-room";
    }
});

$("#submitBooking").click(function() {
    checkFormValid();
    formValid = true;
    if (formValid == true) {
        postBooking();
    } else {
        $("#warningModalBody").html("Make sure all guest information is filled in and correct.")
        $("#warningModal").modal("show");
        setTimeout(function(){
            $("#warningModal").modal("hide");
        }, 60000);
    }
});

$("#clearStorage").click(function() {
    sessionStorage.clear();
    console.log("Session storage cleared");
})




function initDatePickers() {
    // Datepicker for check-in
    $("#formCheckInDate").datepicker({
        dateFormat: "dd-mm-yy",
        changeMonth: true,
        changeYear: true,
        maxDate: "+6M",
        minDate: 0,
        constrainInput: false,
        firstDay: 1,
        autoSize: true,
        beforeShowDay: function(date) {
            if(date.getDay() == 6 || date.getDay() == 0) {
                return [true, "Highlighted", ''];
            } else {
                return [true, '', ''];
            }
        }
    });

    // Datepicker for check-out-in
        $("#formCheckOutDate").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            maxDate: "+6M",
            minDate: 0,
            constrainInput: false,
            firstDay: 1,
            autoSize: true,
            beforeShowDay: function(date) {
                if(date.getDay() == 6 || date.getDay() == 0) {
                    return [true, "Highlighted", ''];
                } else {
                    return [true, '', ''];
                }
            }
        });

        $("#formCheckInDate").change(function() {
             $("#formCheckOutDate").datepicker('option', {
                minDate: $("#formCheckInDate").val()
                });
        });

    // Datepicker for date of birth
    $("#bookingGuestBirthDate").datepicker({
        dateFormat: "dd-mm-yy",
        changeMonth: true,
        changeYear: true,
        maxDate: "-18Y",
        constrainInput: false,
        firstDay: 1,
        autoSize: true,
        beforeShowDay: function(date) {
            if(date.getDay() == 6 || date.getDay() == 0) {
                return [true, "Highlighted", ''];
            } else {
                return [true, '', ''];
            }
        }
    });
}

function getAllRoomLists() {
    parsedCheckInDate = $("#formCheckInDate").val().split('-').reverse().join('-'); //  dd-MM-yyyy ---> yyyy-MM-dd
    parsedCheckOutDate = $("#formCheckOutDate").val().split('-').reverse().join('-');


// Single rooms:
    $.ajax(
        {
            type: "get",
            url: roomListApi + "/available-rooms",
            data: {
            newCheckIn: parsedCheckInDate,
            newCheckOut: parsedCheckOutDate,
            roomType: "single"},
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log("singleRoomList: ");
                console.log(result);
                singleRoomList = result;
                $("#singleRoomListSize").html(singleRoomList.length);
            },
            error: function (error) {
                console.log(error);
            }
        }
    );

// Double rooms:
    $.ajax(
        {
            type: "get",
            url: roomListApi + "/available-rooms",
            data: {
            newCheckIn: parsedCheckInDate,
            newCheckOut: parsedCheckOutDate,
            roomType: "double"},
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log("doubleRoomList: ");
                console.log(result);
                doubleRoomList = result;
                $("#doubleRoomListSize").html(doubleRoomList.length);
            },
            error: function (error) {
                console.log(error);
            }
        }
    );

// Family rooms:
    $.ajax(
        {
            type: "get",
            url: roomListApi + "/available-rooms",
            data: {
            newCheckIn: parsedCheckInDate,
            newCheckOut: parsedCheckOutDate,
            roomType: "family"},
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log("familyRoomList: ");
                console.log(result);
                familyRoomList = result;
                $("#familyRoomListSize").html(familyRoomList.length);
            },
            error: function (error) {
                console.log(error);
            }
        }
    );

// Penthouse rooms:
    $.ajax(
        {
            type: "get",
            url: roomListApi + "/available-rooms",
            data: {
            newCheckIn: parsedCheckInDate,
            newCheckOut: parsedCheckOutDate,
            roomType: "penthouse"},
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log("penthouseRoomList: ");
                console.log(result);
                penthouseRoomList = result;
                $("#penthouseRoomListSize").html(penthouseRoomList.length);
            },
            error: function (error) {
                console.log(error);
            }
        }
    );


}

function setAvailableRoomListSize() {
    $("#singleRoomListSize").html(singleRoomList.length);
    $("#doubleRoomListSize").html(doubleRoomList.length);
    $("#familyRoomListSize").html(familyRoomList.length);
    $("#penthouseRoomListSize").html(penthouseRoomList.length);
}


function addRoomSelection(buttonId) {
      var roomType = buttonId;
      console.log("Adding room of type \"" + roomType + "\"...");

//      Add room to session storage:

        var selectedRoom;
        if (roomType == "single") {
            if (singleRoomList.length > 0) {
                selectedRoom = singleRoomList[0];
                singleRoomList.splice(0,1);
            } else {
            console.log("No single rooms available!");
            $("#warningModalBody").html("No single rooms available for your selected dates.")
            $("#warningModal").modal("show");
            setTimeout(function(){
                $("#warningModal").modal("hide");
            }, 60000);
            return;
            }
        } else if (roomType == "double") {
            if (doubleRoomList.length > 0) {
                selectedRoom = doubleRoomList[0];
                doubleRoomList.splice(0,1);
            } else {
            console.log("No double rooms available!");
            $("#warningModalBody").html("No double rooms available for your selected dates.")
            $("#warningModal").modal("show");
            setTimeout(function(){
                $("#warningModal").modal("hide");
            }, 60000);
            return;
            }
        } else if (roomType == "family") {
            if (familyRoomList.length > 0) {
                selectedRoom = familyRoomList[0];
                familyRoomList.splice(0,1);
            } else {
            console.log("No family rooms available!");
            $("#warningModalBody").html("No family rooms available for your selected dates.")
            $("#warningModal").modal("show");
            setTimeout(function(){
                $("#warningModal").modal("hide");
            }, 60000);
            return;
            }
        } else if (roomType == "penthouse") {
            if (penthouseRoomList.length > 0) {
                selectedRoom = penthouseRoomList[0];
                penthouseRoomList.splice(0,1);
            } else {
            console.log("No penthouse rooms available!");
            $("#warningModalBody").html("No penthouse rooms available for your selected dates.")
            $("#warningModal").modal("show");
            setTimeout(function(){
                $("#warningModal").modal("hide");
            }, 60000);
            return;
            }
        }
        setAvailableRoomListSize();

        if ((sessionStorage.getItem("selectedRooms") == "") || (sessionStorage.getItem("selectedRooms") == undefined)) {
            sessionStorage.setItem("selectedRooms", []);
        }

        if (sessionStorage.getItem("selectedRooms").length != 0) {
            sessionSelectedRoomArray = JSON.parse(sessionStorage.getItem("selectedRooms")); // get selectedArray from session storage
        } else {
            sessionSelectedRoomArray = [];
        }

        sessionSelectedRoomArray.push(selectedRoom); // add selected room to array
        if (sessionSelectedRoomArray[sessionSelectedRoomArray.length-1] != null) {
            sessionStorage.setItem("selectedRooms",JSON.stringify(sessionSelectedRoomArray)); // set session storage array with new selected room
             console.log(sessionStorage.getItem("selectedRooms"));

             var roomType = stringRoomType(selectedRoom.roomType);

             $("#selectedRoomList").append('<li id="id'+ selectedRoom.id + '"><b>Room ' + selectedRoom.roomNumber + '</b> - Type: ' + roomType + '</li>');
        }


   }

function setSessionStorage() {
    sessionStorage.setItem("checkInDate",$("#formCheckInDate").val());
    sessionStorage.setItem("checkOutDate",$("#formCheckOutDate").val());
    sessionStorage.setItem("noOfAdults",$("#noOfAdults").val());
    sessionStorage.setItem("noOfChildren",$("#noOfChildren").val());
}

function fillBookingForm() {
    $("#bookingInfoCheckInDate").html(sessionStorage.getItem("checkInDate"));
    $("#bookingInfoCheckOutDate").html(sessionStorage.getItem("checkOutDate"));
    $("#bookingInfoAdults").html(sessionStorage.getItem("noOfAdults"));
    $("#bookingInfoChildren").html(sessionStorage.getItem("noOfChildren"));

    sessionSelectedRoomArray = JSON.parse(sessionStorage.getItem("selectedRooms"));
    console.log(sessionSelectedRoomArray);

    var pricePerDay = 0;

    $.each(sessionSelectedRoomArray, function(index, value) {
        // sum up price
        pricePerDay += value.price;

        // find roomType string
        var roomType = stringRoomType(value.roomType);

        // append HTML info
        $(".booking-room-info").append('<div id="id' + value.id + '" class="my-3"><div class="row"><div class="col-4 text-right"><b>Room number:</b></div><div id="bookingRoomId" class="col-8 text-left">' + value.roomNumber + '</div></div><div class="row"><div class="col-4 text-right"><b>Room type:</b></div><div id="bookingInfoRoomType" class="col-8 text-left">' + roomType + '</div></div><div class="row"><div class="col-4 text-right"><b>Price / night:</b></div><div id="bookingInfoRoomPrice" class="col-8 text-left">¥' + value.price + '</div></div></div>');
    });

    parsedCheckInDate = sessionStorage.getItem("checkInDate").split('-').reverse().join('-'); //  dd-MM-yyyy ---> yyyy-MM-dd
    parsedCheckOutDate = sessionStorage.getItem("checkOutDate").split('-').reverse().join('-');

    var priceCheckInDate = new Date(parsedCheckInDate);
    var priceCheckOutDate = new Date(parsedCheckOutDate);
    var priceDifferenceInTime = priceCheckOutDate.getTime() - priceCheckInDate.getTime();
    var priceDifferenceInDays = priceDifferenceInTime / (1000 * 3600 * 24);
    var totalBookingPrice = priceDifferenceInDays * pricePerDay;
    $("#bookingTotalPrice").html(totalBookingPrice);

}

function postBooking() {
    var adults = +sessionStorage.getItem("noOfAdults");
    var children = +sessionStorage.getItem("noOfChildren");
    var sumTotalGuests = adults + children;
    var inputBirthDate = $("#bookingGuestBirthDate").val().split('-').reverse().join('-'); //  dd-MM-yyyy ---> yyyy-MM-dd


    let bookingObject = {
        totalGuests: sumTotalGuests,
        status: "booked",
        checkInDate: parsedCheckInDate,
        checkOutDate: parsedCheckOutDate,
        guest: {
//            id: $("#bookingGuestId").val(),
            name: $("#bookingGuestName").val(),
            birthDate: inputBirthDate,
            mail: $("#bookingGuestMail").val(),
            phone: $("#bookingGuestPhone").val(),
            passportNr: $("#bookingGuestPassportNr").val(),
            address: $("#bookingGuestAddress").val(),
            city: $("#bookingGuestCity").val()
        },
        bookedRooms: JSON.parse(sessionStorage.getItem("selectedRooms")),
        invoice: null
    }

    console.log("new bookingObject:");
    console.log(bookingObject);

     var jsonObject = JSON.stringify(bookingObject);

     $.ajax({
         url: roomBookingApi,
         headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
         type: "post",
         dataType: "json",
         data: jsonObject,
         contentType: "application/json",
         success: function(result) {
             console.log("Booking posted / edited: ");
             console.log(result);
             confirmBookingModal(result);
             formValid = false;

         },
         error: function (error) {
             console.log(error);
         }
     });

}

function confirmBookingModal(booking) {
    var confirmedBookingRooms = [];
    confirmedBookingRooms = booking.bookedRooms;
    var confirmedBookingRoomsList = "";


    $.each(confirmedBookingRooms, function(index, value) {
        var roomType = stringRoomType(value.roomType);
        $("#bookingConfirmedRoomList").append('<li><b>Room ' + value.roomNumber + '</b> - Type: ' + roomType + '</li>');
    });

    console.log(confirmedBookingRoomsList);

    // Add information to View modal fields
    $("#bookingConfirmedCheckInDate").html($.format.date(booking.checkInDate,"dd-MM-yyyy"));
    $("#bookingConfirmedCheckOutDate").html($.format.date(booking.checkOutDate,"dd-MM-yyyy"));
    $("#bookingConfirmedTotalGuests").html(booking.totalGuests);

    $("bookingConfirmedRoomList").html(confirmedBookingRoomsList);

    $("#bookingConfirmedGuestName").html(booking.guest.name);
    $("#bookingConfirmedGuestBirthDate").html($.format.date(booking.guest.birthDate,"dd-MM-yyyy"));
    $("#bookingConfirmedGuestMail").html(booking.guest.mail);
    $("#bookingConfirmedGuestPassportNr").html(booking.guest.passportNr);
    $("#bookingConfirmedGuestAddress").html(booking.guest.address);
    $("#bookingConfirmedGuestCity").html(booking.guest.city);

    $("#bookingConfirmedModal").modal("show");
}

function checkFormValid() {
    formValid = false;
     if ($("#bookingGuestName").val() == "") {
        return formValid = false;
     } else if ($("#bookingGuestBirthDate").val() == "") {
        return formValid = false;
     } else if (new Date($("#bookingGuestBirthDate").val().split('-').reverse().join('-')) >= ageCheckDate) {
        return formValid = false;
     } else if ($("#bookingGuestMail").val() == "") {
        return formValid = false;
     } else if ($("#bookingGuestPhone").val() == "") {
        return formValid = false;
     } else if ($("#bookingGuestPassportNr").val() == "") {
        return formValid = false;
     } else if ($("#bookingGuestAddress").val() == "") {
        return formValid = false;
     } else if ($("#bookingGuestCity").val() == "") {
        return formValid = false;
     } else {
        return formValid = true;
     }

}

function emptyFields() {
    $("#bookingConfirmedCheckInDate").html("");
    $("#bookingConfirmedCheckOutDate").html("");
    $("#bookingConfirmedTotalGuests").html("");
    $("bookingConfirmedRoomList").html("");
    $("#bookingConfirmedGuestName").html("");
    $("#bookingConfirmedGuestBirthDate").html("");
    $("#bookingConfirmedGuestMail").html("");
    $("#bookingConfirmedGuestPassportNr").html("");
    $("#bookingConfirmedGuestAddress").html("");
    $("#bookingConfirmedGuestCity").html("");
    $("#bookingInfoCheckInDate").html("");
    $("#bookingInfoCheckOutDate").html("");
    $("#bookingInfoAdults").html("");
    $("#bookingInfoChildren").html("");
    $(".booking-room-info").html("");
    $("#bookingGuestBirthDate").val()
    $("#bookingGuestId").val("");
    $("#bookingGuestName").val("");
    $("#bookingGuestBirthDate").val("");
    $("#bookingGuestMail").val("");
    $("#bookingGuestPhone").val("");
    $("#bookingGuestPassportNr").val("");
    $("#bookingGuestAddress").val("");
    $("#bookingGuestCity").val("");
}

function stringRoomType(roomRoomType) {
    var roomType;
    if (roomRoomType == "singleRoom") {
        return "Single room";
    } else if (roomRoomType == "doubleRoom") {
        return "Double room";
    } else if (roomRoomType == "familyRoom") {
        return "Family room";
    } else if (roomRoomType == "penthouse") {
        return "Penthouse room";
    }
}




