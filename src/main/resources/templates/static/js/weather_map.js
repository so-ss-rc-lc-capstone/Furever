//mapbox
mapboxgl.accessToken = keys.mapbox;
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-98.4916, 29.4252],
    zoom: 10
});



document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    var start = document.getElementById('start').value;
    var end = document.getElementById('end').value;
    var url = 'https://api.mapbox.com/directions/v5/mapbox/driving/' + start + ';' + end + '?access_token=' + mapboxgl.accessToken;
    console.log(start);
    console.log(stop);
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