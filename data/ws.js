var ws;

self.port.on('stop', function (message) {
    console.log(message);
    ws.onclose = function () {};
    ws.send('disconnect from browser');
    ws.close();
});


self.port.on('start', function (message) {
    console.log("PORT ON "+message);
    ws = new WebSocket('ws://' + message);

    ws.onmessage = function (event) {
        console.log(event.data);
        self.port.emit('message', event.data);
    };

    ws.onopen = function (event) {
        ws.send('hello from browser' + event);
    };

    
});
