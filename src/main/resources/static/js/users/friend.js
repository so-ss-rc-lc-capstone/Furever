(
    async function () {
        const response = await fetch('http://localhost:8080/api/alluser', {
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

        const buttons = document.querySelectorAll(".all-users");
        buttons.forEach(function (button) {
            button.addEventListener("click", function () {
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