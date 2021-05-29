function emailCheckSubmit() {
    var obj = document.getElementById("emailCheckEmail").value;
    if (checkEmail(obj) == true) {
        var btnSubmit = document.getElementById("emailCheckButton");
        var script = document.createElement('script');
        script.setAttribute("type", "text/javascript");
        dosubmit(btnSubmit);

        $.post(
            url = "/users/emailCheck",
            {
                "Email": obj
            },
            function (data) {
                console.log("Data: " + data);
                if (data == 10000) {
                    var urlEmail = window.btoa(window.encodeURIComponent(obj));
                    var location = "../HTML/Reset.html?" + urlEmail;
                    window.location.href = location;
                    // window.location.href = "../HTML/Reset.html";
                } else {
                    alert("Your account is not in the database. \nPlease register an account!")
                    window.location.href = "../HTML/Register.html";
                }
            },
            type = "text"
        )

    } else {
        console.log("Please correct your email address.");
    }

}
