function defaultAccount() {
    var thisURL = document.URL;
    var check = thisURL.indexOf('?');
    if (check != -1) {
        var urlEmail = thisURL.split('?')[1];
        var defaultEmail = window.decodeURIComponent(window.atob(urlEmail));
        var currentAccount = document.getElementById("resetEmail");
        currentAccount.value = defaultEmail;
    }
}


function resetGetCode() {
    var email = document.getElementById("resetEmail").value;
    if (checkEmail(email) == true) {
        var btnSubmit = document.getElementById("resetCodeButton");
        dosubmit(btnSubmit);
        $.post(
            url = "/users/resetPass",
            {
                "email": email
            },
            function (data) {
                console.log("Data: " + data);
                if (data == 10000) {
                    alert("Success! \nPlease get your code in email.");
                } else {
                    alert("Error！\nPlease refresh the page and enter your email again.");

                }
            },
            type = "text"
        )
    }
}


function resetSubmit() {
    var email = document.getElementById("resetEmail").value;
    var password1 = document.getElementById("resetPassword1").value;
    var password2 = document.getElementById("resetPassword2").value;
    var code = document.getElementById("resetCode").value;
    if (checkEmail(email) && checkEmpty(code) && checkPassword(password1, password2)) {
        var btnSubmit = document.getElementById("resetButton");
        dosubmit(btnSubmit);
        $.post(
            url = "/users/finishResetPass",
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
                } else {
                    alert("Error！\nPlease refresh the page and register again.")
                }
            },
            type = "text"
        )

    }
}