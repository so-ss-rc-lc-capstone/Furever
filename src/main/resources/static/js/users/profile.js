(
    async function () {

const feedBtn = document.getElementById("feed");
const eventsBtn = document.getElementById("events");

const postsContainer = document.getElementById("posts-container");
const eventsContainer = document.getElementById("events-container")

feedBtn.addEventListener('click', () => {
    feedBtn.classList.add('bg-gray-100');
    feedBtn.classList.add('dark:bg-gray-700')
    eventsBtn.classList.remove('bg-gray-100');
    eventsBtn.classList.remove('dark:bg-gray-700')
    postsContainer.style.display = 'block';
    eventsContainer.style.display = 'none';
});

eventsBtn.addEventListener('click', () => {
    eventsBtn.classList.add('bg-gray-100');
    eventsBtn.classList.add('dark:bg-gray-700')
    feedBtn.classList.remove('bg-gray-100');
    feedBtn.classList.remove('dark:bg-gray-700')
    postsContainer.style.display = 'none';
    eventsContainer.style.display = 'block';
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

        function confirmUserDelete() {
            if (confirm("Are you sure you want to delete your profile?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    })();
