let eventLoc=[
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
    // style: 'mapbox://styles/lukecal24/clg5hkzhf009x01mnuqxbp3y6',
    style: 'mapbox://styles/mapbox/dark-v11',
    zoom: 13,
    center: [-98.4916, 29.4252]
});



eventLoc.forEach(function(eventLoc) {
    geocode(eventLoc.address, keys.mapbox).then(function (result) {
        console.log(result[0]);
        console.log(result[1]);

        let marker = new mapboxgl.Marker()
            .setLngLat([result[0],result[1]])
            .addTo(map);

        let eventPopup = new mapboxgl.Popup()
            .setHTML(`<h3>${eventLoc.name}</h3><p>${eventLoc.address}</p>`);

        // map.setCenter(result);
        map.setZoom(10);

        marker.setPopup(eventPopup);
    });
})