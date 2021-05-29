function checkEmail(email) {
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
    if (email == "") {
        alert("Please enter your email address.");
        return false;
    } else if (!reg.test(email)) {
        alert("Email format error!");
        return false;
    } else {
        console.log("Email address Pass.");
        return true;
    }
}

function checkEmpty(obj) {
    if (obj == "") {
        alert("Please fill the blank.")
        return false;
    } else {
        return true;
    }
}

function checkPassword(password1, password2) {
    if (password1 == "" || password2 == "") {
        alert("Please enter password.");
        return false;
    } else if (password1 != password2) {
        alert("Please repeat the password correctly.");
        return false;
    } else {
        console.log("Password Pass");
        return true;
    }
}

function dosubmit(btnSubmit) {
    btnSubmit.disabled = "disabled";
}