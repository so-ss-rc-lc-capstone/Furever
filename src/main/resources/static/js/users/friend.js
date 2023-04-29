

(
    async function () {
        const response = await fetch(`${window.location.protocol}//${window.location.host}/api/alluser`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        });
        const data = await response.json();
// console.log(data);
        for (let i = 0; i < data.length; i++) {
            console.log("data:")
            console.log(data[i].id);
        }


        function findUser(userId) {

            for (let i = 0; i < data.length; i++) {
                if (data[i].id == userId) {
                    return data[i];
                }
            }
        }

        const followersDiv = document.getElementById('loggedUser');
        const notFollowingDiv = document.getElementById('not-follow');


        const exitBtn = document.getElementById('exit');
        const talkjsDiv = document.getElementById('talkjs');

        const allBtn = document.getElementById('all-btn');
        const followingBtn = document.getElementById('follow-btn');

        allBtn.addEventListener('click', () => {
            if (!allBtn.classList.contains('bg-gray-100') || !allBtn.classList.contains('text-blue-600')) {
                allBtn.classList.add('bg-gray-100', 'text-blue-600');
                followingBtn.classList.remove('bg-gray-100', 'text-blue-600');
                notFollowingDiv.style.display = 'flex';
                followersDiv.style.display = 'none';
            }
        });

        followingBtn.addEventListener('click', () => {
            if (!followingBtn.classList.contains('bg-gray-100') || !followingBtn.classList.contains('text-blue-600')) {
                followingBtn.classList.add('bg-gray-100', 'text-blue-600');
                allBtn.classList.remove('bg-gray-100', 'text-blue-600');
                followersDiv.style.display = 'flex';
                notFollowingDiv.style.display = 'none';
            }
        });


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
                    <div class="bg-white dark:bg-gray-800 p-3 w-[17em] h-[4.5em]" style="overflow: auto; z-index: 9999">
                        <div class="flex items-center justify-between">
                            <a href="/user/${user.id}">
                                <div class="flex items-center">
                                    <img src="${user.profilePhoto ? user.profilePhoto : '/img/profile.jpeg'}" alt="${name}" class="profile-photo rounded-full h-10 w-10 mr-2">
                                    <div>
                                        <div class="username font-bold">@${user.username}</div>
                                        <div class="name" th:text="${name} ? ${name} :'New User'"></div>
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




        exitBtn.addEventListener('click', () => {
            talkjsDiv.style.display = 'none';
        });


        talkjsDiv.addEventListener('click', () => {
            talkjsDiv.style.display = 'none';
        });



        const buttons = document.querySelectorAll(".all-users");
        buttons.forEach(function (button) {
            button.addEventListener("click", function () {
                talkjsDiv.style.display = 'block';
                let loggedUserId = document.getElementById("loggedUser").title;
                console.log("logged in user: " + loggedUserId)
                const buttonId = button.getAttribute("id");
                console.log(buttonId);

                let loggedUser = findUser(loggedUserId);
                let selectedUser = findUser(buttonId);

                console.log(loggedUser.username);
                console.log(selectedUser.username);

                Talk.ready.then(function () {
                    let defaultPhotoUrl = '/img/profile.jpeg';
                    let photoUrl = loggedUser.profilePhoto ? loggedUser.profilePhoto : defaultPhotoUrl;
                    let myPhotoUrl = selectedUser.profilePhoto ? selectedUser.profilePhoto : defaultPhotoUrl;
                    var me = new Talk.User({
                        id: loggedUser.id,
                        name: loggedUser.username,
                        email: loggedUser.email,
                        photoUrl: photoUrl,
                    });

                    window.talkSession = new Talk.Session({
                        appId: keys.talkjs,
                        me: me,
                    });
                    var other = new Talk.User({
                        id: selectedUser.id,
                        name: selectedUser.username,
                        email: selectedUser.email,
                        photoUrl: myPhotoUrl,
                    });

                    var conversation = talkSession.getOrCreateConversation(
                        Talk.oneOnOneId(me, other)
                    );
                    conversation.setParticipant(me);
                    conversation.setParticipant(other);
                    var inbox = talkSession.createInbox({selected: conversation});
                    inbox.mount(document.getElementById('talkjs-container'));
                });


            });
        });


        (function (t, a, l, k, j, s) {
            s = a.createElement('script');
            s.async = 1;
            s.src = "https://cdn.talkjs.com/talk.js";
            a.head.appendChild(s)
            ;k = t.Promise;
            t.Talk = {
                v: 2, ready: {
                    then: function (f) {
                        if (k) return new k(function (r, e) {
                            l.push([f, r, e])
                        });
                        l
                            .push([f])
                    }, catch: function () {
                        return k && new k()
                    }, c: l
                }
            };
        })(window, document, []);


    })();


const checkbox = document.getElementById('toggle-checkbox');
const text = document.getElementById('toggle-text');


function getUserInfo(userId) {
    $.ajax({
        url: "/user/" + userId + "/show",
        type: "GET",
        success: function (user) {
// populate user info into HTML elements on the page
            $("#user-name").text(user.username);
            $("#user-email").text(user.email);
// etc.
        }
    })


    checkbox.addEventListener('change', () => {
        if (checkbox.checked) {
            text.textContent = 'Listing';
        } else {
            text.textContent = 'Map';
        }
    })
}