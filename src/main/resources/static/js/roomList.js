var roomTable;
var roomTypeData = "";
var roomTypeString = "singleRoom";


$(document).ready(function() {
        
    initRoomTable();
    getRoomList();

    $("#roomTable tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
          $(this).removeClass('selected');
          emptyRoomModals();
        }
        else {
            roomTable.$('tr.selected').removeClass('selected');
          emptyRoomModals();
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
            roomTable.$('tr.selected').removeClass('selected');
        }
      }

        
    // View guest details: get ID from selected row
     $("#viewRoomButton").click(function() {
        // Get guest info by ID
        getRoom(roomTable.row($('.selected')).data());
        // console.log("Viewing guest: " + row.name);
      });

          // View guest details: get ID from selected row
    $(".editRoomButton").click(function() {
        // Get guest info by ID
        editRoomModal(roomTable.row($('.selected')).data());
        //console.log("Editing guest: " + row.name);
    });

    $("#saveRoom").click(function() {
        updateRoom();
    });

        //Open 'delete confirmation' modal
        $("#deleteRoomButton").click(function() {
            // Check if room is selected
            if (roomTable.row($('.selected')).data() == undefined) {
                $("#noRoomSelectedModal").modal("show");
            } else {
                // If room is selected, open modal to confirm deletion
                $("#deleteRoomModal").modal("show");
            }
        });
    
        // Confirm deletion of room that is currently selected
         $("#deleteRoomConfirmButton").click(function() {
            deleteRoom(roomTable.row($('.selected')).data());
          });

        // When a modal is closed, the fields inside should be emptied
        $(".room-close-button").click(function() {
            emptyRoomModals();
        });

      
});



function initRoomTable() {
        columns = [
            { "title":  "Room ID",
            "data": "id",
            "visible": false,
            "searchable": true },
            { "title":  "Room Name",
            "data": "roomNumber" },
            { "title":  "Available?",
            "data": "available",
            "render": function(data) {
                if (data == true) {
                return "Yes"
                } else {
                return "No"}
            }},
            {"title": "Price",
            "data": "price",
            "render": function(data) {
            return "¥"+ data}},
            { "title": "Type",
            "data": "roomType",
            "render": function(data) {
            if (data == "singleRoom") {
                return "Single"
            } else if (data == "doubleRoom") {
                return "Double"
            } else if (data == "familyRoom") {
                return "Family"
            } else if (data == "penthouse") {
                return "Penthouse"
            } else {
                return ""
            }

            }}
        ];
    
        // Define new table with above columns
        roomTable = $("#roomTable").DataTable( {
            "order": [[ 0, "asc" ]],
            "columns": columns
        } );
    }



function getRoomList() {
    let api = "/api/rooms";
    $.ajax({
        url: api,
        type: "get",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data) {
                //rooms = data;
                roomTable.clear();
                roomTable.rows.add(data);
                roomTable.columns.adjust().draw();
            }
        },

        error: function (error) {
            console.log(error);
        }
    });
}


function getRoom(room){
    if (room == undefined) {
        $("#noRoomSelectedModal").modal("show");
    } else {
        $("#viewRoomModal").modal("show");
    let api = "/api/rooms/" + room.id;
    $.ajax({
        url: api,
        type: "get",
        dataType: "json",
        contentType: "application/json",
        success: function(data){
            if(data.disabledRoom == false){
                data.disabledRoom = "False";
            }
            if(data.available == false){
                data.available = "False";
            }

                showRoom(data);
        },

        error: function (error) {
            console.log(error);
        }
    });
}
};



// Enter guest information in the 'view guest' modal
function showRoom(result){
    // Add guest information to View modal fields
    $("#viewRoomId").html(result.id);
    $("#viewRoomName").html(result.roomNumber);
    $("#viewRoomPrice").html(result.price);
    $("#viewRoomAvailability").html(result.available);
    $("#viewSingleBeds").html(result.numberOfSingleBeds);
    $("#viewDoubleBeds").html(result.numberOfDoubleBeds);
    $("#viewBabyBeds").html(result.numberOfBabyBeds);
    $("#viewDisabledRoom").html(result.disabledRoom);
   // $("#viewFacilities").html(result.facilities);
    $("#viewNoAdults").html(result.noOfAdults);
    $("#viewNoChildren").html(result.noOfChildren);
   // $("#viewroomType").html(result.roomType);
   // $("#viewStatus").html(result.roomStatus);
};


// Empty modals after closing
function emptyRoomModals() {
    // Remove room information from View modal fields
    $("#viewRoomId").empty();
    $("#viewRoomName").empty();
    $("#viewRoomPrice").empty();
    $("#viewRoomAvailability").empty();
    $("#viewSingleBeds").empty();
    $("#viewDoubleBeds").empty();
    $("#viewBabyBeds").empty();
    $("#viewDisabledRoom").empty();
 //   $("#viewFacilities").empty();
    $("#viewNoAdults").empty();
    $("#viewNoChildren").empty();
    //$("#viewroomType").empty();
  //  $("#viewStatus").empty();


  $("#editRoomId").val("");
  $("#editRoomName").val("");
  $("#editRoomPrice").val("");
  document.getElementById('editRoomAvailability').checked = false;
  $("#editSingleBeds").val("");
  $("#editDoubleBeds").val("");
  $("#editBabyBeds").val("");
  document.getElementById('editDisabledRoom').checked = false;
//   $("#viewFacilities").val("");
  $("#editNoAdults").val("");
  $("#editNoChildren").val("");
  //$("#viewroomType").val("");
//  $("#viewStatus").val("");

   roomTypeString = "";
};

function editRoomModal(room) {
    // Check if guest is selected
    if (room == undefined) {
        $("#noRoomSelectedModal").modal("show");
    } else {

        if(room.available){
            document.getElementById('editRoomAvailability').checked = true;
        }
        if(room.disabledRoom){
            document.getElementById('editDisabledRoom').checked = true;
        }

        $("#editRoomModal").modal("show");
        // Add guest information to View modal fields
        $("#editRoomId").val(room.id);
        $("#editRoomName").val(room.roomNumber);
        $("#editRoomPrice").val(room.price);
        $("#editSingleBeds").val(room.numberOfSingleBeds);
        $("#editDoubleBeds").val(room.numberOfDoubleBeds);
        $("#editBabyBeds").val(room.numberOfBabyBeds);
      //  $("#editFacilities").val(room.facilities);
        $("#editNoAdults").val(room.noOfAdults);
        $("#editNoChildren").val(room.noOfChildren);
        //$("#editroomType").val(room.roomType);
      //  $("#editStatus").val(room.roomStatus);\
        roomTypeString = room.roomType;
        console.log(roomTypeString);
    }


}



function updateRoom(){
if (roomTypeString == "") {
    roomTypeString = "singleRoom";
}

    var room = {
        id : +$("#editRoomId").val(),
        roomNumber : $('#editRoomName').val(),
        price : +$('#editRoomPrice').val(),
        numberOfSingleBeds : +$('#editSingleBeds').val(),
        numberOfDoubleBeds : +$('#editDoubleBeds').val(),
        numberOfBabyBeds : +$('#editBabyBeds').val(),
        available : $('#editRoomAvailability').prop('checked'),
        disabledRoom : $('#editDisabledRoom').prop('checked'),
        noOfAdults :  +$("#editNoAdults").val(),
        noOfChildren : +$("#editNoChildren").val(),
        roomType : roomTypeString
    };

    
    var jsonObject =JSON.stringify(room);
    
    let api = "/api/rooms/";
    $.ajax({
        url: api,
        type: "post",
        data: jsonObject,
        contentType: "application/json",
        success: function(data){
            getRoomList();
            emptyRoomModals()
            $("#editRoomModal").modal("hide");
        },
    
        error: function (error) {
            console.log(error);
            if ((error.responseText).includes("with that name")) {
                alert(error.responseText);
            }
        }
    });

    
}


function deleteRoom(data){
    $("#deleteRoomModal").modal("show");
    let api = "/api/rooms/" + data.id;
    $.ajax({
        url: api,
        type: "delete",
        contentType: "application/json",
        success: function(data){
            getRoomList();
        },
    
        error: function (error) {
            console.log(error);
        }
    });
}