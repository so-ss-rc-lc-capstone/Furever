// mapboxgl.accessToken = keys.mapbox;
//
// const eventLoc = [
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
//
// // Initialize the map
// const map = new mapboxgl.Map({
//     container: 'map',
//     // style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6',
//     style: 'mapbox://styles/mapbox/dark-v11',
//     center: [-98.4916, 29.4252],
//     zoom: 10
// });
//
// // Add map controls
// map.addControl(new mapboxgl.NavigationControl());
//
// // Add markers for each event location
// eventLoc.forEach(event => {
//     // Get the coordinates for the event location
//     try {
//         geocode(event.address, mapboxgl.accessToken).then(result => {
//             // Create a marker at the coordinates
//             const marker = new mapboxgl.Marker()
//                 .setLngLat(result.features[0].center)
//                 .setPopup(new mapboxgl.Popup().setHTML(`<h3>${event.name}</h3><p>${event.address}</p>`))
//                 .addTo(map);
//         });
//     } catch (error) {
//         console.log(error);
//     }
// });


let favFoods=[
    {
        name: "Taco Blvd",
        address: "18360 Blanco Rd #116, San Antonio, TX 78258",
    },
    {
        name: "Chick fil a",
        address: "106 E Houston St, San Antonio, TX  78205",
    },
    {
        name: "Chama Gaucha",
        address: "18318 Sonterra Pl, San Antonio, TX  78258",
    }
];
mapboxgl.accessToken = keys.mapbox;
let map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v9',
    zoom: 13,
    center: [-98.4916, 29.4252]
});



favFoods.forEach(function(favFoods) {
    geocode(favFoods.address, keys.mapbox).then(function (result) {
        console.log(result[0]);
        console.log(result[1]);

        let marker = new mapboxgl.Marker()
            .setLngLat([result[0],result[1]])
            .addTo(map);

        let tacoPopup = new mapboxgl.Popup()
            .setHTML(favFoods.name);

        // map.setCenter(result);
        map.setZoom(10);

        marker.setPopup(tacoPopup);
    });
})