let room;
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const id = urlParams.get('id');

function getRoom(){
    let api = "/api/rooms/" + id;
    $.ajax({
        url: api,
        type: "get",
        dataType: "json",
        contentType: "application/json",
        success: function(data){
                room = data;
                fillFields();
        },

        error: function (error) {
            console.log(error);
        }
    });
}

function fillFields(){
    document.getElementById('name').value= room.roomNumber;
    document.getElementById('single').value= room.numberOfSingleBeds;
    document.getElementById('double').value= room.numberOfDoubleBeds;
    document.getElementById('baby').value= room.numberOfBabyBeds;
    if(room.available){
        document.getElementById('available').checked = true;
    }
    if(room.disabled){
        document.getElementById('disabled').checked = true;
    }

}

function updateRoom(){
    var room = {
        roomNumber : $('#name').val(),
        numberOfSingleBeds : $('#single').val(),
        numberOfDoubleBeds : $('#double').val(),
        numberOfBabyBeds : $('#baby').val(),
        available : $('#available').val(),
        disabledRoom : $('#disabled').val(),
    };
    if(room.available == "on"){
        room.available = true
    }
    if(room.available == "off"){
        room.available = false
    }
    if(room.disabledRoom == "on"){
        room.disabledRoom = true
    }
    if(room.disabledRoom == "off"){
        room.disabledRoom = false
    }
    
    var jsonObject =JSON.stringify(room);
    
    let api = "/api/rooms/" + id;
    $.ajax({
        url: api,
        type: "put",
        data: jsonObject,
        contentType: "application/json",
        success: function(data){
            alert("Room is successfully updated.")
        },
    
        error: function (error) {
            console.log(error);
        }
    });
}

function deleteRoom(){
    let api = "/api/rooms/" + id;
    $.ajax({
        url: api,
        type: "delete",
        contentType: "application/json",
        success: function(data){
            window.location.replace("/Admin/roomList.html");
        },
    
        error: function (error) {
            console.log(error);
        }
    });
}