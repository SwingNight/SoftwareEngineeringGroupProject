function defaultAccount() {
    var thisURL = document.URL;
    var check = thisURL.indexOf('?');
    if (check != -1) {
        var urlEmail = thisURL.split('?')[1];
        var defaultEmail = window.decodeURIComponent(window.atob(urlEmail));
        var currentAccount = document.getElementById("loginEmail");
        currentAccount.value = defaultEmail;
    }
}

function loginSubmit() {
    var email = document.getElementById("loginEmail").value;
    var password = document.getElementById("loginPassword").value;
    if (checkEmail(email) && checkEmpty(password)) {
        var btnSubmit = document.getElementById("loginButton");
        dosubmit(btnSubmit);
        $.post(
            url = "/users/login",
            {
                "email": email,
                "password": password
            },
            function (data) {
                console.log("Data: " + data);
                if (data == 10000) {
                    console.log("Login Successfully.");
                    var urlEmail = window.btoa(window.encodeURIComponent(email));
                    var location = "../../main.html?" + urlEmail;
                    window.location.href = location;
                } else {
                    alert("Error! \nPlease login again and check your account and password.")
                    window.location.href = "../HTML/Login.html";
                }
            },
            type = "text"
        )
    } else {
        console.log("Please correct your email address or password.");
    }
}