var account = window.parent.email;
console.log("This is para:" + account);

function load() {
    var acc = document.getElementById("acc");
    acc.innerHTML = account;
    $.post(
        url = "/users/getUser",
        {
            "account": account
        },
        function (data) {
            console.log(data);
            var data = JSON.parse(data);
            var name = document.getElementById("name");
            name.value = data.name;
            // yyyy-MM-dd
            var bir = document.getElementById("birthday");
            bir.value = data.birthday;
            var cons = document.getElementById("cons");
            cons.value = data.cons;
        },
        type = "text"
    )

}

function disable(id) {
    var obj = document.getElementById(id);
    obj.disabled = true;
}

function able(id) {
    var obj = document.getElementById(id);
    obj.disabled = false;
}

function save(id) {
    disable(id);
    var name = document.getElementById("name").value;
    var bir = document.getElementById("birthday").value;
    var cons = document.getElementById("cons").value;
    $.post(
        url = "/users/update",
        {
            "account": account,
            "name": name,
            "birthday": bir,
            "cons": cons
        },
        function (data) {
            console.log(data);
            if (data == 1) {
                alert("Please save again!")
            } else {
                alert('save success');
            }
        },
        type = "text"
    )
    able(id);
}

// exit
function exit() {
    var urlEmail = window.btoa(window.encodeURIComponent(account));
    var location = "../prepage/HTML/Login.html?" + urlEmail;
    window.parent.location.href = location;
    // window.parent.location.href = "../prepage/HTML/Login.html";
}

function reset() {
    var urlEmail = window.btoa(window.encodeURIComponent(account));
    var location = "../prepage/HTML/Reset.html?" + urlEmail;
    window.parent.location.href = location;
    // window.parent.location.href = "../prepage/HTML/Reset.html";
}