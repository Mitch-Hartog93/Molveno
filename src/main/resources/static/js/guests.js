const guestApi = "http://localhost:8080/api/guests";

var guestTable;
var bookingIdString = "";

let today = new Date();
console.log(today);
let ageCheckDate = new Date(today.setMonth(today.getMonth()-216));
console.log(ageCheckDate);


$(document).ready(function() {

    initGuestTable();
    getGuestData();


    $("#fetchGuestList").click(function() {
        getGuestData();
    });

    $("#clearGuestList").click(function() {
        clearGuestTable();
    });



    // Add 'selected' class on row (for selection of data/guest ID)
    $("#guestTable tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
          $(this).removeClass('selected');
          emptyGuestModals();
        }
        else {
          guestTable.$('tr.selected').removeClass('selected');
            emptyGuestModals();
            $(this).addClass('selected');
        }
      });

      // Remove row selection if escape is pressed
       document.onkeydown = function(evt) {
          evt = evt || window.event;
          var isEscape = false;
          if ("key" in evt) {
              isEscape = (evt.key === "Escape" || evt.key === "Esc");
          } else {
              isEscape = (evt.keyCode === 27);
          }
          if (isEscape) {
              guestTable.$('tr.selected').removeClass('selected');
              emptyGuestModals();
          }
        };

    // View guest details: get ID from selected row
     $("#viewGuestButton").click(function(event) {
        viewGuestModal(guestTable.row($('.selected')).data());
      });

      // Edit guest details: get ID from selected row
       $(".editGuestButton").click(function(event) {
          $("form").removeClass("was-validated");
           $("#guestValidationError").addClass("d-none");
          editGuestModal(guestTable.row($('.selected')).data());
        });

       // Open new guest form
    $("#newGuestButton").click(function(event) {
        emptyGuestModals();

        $("#editGuestModal").modal("show");

    })


    // Datepicker for date of birth
    $("#editGuestBirthDate").datepicker({
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

    //Open 'delete confirmation' modal
    $("#deleteGuestButton").click(function() {
        // Check if guest is selected
        if (guestTable.row($('.selected')).data() == undefined) {
            $("#noGuestSelectedModal").modal("show");
        } else {
            // If guest is selected, open modal to confirm deletion
            $("#deleteGuestModal").modal("show");
        }
    });

    // Confirm deletion of guest that is currently selected
     $("#deleteGuestConfirmButton").click(function() {
        deleteGuest(guestTable.row($('.selected')).data());
      });

     // When a modal is closed, the fields inside should be emptied
    $(".guest-close-button").click(function() {
        emptyGuestModals();
    });



});

// Validation of input fields
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                } else if (new Date($("#editGuestBirthDate").val().split('-').reverse().join('-')) >= ageCheckDate) {
                    event.preventDefault();
                    event.stopPropagation();

                    $("#guestValidationError").html("Guest must be 18 years or older.");
                    $("#guestValidationError").removeClass("d-none");
                    console.log("input date after age check");

                }
                else {
                    event.preventDefault();
                    saveGuest();
                    $("#guestValidationError").addClass("d-none");
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

function initGuestTable() {
// Create columns (with titles) for datatable: id, name, date of birth and city.
    columns = [
        { "title": "Guest ID",
        "data": "id",
        "visible": false,
        "searchable": true},
        { "title":  "Guest Name",
        "data": "name" },
        { "title":  "Date of Birth",
        "data": "birthDate",
        "render": function(data) {
         return $.format.date(data,"dd-MM-yyyy");
         }},
        { "title":  "City",
        "data": "city"},
    ];

    // Define new table with above columns
   guestTable = $("#guestTable").DataTable( {
        "order": [[ 0, "asc" ]],
        "columns": columns
    } );
}

// Clear all data from table
function clearGuestTable() {
    guestTable.clear();
    guestTable.columns.adjust().draw();
}

// Fetch guest data from API
function getGuestData() {
    $.get(
        {
            url: guestApi,
            dataType: "json",
            success: function (data) {
                if (data) {
                    guestTable.clear();
                    guestTable.rows.add(data);
                    guestTable.columns.adjust().draw();
                }
            }
        }
    );
}

// View guest details with ID from selected row
function viewGuestModal(guest) {
    // Ensure that guest ID is a number (not string)
    // let guestId = +id;
    if (guest == undefined) {
        $("#noGuestSelectedModal").modal("show");
    } else {
        $("#viewGuestModal").modal("show");
        $.ajax({
            url: guestApi + "/" + guest.id,
            type: "get",
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log("viewing guest:");
                console.log(result);
                // If guest was retrieved, add guest details to modal
                showGuestDetails(result);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

};

// Enter guest information in the 'view guest' modal
function showGuestDetails(result){
    getGuestBookings(result.id);

    // Add guest information to View modal fields
    $("#viewGuestId").html(result.id);
    $("#viewGuestName").html(result.name);
    $("#viewGuestBirthDate").html($.format.date(result.birthDate,"dd-MM-yyyy"));
    $("#viewGuestMail").html(result.mail);
    $("#viewGuestPhone").html(result.phone);
    $("#viewGuestPassportNr").html(result.passportNr);
    $("#viewGuestAddress").html(result.address);
    $("#viewGuestCity").html(result.city);
};

function getGuestBookings(id) {

        $.ajax({
            url: guestApi + "/" + id + "/bookings",
            type: "get",
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log("Guest booking array:");
                console.log(result);
                getBookingIdString(result);
            },
            error: function (error) {
                console.log(error);
            }
        });
}

function getBookingIdString(bookings) {
    // Get booking array:
    let bookingArray = bookings;

    let bookingList = "";
    // If no bookings exist:
    if (bookingArray.length == 0) {
        bookingList = "No bookings."
    }
    // If multiple bookings exist:
    else if (bookingArray.length >= 2) {
        $.each(bookingArray, function(index, value) {
            bookingList += value.id;
            if (!(index === bookingArray.length - 1)) {
                bookingList += ", ";
            }
        });
    }
    // If 1 booking exists:
    else if (bookingArray.length == 1) {
        bookingList = bookingArray[0].id;
    }

    bookingIdString = bookingList;

    $("#viewGuestBookings").html(bookingIdString);
}

function editGuestModal(guest) {
    // Check if guest is selected
    if (guest == undefined) {
        $("#noGuestSelectedModal").modal("show");
    } else {
        $("#editGuestModal").modal("show");
        // Fill in guest to modal fields
        $("#editGuestId").val(guest.id);
        $("#editGuestName").val(guest.name);
        $("#editGuestBirthDate").val($.format.date(guest.birthDate,"dd-MM-yyyy"));
        $("#editGuestMail").val(guest.mail);
        $("#editGuestPhone").val(guest.phone);
        $("#editGuestPassportNr").val(guest.passportNr);
        $("#editGuestAddress").val(guest.address);
        $("#editGuestCity").val(guest.city);
    }
}

function saveGuest() {
// toDo: age-check in guestController

    // Create string that can be read from JSON parser (yyyy-MM-dd)
    var inputBirthDate = $("#editGuestBirthDate").val().split('-').reverse().join('-'); //  dd-MM-yyyy ---> yyyy-MM-dd
    console.log("input birthdate for JSON: " + inputBirthDate);


    let guestObject = {
        id: +$("#editGuestId").val(),
        name: $("#editGuestName").val(),
        birthDate: inputBirthDate,
        mail: $("#editGuestMail").val(),
        phone: $("#editGuestPhone").val(),
        passportNr: $("#editGuestPassportNr").val(),
        address: $("#editGuestAddress").val(),
        city: $("#editGuestCity").val()
    };
    console.log("New guestObject: ");
    console.log(guestObject);

    var jsonObject = JSON.stringify(guestObject);
    console.log("new jsonObject: " + jsonObject);

    $.ajax({
        url: guestApi,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "post",
        dataType: "json",
        data: jsonObject,
        contentType: "application/json",
        success: function(result) {
            console.log("Guest posted / edited: ");
            console.log(result);
            $("#editGuestModal").modal("hide");
            emptyGuestModals();
            getGuestData();
        },
        error: function (error) {
        // todo: add error message for checking in backend
            console.log(error);
            getGuestData();
        }
    });
}

// Delete selected guest with ID from datatable & database
function deleteGuest(guest) {
    console.log("deleting guest: ");
    console.log(guest);

        $("#deleteGuestModal").modal("show");
        $.ajax({
            url: guestApi,
            type: "delete",
            dataType: "json",
            data: JSON.stringify(guest),
            contentType: "application/json",
            success: function () {
                getGuestData();
            },
            error: function (error) {
                console.log(error.responseText);
                $("#guestWarningModal .modal-body").html(error.responseText);
                $("#guestWarningModal").modal('show');
                getGuestData();
            }
        });
}

// Empty modals after closing
function emptyGuestModals() {
    // Remove guest information from View modal fields
    $("#viewGuestId").html("No guest selected.");
    $("#viewGuestName").empty();
    $("#viewGuestBirthDate").empty();
    $("#viewGuestMail").empty();
    $("#viewGuestPhone").empty();
    $("#viewGuestPassportNr").empty();
    $("#viewGuestAddress").empty();
    $("#viewGuestCity").empty();
    $("#viewGuestBookings").empty();
    $("#guestWarningModal .modal-body").empty();
    bookingIdString = "";



    // Remove guest information from Edit modal fields
    $("#editGuestId").val("");
    $("#editGuestName").val("");
    $("#editGuestBirthDate").val("");
    $("#editGuestMail").val("");
    $("#editGuestPhone").val("");
    $("#editGuestPassportNr").val("");
    $("#editGuestAddress").val("");
    $("#editGuestCity").val("");
    $("#guestValidationError").addClass("d-none");
    $("form").removeClass("was-validated");


};