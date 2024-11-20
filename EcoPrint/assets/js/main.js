/* =====================================
GOOGLE MAPS
========================================*/

// Iniciando e adicionando o mapa
let map;

async function initMap() {
  // Localização no mapa
  const position = { lat: -19.860124, lng: -43.9503157 };

  // Bibliotecas
  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
  const { places } = await google.maps.importLibrary("places");

  // Certifique-se de que o elemento de mapa tenha o tamanho correto
  const mapElement = document.getElementById("map");
  if (!mapElement) {
    console.error("Elemento do mapa não encontrado!");
    return;
  }

  // Exibição no mapa   
  map = new Map(document.getElementById("map"), {
    zoom: 12,
    center: position,
    mapId: "ROADMAP",
    mapTypeControlOptions: {
      style: google.maps.MapTypeControlStyle.DROPDOWN_MENU, // Tipo de barra (exemplo de estilo horizontal)
      position: google.maps.ControlPosition.LEFT_TOP, // Posição dos controles
    }

  });

  // Defina o conteúdo do marcador
  const beachFlagImg = document.createElement("img");
  beachFlagImg.src = "assets/img/planeta-terra.png";
  beachFlagImg.alt = "Gráfica Utilizando EcoPrint";
  beachFlagImg.style.width = "42px";
  beachFlagImg.style.height = "42px";

  // Localização do marcador
  const marker = new AdvancedMarkerElement({
    map: map,
    position: position,
    content: beachFlagImg,
    title: "Gráfica que Utiliza EcoPrint",
    zIndex: 999
  });

  // Adiciona a search box
  const input = document.createElement("input");
  input.id = "pac-input";
  input.className = "controls";
  input.type = "text";
  input.placeholder = "Pesquisa...";

  const pacCard = document.createElement("div");
  pacCard.className = "pac-card";
  pacCard.appendChild(input);

  map.controls[google.maps.ControlPosition.TOP_LEFT].push(pacCard);

  const searchBox = new google.maps.places.SearchBox(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener("bounds_changed", () => {
    searchBox.setBounds(map.getBounds());
  });

  searchBox.addListener("places_changed", () => {
    const places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }

    // Clear out the old markers.
    //marker.map = null;

    // For each place, get the icon, name and location.
    const bounds = new google.maps.LatLngBounds();

    places.forEach((place) => {
      if (!place.geometry || !place.geometry.location) {
        console.log("Returned place contains no geometry");
        return;
      }

      // Create a marker for each place.
      const newMarker = new AdvancedMarkerElement({
        map: map,
        position: place.geometry.location,
        title: place.name,
      });

      if (place.geometry.viewport) {
        // Only geocodes have viewport.
        bounds.union(place.geometry.viewport);
      } else {
        bounds.extend(place.geometry.location);
      }
    });

    map.fitBounds(bounds);
  });

  // Adiciona a funcionalidade de busca de lugares ao iniciar
  const request = {
    location: position,
    radius: '10000', // raio de busca em metros
    type: ['store'], // tipo de lugar (pode ser alterado)
    keyword: 'Recarga de cartuchos' // termo de busca
  };
  const service = new google.maps.places.PlacesService(map);
  service.nearbySearch(request, (results, status) => {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
      for (let i = 0; i < results.length; i++) {
        createAdvancedMarker(results[i], map);
      }
    }
  });
}
function createAdvancedMarker(place, map) {
  new google.maps.marker.AdvancedMarkerElement({
    position: place.geometry.location,
    map: map,
    title: place.name
  });
}

initMap();

(function () {
  "use strict";
  /**
   * Aplique a classe .scrolled ao corpo conforme a página rola para baixo.
   */
  function toggleScrolled() {
    const selectBody = document.querySelector('body');
    const selectHeader = document.querySelector('#header');
    if (!selectHeader.classList.contains('scroll-up-sticky') && !selectHeader.classList.contains('sticky-top') && !selectHeader.classList.contains('fixed-top')) return;
    window.scrollY > 100 ? selectBody.classList.add('scrolled') : selectBody.classList.remove('scrolled');
  }
  document.addEventListener('scroll', toggleScrolled);
  window.addEventListener('load', toggleScrolled);

  /**
   * Responsividade para móveis
   */
  const mobileNavToggleBtn = document.querySelector('.mobile-nav-toggle');

  function mobileNavToogle() {
    document.querySelector('body').classList.toggle('mobile-nav-active');
    mobileNavToggleBtn.classList.toggle('bi-list');
    mobileNavToggleBtn.classList.toggle('bi-x');
  }
  mobileNavToggleBtn.addEventListener('click', mobileNavToogle);

  /**
   * Ocultar navegação móvel em links de mesma página/hash
   */
  document.querySelectorAll('#navmenu a').forEach(navmenu => {
    navmenu.addEventListener('click', () => {
      if (document.querySelector('.mobile-nav-active')) {
        mobileNavToogle();
      }
    });

  });

  /**
   * Alternar menus suspensos de navegação móvel
   */
  document.querySelectorAll('.navmenu .toggle-dropdown').forEach(navmenu => {
    navmenu.addEventListener('click', function (e) {
      e.preventDefault();
      this.parentNode.classList.toggle('active');
      this.parentNode.nextElementSibling.classList.toggle('dropdown-active');
      e.stopImmediatePropagation();
    });
  });

  /**
   * Preloader
   */
  const preloader = document.querySelector('#preloader');
  if (preloader) {
    window.addEventListener('load', () => {
      preloader.remove();
    });
  }

  /**
   * Scroll top button
   */
  let scrollTop = document.querySelector('.scroll-top');

  function toggleScrollTop() {
    if (scrollTop) {
      window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
    }
  }
  scrollTop.addEventListener('click', (e) => {
    e.preventDefault();
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  });

  window.addEventListener('load', toggleScrollTop);
  document.addEventListener('scroll', toggleScrollTop);

  /**
   * Animação na função de rolagem e inicialização
   */
  function aosInit() {
    AOS.init({
      duration: 600,
      easing: 'ease-in-out',
      once: true,
      mirror: false
    });
  }
  window.addEventListener('load', aosInit);

  /**
   * Iniciar Glightbox
   */
  const glightbox = GLightbox({
    selector: '.glightbox'
  });

  /**
   * Alternar perguntas frequentes
   */
  document.querySelectorAll('.faq-item h3, .faq-item .faq-toggle').forEach((faqItem) => {
    faqItem.addEventListener('click', () => {
      faqItem.parentNode.classList.toggle('faq-active');
    });
  });
}
)();


