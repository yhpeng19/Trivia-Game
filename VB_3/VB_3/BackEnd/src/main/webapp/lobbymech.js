var ws;

function connect() {
    var username = document.getElementById("username").value;
    var identity = document.getElementById("gameId").value;

    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" +"localhost:8080"+"/game_lobby" + "/"+username+"/"+identity);

    ws.onmessage = function(event) {
    var log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML += event.data + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;

    ws.send(content);
}