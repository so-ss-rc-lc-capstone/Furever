fetch('http://localhost:8080/api/allevents', {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    }
})
    .then(response => response.json())
    .then(data => {
        const event = data.map(event => ({
            id: event.id,
            title: event.title,
            name: event.location_name,
            address: event.location_address
        }));

        mapboxgl.accessToken = keys.mapbox;
        let map = new mapboxgl.Map({
            container: 'map',
            // style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6',
            style: 'mapbox://styles/mapbox/dark-v11',
            zoom: 13,
            center: [-98.4916, 29.4252]
        });

        // Get the user's current position
        navigator.geolocation.getCurrentPosition(position => {
            // Update the map's center to the user's position
            map.setCenter([position.coords.longitude, position.coords.latitude]);
            map.setZoom(10);
        });

        event.forEach(function(event) {
            geocode(event.address, keys.mapbox).then(function (result) {
                let marker = new mapboxgl.Marker()
                    .setLngLat([result[0],result[1]])
                    .addTo(map);

                let eventPopup = new mapboxgl.Popup()
                    .setHTML(`<h2><a href="/events/${event.id}/find?event=${event.id}">${event.title}</a></h2><h3><a href="/events/${event.id}/find?event=${event.id}">${event.name}</a></h3><p>Address: ${event.address}</p>`)

                marker.setPopup(eventPopup);
            });
        });
    })
    .catch(error => console.error(error));

