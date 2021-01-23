function deleteDish(dishId) {
    let result = confirm("Are you really want to delete the dish?");
    if (result) {
        let form = document.getElementById('deleteForm' + dishId);
        form.submit();
    }

}




function getOrderInfo(orderId) {
    let div = document.getElementById('ajaxDiv');
    if (div.style.display === "flex") {
        div.style.display = "none";
        return;
    }

    let request = new XMLHttpRequest();

    request.open('GET', ctx + '/ajax?orderId=' + orderId, true);
    request.addEventListener('readystatechange', function () {
        if ((request.readyState === 4) && (request.status === 200)) {
            let result = JSON.parse(request.responseText);
            document.getElementById("cost").innerHTML = result.cost;

            var strDate = result.date.date.day + '/' + result.date.date.month + '/' + result.date.date.year;
            strDate += ' ' + result.date.time.hour + ':' + result.date.time.minute;
            document.getElementById("date").innerHTML = strDate;

            let dishNames = result.dishNames;
            let str = ' ';
            for (var index in dishNames) {
                str += dishNames[index];
                str += '<br>'
            }

            document.getElementById("dishNames").innerHTML = str;
            div.style.display = "flex";
        }
    });
    request.send();


}


function showChangeAvatarForm() {
    if (document.getElementById("changeAvatarForm").style.display === "none") {
        document.getElementById("changeAvatarForm").style.display = "block";
    } else {
        document.getElementById("changeAvatarForm").style.display = "none";
    }
}

function showLoginDiv() {
    if (document.getElementById("myForm").style.display === "none") {
        document.getElementById("myForm").style.display = "contents";
    } else {
        document.getElementById("myForm").style.display = "none";
    }
}


function showChangePointsDiv(idForChangePointsDiv) {
    if (document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display === "none") {
        document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display = "block";
    } else {
        document.getElementById("changePointsDiv" + idForChangePointsDiv).style.display = "none";
    }
}

function showChangeCommentForm(dishCommentId) {
    if (document.getElementById("changeCommentForm" + dishCommentId).style.display === "none") {
        document.getElementById("changeCommentForm" + dishCommentId).style.display = "flex";
    } else {
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

function openChangeMoneyForm() {
    if (document.getElementById("addMoneyBlock").style.display === "none") {
        document.getElementById("addMoneyBlock").style.display = "flex";
    } else {
        document.getElementById("addMoneyBlock").style.display = "none";
    }
}

function openAddDishForm() {
    if (document.getElementById("add_dish_form").style.display === "none") {
        document.getElementById("add_dish_form").style.display = "flex";
    } else {
        document.getElementById("add_dish_form").style.display = "none";
    }
}



