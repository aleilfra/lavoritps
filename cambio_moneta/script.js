var cambi = new XMLHttpRequest();

var tassi = {};

cambi.open("GET", "eurofxref-daily.xml", true);

cambi.onreadystatechange = function() {

    if (cambi.readyState == 4 && cambi.status == 200) {

        var xml = cambi.responseXML;

        var valute = xml.getElementsByTagName("Cube");

        for (var i = 0; i < valute.length; i++) {

            var valuta = valute[i].getAttribute("currency");
            var cambio = valute[i].getAttribute("rate");

            if (valuta != null) {
                tassi[valuta] = cambio;
            }
        }
    }
};

cambi.send();


function converti() {

    var valuta = document.getElementById("valuta").value;

    var quantita = document.getElementById("quantita").value;

    var cambio = tassi[valuta];

    var euro = quantita / cambio;

    document.getElementById("risultato").innerHTML =
        "Risultato: " + euro.toFixed(2) + " Euro";
}
