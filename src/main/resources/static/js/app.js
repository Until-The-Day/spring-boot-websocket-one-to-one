
var ws;
ws = new WebSocket("ws://" + location.host + "/call");

// 接收返回的信息
ws.onmessage = function (ev) {
    var message = JSON.parse(ev.data);

    switch (message.id) {
        case 'call':
            console.log(message.msg)
            break
        default:
            break
    }

};

function singIn() {
    var name = $("#name").val();
    var msg = {
        "id": "sign-in",
        "name": name
    }
    sendMsg(msg);
}

function call() {
    var callName = $("#callName").val();
    var content = $("#callVal").val();

    var msg = {
        "id": "call",
        "callName": callName,
        "content": content
    }
    sendMsg(msg);
}

function sendMsg(msg) {
    var jsonIfy = JSON.stringify(msg);
    ws.send(jsonIfy);
}


window.onbeforeunload = function() {
    ws.close();
};

