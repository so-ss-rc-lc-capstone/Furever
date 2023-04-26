const modal = document.getElementById('defaultModal');
const closeBtn = document.getElementById('close-btn');
const modalToggle = document.querySelectorAll('[data-modal-toggle]');
const modalClose = document.querySelectorAll('[data-modal-hide]');

modalToggle.forEach((toggle) => {
    toggle.addEventListener('click', () => {
        const target = toggle.getAttribute('data-modal-target');
        const modalTarget = document.getElementById(target);
        modalTarget.classList.toggle('hidden');
    });
});

modalClose.forEach((toggle) => {
    toggle.addEventListener('click', () => {
        const target = toggle.closest('.modal').getAttribute('id');
        const modalTarget = document.getElementById(target);
        modalTarget.classList.toggle('hidden');
    });
});

function hideModal() {
    document.getElementById("defaultModal").classList.add("hidden");
}


//Modal div
let participantsList = document.getElementById("view-participants-list");
let allBtns = document.querySelectorAll('.pt-3.pr-3 .view-participants-btn');

allBtns.forEach(event => {
    event.addEventListener('click', function() {
        participantsList.innerHTML = "";
        console.log("clicked");
        // Get the event ID from the "data-event-id" attribute of the button
        let eventId = event.getAttribute("data-event-id");
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
    participantsList.innerHTML = html;
}