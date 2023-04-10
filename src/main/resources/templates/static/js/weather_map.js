mapboxgl.accessToken = keys.mapbox;
//ask for user location
navigator.geolocation.getCurrentPosition(successLocation, errorLocation, {enableHighAccuracy: true})
function successLocation(position){
    console.log(position);
    setupMap([position.coords.longitude, position.coords.latitude])
}
function errorLocation(){
    setupMap([-98.4916, 29.4252])
}
function setupMap(center){
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11',
        center: center, //[-98.4916, 29.4252],
        zoom: 10
    })
    //navigation control
    const nav = new mapboxgl.NavigationControl()
    map.addControl(nav);

    var directions = new MapboxDirections({
        accessToken: mapboxgl.accessToken
    })

    map.addControl(directions, "top-left")
}




var distance;

function formatDistance(distance) {
    if (distance > 1000) {
        return (distance / 1000).toFixed(1) + ' km';
    } else {
        return distance.toFixed(0) + ' m';
    }
}

document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    var start = document.getElementById('start').value;
    var end = document.getElementById('end').value;
    var url = 'https://api.mapbox.com/directions/v5/mapbox/driving/' + start + ';' + end + '?access_token=' + mapboxgl.accessToken;
    console.log(start);
    console.log(end);
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (!data.routes || !data.routes[0]) {
                throw new Error('No routes found');
            }
            var route = data.routes[0].geometry;
            distance = data.routes[0].distance;
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

            var popup = new mapboxgl.Popup({ closeOnClick: false })
                .setLngLat(end)
                .setHTML('<p>' + formatDistance(distance) + '</p>')
                .addTo(map);

            map.on('click', 'route', function(e) {
                popup.setLngLat(e.lngLat).addTo(map);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
});
