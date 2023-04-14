// let eventLoc=[
//     {
//         name: "Taco Blvd",
//         address: "18360 Blanco Rd #116, San Antonio, TX 78258",
//     },
//     {
//         name: "Chick fil a",
//         address: "106 E Houston St, San Antonio, TX  78205",
//     },
//     {
//         name: "Chama Gaucha",
//         address: "18318 Sonterra Pl, San Antonio, TX  78258",
//     }
// ];

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
                console.log(result[0]);
                console.log(result[1]);

                let marker = new mapboxgl.Marker()
                    .setLngLat([result[0],result[1]])
                    .addTo(map);

                let eventPopup = new mapboxgl.Popup()
                    .setHTML(`<h2>Event Name: ${event.title}</h2><h3>Location: ${event.name}</h3><p>Address: ${event.address}</p>`)
                    .on('click', function(e) {
                        // Prevent the default map click behavior
                        e.preventDefault();

                        // Extract the eventId from the event object
                        let eventId = event.id;

                        // Construct the URL for the event details page
                        let url = `http://localhost:8080/events/${eventId}/find?event=${eventId}`;

                        // Open the event details page in a new tab
                        window.open(url, '_blank');
                    });

                marker.setPopup(eventPopup);
            });
        });
    })
    .catch(error => console.error(error));



