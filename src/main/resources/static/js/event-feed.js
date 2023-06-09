

// Get the current page URL

//
// (
//     async function () {


//Search Bar Toggle
const checkbox = document.getElementById('toggle-checkbox');
const text = document.getElementById('toggle-text');

const toggleSearch = (search, button) => {
    const searchBar = document.getElementById(search);
    const searchButton = document.getElementById(button);
    const close = document.getElementById('close');
    const searchInput = document.getElementById('search-input');

    // Create a new div element for the search results
    const searchResults = document.createElement('div');

    searchResults.classList.add('search-results');
    document.body.appendChild(searchResults); // Append to body instead of search bar

    searchButton.addEventListener('click', () => {
        searchBar.classList.toggle('w-[350px]');
        close.classList.toggle('opacity-1');
        if (searchBar.classList.contains('w-[350px]')) {
            searchInput.style.pointerEvents = 'initial';
            searchInput.style.opacity = '1';
            searchInput.style.transition = '1.4s';
        } else {
            searchInput.style.pointerEvents = 'none';
            searchInput.style.opacity = '0';
            searchInput.style.transition = '0.4s';
            // Remove the search results when the search bar is closed
            searchResults.innerHTML = '';
        }
    });

    searchInput.addEventListener('keyup', (e) => {
        const searchTerm = e.target.value;
        if (searchTerm === '') {
            // Clear search results if search term is empty
            searchResults.innerHTML = '';
            return;
        }
        fetch(`${window.location.protocol}//${window.location.host}/api/alluser?search=${searchTerm}`)
            .then((response) => response.json())
            .then((data) => {
                // Filter the data by username and first_name + last_name
                const filteredData = data.filter(user => {
                    const name = `${user.first_name} ${user.last_name}`;
                    return user.username.includes(searchTerm) || name.includes(searchTerm);
                });
                // Display the search results in the popup
                searchResults.innerHTML = '';
                if (filteredData.length > 0) { // Only display results if there are any
                    const inputRect = searchInput.getBoundingClientRect(); // Get position of search input
                    searchResults.style.top = inputRect.bottom + 'px'; // Set top and left properties to position popup
                    searchResults.style.left = inputRect.left + 'px';
                    searchResults.style.position = 'fixed'; // Set position to fixed
                    searchResults.style.zIndex = '999'; // Set high z-index
                    filteredData.forEach(user => {
                        const name = `${user.first_name} ${user.last_name}`;
                        const result = document.createElement('div');
                        result.classList.add('search-result');
                        result.innerHTML = `
                    <div class="bg-white dark:bg-[#26272d] p-3 w-[17em] h-[4.5em]" style="overflow: auto; z-index: 9999">
                        <div class="flex items-center justify-between">
                            <a href="/user/${user.id}">
                                <div class="flex items-center">
                                    <img src="${user.profilePhoto ? user.profilePhoto : '/img/profile.jpeg'}" alt="${name}" class="profile-photo rounded-full h-10 w-10 mr-2">
                                    <div>
                                               <div class="name text-black dark:text-white" th:text="${name} ? ${name} :'New User'"></div>

                                        <div class="username text-blue-600 font-bold">@${user.username}</div>
                                    </div> 
                                </div>
                            </a>
                            <a href="/users/${user.id}/follow-search" class="flex flex-row justify-self-end items-center group/edit group-hover/item:visible ">
                                <button class="items-center justify-center hover:cursor-pointer"
                                        type="submit">
                                  <svg xmlns="http://www.w3.org/2000/svg" width="19"
                                       height="19" fill="currentColor"
                                       class="bi bi-person-fill-add text-gray-300 transition duration-300 hover:text-gray-500" viewBox="0 0 16 16">
                                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0Zm-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
                                    <path d="M2 13c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Z"/>
                                  </svg>
                                </button>
                            </a>
                        </div>
                    </div>
                    `;
                        searchResults.appendChild(result);
                    });
                } else {
                    // Remove the search results if there are no filtered results
                    searchResults.innerHTML = '';
                }
            })
            .catch((error) => {
                console.error(error);
            });
    });
};
toggleSearch('search', 'search-button');


// Map and Listing Toggle
const listingBtn = document.getElementById('listing-btn');
const mapBtn = document.getElementById('map-btn');

mapBtn.addEventListener('click', () => {
    mapBtn.classList.add('bg-gray-200');
    mapBtn.classList.add('dark:bg-gray-700');
    listingBtn.classList.remove('bg-gray-200');
    listingBtn.classList.remove('dark:bg-gray-700');
});

listingBtn.addEventListener('click', () => {
    listingBtn.classList.add('bg-gray-200');
    listingBtn.classList.add('dark:bg-gray-700');
    mapBtn.classList.remove('bg-gray-200');
    mapBtn.classList.remove('dark:bg-gray-700');
});


//Modal div
let participantsList = document.getElementById("view-participants-list");
// Get all event buttons
let eventButtons = document.querySelectorAll(".view-participants-btn");

// Add a click event listener to each event button that fetches the participants for the event
eventButtons.forEach(button => {
    button.addEventListener("click", function() {
        participantsList.innerHTML = "";
        // Get the event ID from the "data-event-id" attribute of the button
        let eventId = button.getAttribute("data-event-id");

        // Fetch participants for the selected event
        fetch(`${window.location.protocol}//${window.location.host}/events/${eventId}/participants`, {
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
                <div class="flex w-full h-[6em] rounded-full">
                <div class="flex w-full">
                <div class="flex justify-between p-3 w-full" >
                  <div class="flex items-center ml-[1em]">
                  <img class="w-[50px] h-[50px] rounded-full mr-4" src="${viewParticipants[i].profilePhoto || '/img/profile.jpeg'}" alt="Profile image"/>
                  <div>
                    <p class="text-black dark:text-white">${viewParticipants[i].first_name ?? ''} ${viewParticipants[i].last_name ?? '' ? viewParticipants[i].last_name : ''} ${viewParticipants[i].first_name === null && viewParticipants[i].last_name === null ? 'User' : ''}</p>
                    <p class="text-blue-600">@${viewParticipants[i].username}</p></div>
                    
                    </div>
                   
                    <div class="flex w-1/3 items-center justify-evenly mr-3"> 
                   
                   <a href="/friends" class="hover:cursor-pointer"> <div class="flex justify-center items-center w-[40px]">
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-chat-fill transition duration-300 text-gray-300 hover:text-gray-500"  viewBox="0 0 16 16">
                      <path d="M8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6-.097 1.016-.417 2.13-.771 2.966-.079.186.074.394.273.362 2.256-.37 3.597-.938 4.18-1.234A9.06 9.06 0 0 0 8 15z"/>
                    </svg>
                    </div>
                    </a>
                    
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
//delete
function confirmDelete() {
    if (confirm("Are you sure you want to delete this event?")) {
        return true;
    } else {
        return false;
    }
}

function confirmCommentDelete() {
    if (confirm("Are you sure you want to delete this comment?")) {
        return true;
    } else {
        return false;
    }
}

function confirmUserDelete() {
    if (confirm("Are you sure you want to delete your profile?")) {
        document.getElementById("deleteForm").submit();
    }
}
    // })();



