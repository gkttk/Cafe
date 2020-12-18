function openPanel() {
    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;
            if (panel.style.maxHeight) {
                panel.style.maxHeight = null;
            } else {
                panel.style.maxHeight = panel.scrollHeight + "px";
            }
        });
    }
}

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
