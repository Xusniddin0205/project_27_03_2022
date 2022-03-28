
$(document).ready(function (){




//Knopka idisi
    var mybutton = document.getElementById("myBtn");

// Sahifada ko'rinadi
    window.onscroll = function () {
        scrollFunction()
    };

    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            mybutton.style.display = "block";
        } else {
            mybutton.style.display = "none";
        }
    }



});





