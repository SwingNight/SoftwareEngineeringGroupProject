function registerGetCode() {
    var email = document.getElementById("registerEmail").value;
    if (checkEmail(email) == true) {
        var btnSubmit = document.getElementById("registerCodeButton");
        dosubmit(btnSubmit);
        $.post(
            url = "/users/register",
            {
                "email": email
            },
            function (data) {
                console.log("Data: " + data);
                if (data == 10000) {
                    alert("Success! \nPlease get your code in email.");
                } else if (data == 10001) {
                    alert("Error! \nPlease refresh the page and enter your email again.")
                } else {
                    alert("Error！\nYour account is in the database. \nPlease login directly. \nOr correct your code.");
                    // window.location.href = "../HTML/Login.html";
                }
            },
            type = "text"
        )
    } else {
        console.log("Please correct your email address.");
    }
}

function registerSubmit() {
    var email = document.getElementById("registerEmail").value;
    var password1 = document.getElementById("registerPassword1").value;
    var password2 = document.getElementById("registerPassword2").value;
    var code = document.getElementById("registerCode").value;
    if (checkEmail(email) && checkEmpty(code) && checkPassword(password1, password2)) {
        var btnSubmit = document.getElementById("registerButton");
        dosubmit(btnSubmit);
        $.post(
            url = "/users/registerCheck",
            {
                "email": email,
                "code": code,
                "password": password1
            },
            function (data) {
                console.log("Data: " + data);
                if (data == 10000) {
                    var urlEmail = window.btoa(window.encodeURIComponent(email));
                    var location = "../HTML/Login.html?" + urlEmail;
                    window.location.href = location;
                    // window.location.href = "../HTML/Login.html";
                } else if (data == 10001) {
                    alert("Error！\nPlease enter the correct code.");
                } else {
                    alert("Error！\nPlease refresh the page and register again.");
                }
            },
            type = "text"
        )

    }
}