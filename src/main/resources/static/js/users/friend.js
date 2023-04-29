

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
                searchBar.classList.toggle('w-[235px]');
                close.classList.toggle('opacity-1');
                if (searchBar.classList.contains('w-[235px]')) {
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
                    <div class="user-div bg-white p-3 w-[12em] h-[4.5em]" style="overflow: auto; z-index: 9999">
                        
                            <div class="flex items-center">
                            <a href="/user/${user.id}">
                               <img src="${user.profilePhoto ? user.profilePhoto : '/img/profile.jpeg'}" alt="${name}" class="profile-photo rounded-full h-10 w-10 mr-2">
                                </a>
                                <div>
                                    <div class="username font-bold">@${user.username}</div>
                                    <div class="name" th:text="${name} ? ${name} :'New User'"></div>
                                </div>
                                <form action="/users/${user.id}/follow" name="followFromEvent" method="POST">
                                <button type="submit">Follow</button>
                                <div>
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