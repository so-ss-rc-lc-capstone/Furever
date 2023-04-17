
//Function to get all the event locations
function allEvents() {

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
                style: 'mapbox://styles/mapbox/dark-v11',
                zoom: 13,
                center: [-98.4916, 29.4252]
            });

            // Get the user's current position
            navigator.geolocation.getCurrentPosition(position => {
                // Update the map's center to the user's position
                let userPosition = [position.coords.longitude, position.coords.latitude];
                map.setCenter(userPosition);
                map.setZoom(10);

                event.forEach(function (event) {
                    geocode(event.address, keys.mapbox).then(function (result) {
                        let eventPosition = [result[0], result[1]];

                        let marker = new mapboxgl.Marker()
                            .setLngLat(eventPosition)
                            .addTo(map);

                        let eventPopup = new mapboxgl.Popup()
                            .setHTML(`<h2><a href="/events/${event.id}/find?event=${event.id}">${event.title}</a></h2><h3><a href="/events/${event.id}/find?event=${event.id}">${event.name}</a></h3><p>Address: ${event.address}</p>`)

                        marker.setPopup(eventPopup);
                    });
                });
            });

            function deg2rad(deg) {
                return deg * (Math.PI / 180)
            }

        })
        .catch(error => console.error(error));
}
// ==========> Events within 50 miles radius <=================

let withinFiftymilesIds = [];

async function withinFiftyMiles() {

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
                style: 'mapbox://styles/mapbox/dark-v11',
                zoom: 13,
                center: [-98.4916, 29.4252]
            });

            // Get the user's current position
            navigator.geolocation.getCurrentPosition(position => {
                // Update the map's center to the user's position
                let userPosition = [position.coords.longitude, position.coords.latitude];
                map.setCenter(userPosition);
                map.setZoom(10);

                event.forEach(function (event) {
                    geocode(event.address, keys.mapbox).then(function (result) {
                        // Calculate distance between user location and event location
                        let eventPosition = [result[0], result[1]];
                        let distance = getDistance(userPosition, eventPosition);


                        if (distance <= 50) { // Display event marker only if within 50 miles
                            let marker = new mapboxgl.Marker()
                                .setLngLat(eventPosition)
                                .addTo(map);

                            let eventPopup = new mapboxgl.Popup()
                                .setHTML(`<h2><a href="/events/${event.id}/find?event=${event.id}">${event.title}</a></h2><h3><a href="/events/${event.id}/find?event=${event.id}">${event.name}</a></h3><p>Address: ${event.address}</p>`)
                            withinFiftymilesLocation.push(event.id);
                            marker.setPopup(eventPopup);
                        }
                    });
                });
            });


            function getDistance(coord1, coord2) {
                const R = 3958.8; // Earth's radius in miles
                const lat1 = deg2rad(coord1[1]);
                const lon1 = deg2rad(coord1[0]);
                const lat2 = deg2rad(coord2[1]);
                const lon2 = deg2rad(coord2[0]);

                const dLat = lat2 - lat1;
                const dLon = lon2 - lon1;

                const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(lat1) * Math.cos(lat2) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                const d = R * c;

                return d;
            }

            function deg2rad(deg) {
                return deg * (Math.PI / 180)
            }

        })
        .catch(error => console.error(error));
}

withinFiftyMiles();
// for(let i = 0; i < withinFiftymilesLocation.length; i++) {
//     console.log("Test" + withinFiftymilesLocation[i]);
// }
    console.log(withinFiftymilesIds);