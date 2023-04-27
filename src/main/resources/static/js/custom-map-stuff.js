
//Function to get all the event locations

function allEvents() {
    fetch(`${window.location.protocol}//${window.location.host}/api/allevents`, {
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
                zoom: 3,
                center: [-98.4916, 45.4252]
            });

            // Get the user's current position
            navigator.geolocation.getCurrentPosition(position => {
                // Update the map's center to the user's position
                let userPosition = [position.coords.longitude, position.coords.latitude];
                map.setCenter(userPosition);
                map.setZoom(3);
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
let localLocations = async function () {
    let withinFiftyMilesIds = [];
    try {
        const response = await fetch(`${window.location.protocol}//${window.location.host}/api/allevents`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        });
        const data = await response.json();
        const events = data.map(event => ({
            id: event.id,
            title: event.title,
            name: event.location_name,
            address: event.location_address
        }));

        mapboxgl.accessToken = keys.mapbox;
        let map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/dark-v11',
            zoom: 10,
            center: [-98.4916, 29.4252]
        });




        // Get the user's current position
        let userPosition = await new Promise((resolve, reject) => {
            navigator.geolocation.getCurrentPosition(position => {
                resolve([position.coords.longitude, position.coords.latitude]);
            }, reject);
        });
        // Update the map's center to the user's position
        map.setCenter(userPosition);
        map.setZoom(10);

        await Promise.all(events.map(async function (event) {
            const result = await geocode(event.address, keys.mapbox);
            // Calculate distance between user location and event location
            let eventPosition = [result[0], result[1]];
            let distance = getDistance(userPosition, eventPosition);

            if (distance <= 50) { // Display event marker only if within 50 miles
                let marker = new mapboxgl.Marker()
                    .setLngLat(eventPosition)
                    .addTo(map);

                let eventPopup = new mapboxgl.Popup()
                    .setHTML(`<h2><a href="/events/${event.id}/find?event=${event.id}">${event.title}</a></h2><h3><a href="/events/${event.id}/find?event=${event.id}">${event.name}</a></h3><p>Address: ${event.address}</p>`)
                withinFiftyMilesIds.push(event.id);
                marker.setPopup(eventPopup);
            }
        }));
    } catch (error) {
        console.error(error);
    }
    return withinFiftyMilesIds;
};

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


//Comparing the Ids of the events within 50 miles radius.
async function localIdComparison() {
    let eventIds = await localLocations();
    console.log(typeof eventIds)
    let eventCards = document.querySelectorAll(".card-div");
    eventCards.forEach(function (card) {
        if (!eventIds.includes(parseInt(card.title))) {
            console.log("not included");
            card.style.display = "none";
        }else{
            console.log("included");
            card.style.display = "block";
        }
    });
}


//Comparing all the ids of events with the ones from API
function allIdcomparison() {
    let eventCards = document.querySelectorAll(".card-div");
    eventCards.forEach(function (card) {
        let eventIds = allEvents();
        if (!eventIds.includes(parseInt(card.title))) {
            card.style.display = "none";
        } else {
            card.style.display = "block";
        }
    });
}


//Filter on change can run either all events with maps, or events within 50 miles radius with events
const filter = document.getElementById('filter');
filter.addEventListener('change', function () {
    let map = document.getElementById('map');
    const selectedValue = filter.value;
    if (selectedValue === 'local') {
        localIdComparison();
        map.style.display = "none";
    } else {
        location.reload();
        allIdcomparison();
    }
});


//Listing button that show list of events when clicked on and hides map
const listBtn = document.getElementById('listing-btn');
const mapButton = document.getElementById('map-btn');
listBtn.addEventListener("click", function () {
    let map = document.getElementById('map');
    let eventCards = document.querySelectorAll(".card-div");
    const selectedValue = filter.value;
    eventCards.forEach(function (card) {
        if (selectedValue === 'local') {
            localIdComparison();
            map.style.display = "none";
            card.style.display = "block";
        } else if (selectedValue === 'all') {
            allEvents();
            map.style.display = "none";
            card.style.display = "block";
        }
    });
});


// Map button shows map on click and hides the list of events
mapButton.addEventListener("click", function () {
    let map = document.getElementById('map');
    let eventCards = document.querySelectorAll(".card-div");
    const selectedValue = filter.value;
    eventCards.forEach(function (card) {
        if (selectedValue === 'all') {
            allEvents();
            map.style.display = "block";
            card.style.display = "none";
        } else if (selectedValue === 'local') {
            localLocations();
            map.style.display = "block";
            card.style.display = "none";
        }
    });
});



const spinnerEl = document.getElementById('spinner');
const backgroundEl = document.getElementById('loading-background');


map.on('load', ()=> {
    loadingSpinner(false);

    map.addSource('addr', {
        type: 'vector',
        url: 'mapbox://mollymerp.openadd'

    });
});

function addLayerSpinner(){
    loadingSpinner(true);
    map.addLayer({
        id: 'test',
        source: 'addr',
        'source-layer':'openaddresses',
        type: 'circle',
        paint: {
            'circle-color':'pink'
        }
    });
    map.on('render', stopSpinner);
}

function stopSpinner (e) {
    if (e.target && e.target.loaded()) {
        loadingSpinner(false);
        map.off('render', stopSpinner)
    }
}
function loadingSpinner(on) {
    if (on) {
        spinnerEl.classList.add('loading');
        backgroundEl.classList.add('absolute');
        backgroundEl.classList.remove('none');
    } else {
        spinnerEl.classList.remove('loading');
        backgroundEl.classList.remove('absolute');
        backgroundEl.classList.add('none');
    }
}


