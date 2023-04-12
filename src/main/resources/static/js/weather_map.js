mapboxgl.accessToken = keys.mapbox;
let userLocation = {
    longitude: 0,
    latitude: 0
};
let destinations = [];

navigator.geolocation.getCurrentPosition(successLocation, errorLocation, {enableHighAccuracy: true});

function successLocation(position) {
    userLocation.longitude = position.coords.longitude;
    userLocation.latitude = position.coords.latitude;
    setupMap([userLocation.longitude, userLocation.latitude]);
    // store user location in an array
    // destinations.push([userLocation.longitude, userLocation.latitude]);
}


function errorLocation() {
    setupMap([-98.4916, 29.4252]);
}

function setupMap(center) {
    const map = new mapboxgl.Map({
        container: 'map',
        // style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6',
        style: 'mapbox://styles/mapbox/dark-v11',
        center: center,
        zoom: 10
    });

    const nav = new mapboxgl.NavigationControl();
    map.addControl(nav);

    var directions = new MapboxDirections({
        accessToken: mapboxgl.accessToken
    });


    map.addControl(directions, "top-left");




}
console.log(userLocation);




//the destinations array in the console is only showing the user location not the 2 inputs . the id of the inputs are id="mapbox-directions-origin-input" and id="mapbox-directions-destination-input"