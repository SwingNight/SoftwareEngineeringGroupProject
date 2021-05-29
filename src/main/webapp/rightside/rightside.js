// The interfaces are at the bottom
// Do not forget to see the web page in 100% system scalling

// default date: today
function today() {
    var today = new Date();
    today = getFormatDate(today);
    var box = document.getElementById("showDay");
    box.innerHTML = "today";
    console.log("Today is " + today); // yyyy-mm-dd
    getEventData(today);
}

// when date changed on <input type = "date">
// function change() {
//     var time = document.getElementById("theday").value;
//     console.log("Show events of " + time); // yyyy-mm-dd
//     del();
//     getEventData(time);
// }

// when click on the home calendar
function clickHomeCalendar(day) {
    console.log("Click on the home calendar: " + day);
    var box = document.getElementById("showDay");
    box.innerHTML = day;
    del();
    getEventData(day);
}

// copy from calender.html
// format like xxxx-xx-xx
function getFormatDate(date) {
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

// format like xxxx/xx/xx or xx/xx/xxxx
// useless
function getShortFormatDate(date) {
    date = date.toLocaleDateString();
    return date;
}

// delete HTML elements
function del() {
    $("div.list").remove();
}

// sort of time
function sortDownDate(a, b) {
    return Date.parse(a.start) - Date.parse(b.start);
}

// set background color
function confirmTime(end) {
    var now = new Date();
    now = Date.parse(now);
    end = Date.parse(end);
    if (end > now) {
        return "white";
    } else {
        return "#d8dbde";
    }
}

// del the logo
function imageManagement(length) {
    if (length > 4) {
        $("div.label img").remove();
    }
}


// clip time
function manageTime(time) {
    if (time.length > 15) {
        var newTime = time.substring(11);
        return newTime;
    } else {
        return time;
    }

}

// set color according to the emergency(not implemented)
function setColor(grade) {
    if (grade == "Red") {
        return "rgb(241, 11, 11)"; // red
    } else if (grade == "Blue") {
        return "rgb(0, 191, 255)"; // blue
    } else {
        return "rgb(25, 192, 75)"; // green
    }
}

// create HTML elements
function create(id, color, title, address, start, end, finish) {
    var put = document.getElementById("place");
    var div1 = document.createElement("div");
    div1.className = "list";
    div1.style = "background-color: " + confirmTime(end) + ";"; // check overdue
    put.appendChild(div1);
    var div2 = document.createElement("div");
    div2.style = "width: 2rem;";
    div2.className = "checkboxFive";
    div1.appendChild(div2);
    // checkbox
    var box = document.createElement("input");
    box.type = "checkbox";
    box.id = id;
    // onclick function
    // box.onclick = function () {
    //     addFinish(id, title, place, start, end);
    // };
    box.onclick = function () {
        addFinish(id, title, start, end);
    };
    box.checked = finish;
    div2.appendChild(box);
    // label
    var label = document.createElement("label");
    label.htmlFor = id;
    var style = "border-color: " + color + ";";
    label.style = style;
    div2.appendChild(label);
    // task
    var div3 = document.createElement("div");
    div1.appendChild(div3);
    var dt1 = document.createElement("dt");
    dt1.className = "tasktitle";
    dt1.innerHTML = title;
    dt1.title = title;
    div3.appendChild(dt1);
    var dt2 = document.createElement("dt");
    dt2.className = "taskplace";
    dt2.innerHTML = address;
    dt2.title = address;
    div3.appendChild(dt2);

    // time
    var div4 = document.createElement("div");
    div1.appendChild(div4);
    var dt3 = document.createElement("dt");
    dt3.className = "time";
    newstart = manageTime(start);
    dt3.innerHTML = newstart;
    dt3.title = start;
    div4.appendChild(dt3);
    var dt4 = document.createElement("dt");
    dt4.className = "time";
    newend = manageTime(end);
    dt4.innerHTML = newend;
    dt4.title = end;
    div4.appendChild(dt4);
}

// iterate through the JSON array
function management(data) {
    var data = JSON.parse(data);
    imageManagement(data.length);
    data = data.sort(sortDownDate);
    for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var id = "box" + i;
        // color means emergency level
        var color = setColor(temp.grade);
        var title = temp.title;
        var address = temp.address;
        var start = temp.start;
        var end = temp.end;
        var finish = temp.finish;
        create(id, color, title, address, start, end, finish);
    }
}

// get status of the checkbox
function status(id) {
    var box = document.getElementById(id);
    console.log("Current status of " + id + " is " + box.checked);
    return box.checked;
}

// interface
function getEventData(date) {
    var account = email;
    $.post(
        url = "/event/getEventData",
        {
            "account": account,
            "date": date
        },
        function (data) {
            console.log("getEventData: " + data);
            management(data);
        },
        type = "text"
    )

}

// interface
function addFinish(id, title, start, end) {
    var account = email;
    var finish = status(id);
    $.post(
        url = "/event/addFinish",
        {
            "account": account,
            "title": title,
            "start": start,
            "end": end,
            "finish": finish
        },
        function (data) {
            console.log("addFinish return: " + data);
            if (data == 11) {
                alert("Error!\nPlease confirm again.")
            }
        },
        type = "text"
    )
}
