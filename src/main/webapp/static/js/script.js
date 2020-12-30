
function showChangeAvatarForm(){
    if (document.getElementById("changeAvatarForm").style.display === "none") {
        document.getElementById("changeAvatarForm").style.display = "block";
    }else{
        document.getElementById("changeAvatarForm").style.display = "none";
    }
}



function openForm() {
    document.getElementById("myForm").style.display = "contents";
}

function closeForm() {
    document.getElementById("myForm").style.display = "none";
}


function showChangePointsDiv(idForChangePointsDiv){
    if (document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display === "none") {
        document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display = "block";
    }else{
        document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display = "none";
    }
}






