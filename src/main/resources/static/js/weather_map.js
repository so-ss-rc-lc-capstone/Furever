mapboxgl.accessToken = keys.mapbox;
let userLocation = {
    longitude: 0,
    latitude: 0
};

navigator.geolocation.getCurrentPosition(successLocation, errorLocation, {enableHighAccuracy: true});

function successLocation(position) {
    userLocation.longitude = position.coords.longitude;
    userLocation.latitude = position.coords.latitude;
    setupMap([userLocation.longitude, userLocation.latitude]);
}

function errorLocation() {
    setupMap([-98.4916, 29.4252]);
}

function setupMap(center) {
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6',
        center: center,
        zoom: 10
    });

    const nav = new mapboxgl.NavigationControl();
    map.addControl(nav);

    var directions = new MapboxDirections({
        accessToken: mapboxgl.accessToken
    });

    map.addControl(directions, "top-left");

    // Example of how to access the user's location
    console.log("User's location: ", userLocation.longitude, userLocation.latitude);
}
