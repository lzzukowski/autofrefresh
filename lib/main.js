var data = require("self").data;
var pageWorkers = require("page-worker");
var tabs = require("tabs");

var name = "extensions.refresh.address";
var cfg = require("sdk/preferences/service");

if (!cfg.has(name)) {
    cfg.set(name, 'localhost:8888');
}

var buttons = require("sdk/ui/button/toggle");
var wsWorker;
var L = console.log;

var button = buttons.ToggleButton({
    id: "my-button",
    label: "my button",
    icon: "./icon-32.png",
    onChange: function (state) {
        if (state.checked)
            startConnection();
        else
            stopConnection();
    }
});


function startConnection() {
    wsWorker = pageWorkers.Page({
        contentScriptFile: data.url('ws.js')
    });

    wsWorker.port.emit('start', cfg.get(name));

    wsWorker.port.on('message', function (message) {
        L('received : ' + message);
        tabs.activeTab.reload();
    });
}


function stopConnection() {
    wsWorker.port.emit('stop', 'stop');
}
