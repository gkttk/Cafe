
function showChangeAvatarForm(){
    if (document.getElementById("changeAvatarForm").style.display === "none") {
        document.getElementById("changeAvatarForm").style.display = "block";
    }else{
        document.getElementById("changeAvatarForm").style.display = "none";
    }
}

function showLoginDiv(){
    if (document.getElementById("myForm").style.display === "none") {
        document.getElementById("myForm").style.display = "contents";
    }else{
        document.getElementById("myForm").style.display = "none";
    }
}



function showChangePointsDiv(idForChangePointsDiv){
    if (document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display === "none") {
        document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display = "block";
    }else{
        document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display = "none";
    }
}

function showChangeCommentForm(dishCommentId){
    if (document.getElementById("changeCommentForm" + dishCommentId).style.display === "none") {
        document.getElementById("changeCommentForm" + dishCommentId).style.display = "flex";
    }else{
        document.getElementById("changeCommentForm" + dishCommentId).style.display = "none";
    }
}

function openCommentForm() {
    if (document.getElementById("commentDiv").style.display === "none") {
        document.getElementById("commentDiv").style.display = "block";
    } else {
        document.getElementById("commentDiv").style.display = "none";
    }
}


