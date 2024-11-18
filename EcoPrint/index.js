//if (localStorage.getItem("token") == null) {
 //   alert("É necessário estar logado para continuar")
 ////   window.location.href = "Login/login.html"
//}
document.body.onload = () => {
    btnSearch.addEventListener('click', doSearch)
}

// Definir a função initMap globalmente
function initMap() {
    const options = {
        center: { lat: -19.8582435, lng: -43.921429 },
        zoom: 12,
        zoomControl: true,
        scrollwheel: true,
        draggable: true,
        disableDoubleClickZoom: false
    };

    const map = new google.maps.Map(document.getElementById("mapa"), options);

    // Adiciona a funcionalidade de busca de lugares
    const request = {
        location: options.center,
        radius: '10000', // raio de busca em metros
        type: ['recycling_center'], // tipo de lugar (pode ser alterado)
        keyword: 'recycling'
    };

    const service = new google.maps.places.PlacesService(map);

    service.nearbySearch(request, (results, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            for (let i = 0; i < results.length; i++) {
                createMarker(results[i], map);
            }
        }
    });
}

// Função para criar o marcador no mapa
function createMarker(place, map) {
    new google.maps.Marker({
        position: place.geometry.location,
        map: map,
        title: place.name
    });
}

// Script para o menu do site
document.addEventListener("DOMContentLoaded", function () {
    const menuButton = document.getElementById("menu-button");
    const dropdownMenu = document.getElementById("dropdown-menu");

    menuButton.addEventListener("click", function () {
        if (dropdownMenu.style.opacity === "1") {
            dropdownMenu.style.opacity = "0";  // Torna o menu invisível
            dropdownMenu.style.transform = "translateY(-10px)";  // Move para fora da tela
        } else {
            dropdownMenu.style.display = "block";  // Garante que o menu esteja em display block
            setTimeout(function () {  // Dá tempo para a transição de opacidade funcionar
                dropdownMenu.style.opacity = "1";  // Torna o menu visível
                dropdownMenu.style.transform = "translateY(0)";  // Move para a posição normal
            }, 10); // Um pequeno delay para iniciar a transição
        }
    });

    // Clique fora do menu para fechar
    document.addEventListener("click", function (event) {
        if (!menuButton.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.opacity = "0";  // Torna o menu invisível
            dropdownMenu.style.transform = "translateY(-10px)";  // Move para fora da tela
            setTimeout(function () {  // Após o tempo da transição, oculta o menu
                dropdownMenu.style.display = "none";
            }, 300);  // Tempo suficiente para concluir a animação
        }
    });
});