var wsUri = "ws://" + document.location.host + "/STWGpus/stwGpus";
var webSocket;

var titulo = document.getElementById("prueba");
var provinciaSelect = document.getElementById("provinciaSelect");
var municipioSelect = document.getElementById("municipioSelect");
var eessSelect = document.getElementById("eessSelect");
var pruebaPrecios = document.getElementById("pruebaPrecios");
openSocket();

/**
 * ==================== openSocket =========================================
 * @returns {undefined}
 */
function openSocket() {
    console.log("1OPENING: " + wsUri);
    // Ensures only one connection is open at a time
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        closeSocket();
    }

    webSocket = new WebSocket(wsUri);

    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function (event) {
        if (event.data === undefined) {
            return;
        }
        console.log(event.data);
    };

    webSocket.onmessage = function (event) {
        var msg = event.data;

        console.log("==== " + msg);
        var json = JSON.parse(event.data);
        console.log("EEEEEEEE")
        console.log(json)
        console.log(json.cmnd)
        switch (json.cmnd) {
            case "datePrecioLuzResult":
            break;
            case "datePrecioEthResult":
            break;
            case "listaDatosTemp":
                console.log("SSSSSS")
                drawPrices(json.values);
                break;
            case "listaDatosEfi":
            break;
            case "listaDatosPower":
            break;
            case "listaDatosHash":
                drawPrices(json.values);
                break;

        }


    };

    webSocket.onclose = function (event) {
        console.log("Connection Closed");
    };

    webSocket.onerror = function (event) {
        console.log("ERROR: " + event.toString());
    };
} //openSocket





function initGrafica(tipoGrafica) {
    console.log("HEEE");
    grafica = new google.visualization.LineChart(document.getElementById('graficaLuz'));
    datosGrafica = new google.visualization.DataTable();
    datosGrafica.addColumn('string', 'Hora');

    if (tipoGrafica == null || tipoGrafica == "nada") {
        datosGrafica.addColumn('number', 'Precio');
        optionsGrafico = {
            chart: {title: 'Precio'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };

    } else if (tipoGrafica == "luz") {
        datosGrafica.addColumn('number', 'Precio Luz (€/MWh)');
        optionsGrafico = {
            chart: {title: 'Precio Luz'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };
    } else if (tipoGrafica == "eth") {
        datosGrafica.addColumn('number', 'Precio ETH');
        optionsGrafico = {
            chart: {title: 'Precio ETH'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };
    } else if (tipoGrafica == "temp") {
        datosGrafica.addColumn('number', 'ºC');
        optionsGrafico = {
            chart: {title: 'ºC'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };
    } else if (tipoGrafica == "efi") {
        datosGrafica.addColumn('number', 'Hash/Watt');
        optionsGrafico = {
            chart: {title: 'Hash/Watt'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };
    } else if (tipoGrafica == "watt") {
        datosGrafica.addColumn('number', 'Watt');
        optionsGrafico = {
            chart: {title: 'Watt'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };
    } else if (tipoGrafica == "hash") {
        datosGrafica.addColumn('number', 'Hash/s');
        optionsGrafico = {
            chart: {title: 'hash'},
            vAxis: {format: 'decimal'},
            curveType: 'function',
            legend: {position: 'bottom'}
        };
    }

    grafica.draw(datosGrafica, optionsGrafico);
}


function drawPrices(value) {
    if (datosGrafica !== undefined) {
        if (value) {
            for (var i = 0; i < value.length; i += 1) {
                console.log("heee " + value[i].id + " y la hora es " + value[i].hora);
                datosGrafica.addRow([value[i].fecha, value[i].precio]);
            }

            if (value.length == 0) {
            }
            grafica.draw(datosGrafica, optionsGrafico);
        }

    }


}




/**
 * ==================== closeSocket =========================================
 * @returns {undefined}
 */
function closeSocket() {
    webSocket.close();
}





function prueba() {
    var graficaSeleccionada = document.getElementById("tipoGrafica").value;
    initGrafica(graficaSeleccionada)
    webSocket.send("{\"comando\": \"tipoGrafica\", \"values\": \"" + graficaSeleccionada + "\" }");

}


