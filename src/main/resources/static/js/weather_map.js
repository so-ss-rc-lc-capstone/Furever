//mapbox
mapboxgl.accessToken = keys.mapbox;
const map = new mapboxgl.Map({
    container: 'map', // Container ID
    style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6', // Map style to use
    center: [-98.4916, 29.4252], // Starting position [lng, lat]
    zoom: 12, // Starting zoom level
});

let marker;