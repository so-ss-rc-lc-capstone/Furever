




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
            html += `<div class="flex justify-center items-center w-full flex-col">
                <div class="flex w-full h-[6em] rounded-full" style="box-shadow: 0 4px 24px hsla(222,68%,20%, .1); overflow: hidden; transition: width .5s cubic-bezier(.9, 0, .3, .9)">
                <div class="flex w-full">
                <div class="flex justify-between p-3 w-full" >
                  <div class="flex items-center ml-[1em]">
                  <img class="w-[50px] h-[50px] rounded-full mr-4" src="${viewParticipants[i].profilePhoto || '/img/profile.jpeg'}" alt="Profile image"/>
                  <div>
                    <p>${viewParticipants[i].first_name ?? ''} ${viewParticipants[i].last_name ?? '' ? viewParticipants[i].last_name : ''} ${viewParticipants[i].first_name === null && viewParticipants[i].last_name === null ? 'User' : ''}</p>
                    <p class="text-gray-400">@${viewParticipants[i].username}</p></div>
                    
                    </div>
                   
                    <div class="flex w-1/3 items-center justify-evenly mr-3"> 
                   
                   <a class="hover:cursor-pointer"> <div class="flex justify-center items-center w-[40px]">
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-chat-fill transition duration-300 text-gray-300 hover:text-gray-500"  viewBox="0 0 16 16">
                      <path d="M8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6-.097 1.016-.417 2.13-.771 2.966-.079.186.074.394.273.362 2.256-.37 3.597-.938 4.18-1.234A9.06 9.06 0 0 0 8 15z"/>
                    </svg>
                    </div></a>
                    
                    
<!--                    <a class="hover:cursor-pointer"> <div class="flex justify-center items-center w-[40px]">-->
<!--                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi text-gray-300 transition duration-300 hover:text-gray-500 bi-person-plus-fill" viewBox="0 0 16 16">-->
<!--                      <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>-->
<!--                      <path fill-rule="evenodd" d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>-->
<!--                    </svg>-->
<!--                    </div>-->
<!--                    </a>-->
                    
                    <a class="hover:cursor-pointer" href="/user/${viewParticipants[i].id}">
                    <div class="flex justify-center items-center w-[40px]">
                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi text-gray-300 hover:text-gray-500 transition duration-300  bi-person-circle" viewBox="0 0 16 16">
                          <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                          <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                        </div>
                     </a>
                    </div>
                   
                    
                  </div>
                </div>
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
