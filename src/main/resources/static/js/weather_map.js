//mapbox
mapboxgl.accesToken = keys.mapbox;
const map = new mapboxgl.Map({
    container: 'map', // Container ID
    style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6', // Map style to use
    center: [-98.4916, 29.4252], // Starting position [lng, lat]
    zoom: 12, // Starting zoom level
});


document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    var start = document.getElementById('start').value;
    var end = document.getElementById('end').value;
    var url = 'https://api.mapbox.com/directions/v5/mapbox/driving/' + start + ';' + end + '?access_token=' + mapboxgl.accessToken;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            var route = data.routes[0].geometry;
            map.addLayer({
                'id': 'route',
                'type': 'line',
                'source': {
                    'type': 'geojson',
                    'data': {
                        'type': 'Feature',
                        'properties': {},
                        'geometry': route
                    }
                },
                'layout': {
                    'line-join': 'round',
                    'line-cap': 'round'
                },
                'paint': {
                    'line-color': '#888',
                    'line-width': 8
                }
            });

            var bounds = route.coordinates.reduce(function(bounds, coord) {
                return bounds.extend(coord);
            }, new mapboxgl.LngLatBounds(route.coordinates[0], route.coordinates[0]));
            map.fitBounds(bounds, { padding: 20 });
        });
});