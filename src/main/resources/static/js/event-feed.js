




//Search Bar Toggle
const checkbox = document.getElementById('toggle-checkbox');
const text = document.getElementById('toggle-text');
const toggleSearch = (search, button) =>{
    const searchBar = document.getElementById(search),
        searchButton = document.getElementById(button),
        close = document.getElementById('close'),
        searchInput= document.getElementById('search-input');

    searchButton.addEventListener('click', () => {
        searchBar.classList.toggle('w-[350px]');
        close.classList.toggle("opacity-1");
        if (searchBar.classList.contains('w-[350px]')) {
            searchInput.style.pointerEvents = "initial";
            searchInput.style.opacity = "1";
            searchInput.style.transition ="1.4s";
        } else {
            searchInput.style.pointerEvents = "none";
            searchInput.style.opacity = "0";
            searchInput.style.transition ="0.4s";
        }
    });
};

toggleSearch('search', 'search-button');


// Map and Listing Toggle
const listingBtn = document.getElementById('listing-btn');
const mapBtn = document.getElementById('map-btn');

mapBtn.addEventListener('click', () => {
    mapBtn.classList.add('bg-gray-200');
    listingBtn.classList.remove('bg-gray-200');
});

listingBtn.addEventListener('click', () => {
    listingBtn.classList.add('bg-gray-200');
    mapBtn.classList.remove('bg-gray-200');
});


//Modal div
let participantsList = document.getElementById("view-participants-list");
// Get all event buttons
let eventButtons = document.querySelectorAll(".view-participants-btn");

// Add a click event listener to each event button that fetches the participants for the event
eventButtons.forEach(button => {
    button.addEventListener("click", function() {
        participantsList.innerHTML = "";
        console.log("clicked")
        // Get the event ID from the "data-event-id" attribute of the button
        let eventId = button.getAttribute("data-event-id");
        console.log(eventId);

        // Fetch participants for the selected event
        fetch('http://localhost:8080/events/' + eventId + '/participants', {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("Network response was not ok");
                }
                return res.json();
            })
            .then(data => {
                console.log(data);
                let viewParticipants = JSON.stringify(data);
                getAllParticipants(viewParticipants);
            })
            .catch(error => console.error(error));
    });
});

function getAllParticipants(data){
    let viewParticipants = JSON.parse(data);
    let html = '';
    if (Array.isArray(viewParticipants) && viewParticipants.length > 0) {
        for(let i = 0; i < viewParticipants.length; i++){
            html += `<div class="flex flex-col">
              <div class="flex border-b border-gray-200 hover:cursor-pointer">
                <a class="flex pb-5">
                  <img class="w-[50px] h-[50px] rounded-full mr-4" src="${viewParticipants[i].profilePhoto || '/img/profile.jpeg'}" alt="Profile image"/>
                  <div class="flex flex-col">
                    <p>${viewParticipants[i].first_name} ${viewParticipants[i].last_name}</p>
                    <p>@${viewParticipants[i].username}</p>
                  </div>
                </a>
              </div>
            </div>`;
        }
    } else {
        html += `<div class="flex flex-col">
          <div class="flex hover:cursor-pointer">
           <h2>No Participants</h2>
          </div>
        </div>`;
    }
    participantsList.innerHTML = html;
}
