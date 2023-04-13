const userCardTemplate = document.querySelector("[data-user-template]")
const userCardContainer = document.querySelector("[data-user-cards-container]")
const searchInput = document.querySelector("[data-search]")

let users = []

searchInput.addEventListener("input", e => {
    const value = e.target.value.toLowerCase()
    users.forEach(user => {
        const isVisible =
            user.username.toLowerCase().includes(value) ||
            user.first_name.toLowerCase().includes(value)||
            user.last_name.toLowerCase().includes(value)
        user.element.classList.toggle("hidden", !isVisible);
        user.element.classList.toggle("visible", isVisible);
    })
})

// fetch("https://jsonplaceholder.typicode.com/users")
// // fetch("https://localhost:8080/api/users")
//     .then(res => res.json())
//     .then(data => {
//         users = data.map(user => {
//             console.log(data);
//             const card = userCardTemplate.content.cloneNode(true).children[0]
//             const header = card.querySelector("[data-header]")
//             const body = card.querySelector("[data-body]")
//             header.textContent = user.name
//             body.textContent = user.email
//             userCardContainer.append(card)
//             console.log(user);
//             return { name: user.name, email: user.email, element: card }
//         })
//     })

fetch("http://localhost:8080/api/alluser", {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
        // "Access-Control-Allow-Origin": "http://localhost:63342",
    }
})
    .then(res => res.json())
    .then(data => {
        users = data.map(user => {
            const card = userCardTemplate.content.cloneNode(true).children[0]
            const header = card.querySelector("[data-header]")
            const body = card.querySelector("[data-body]")
            header.textContent = user.username
            body.innerHTML = `
                <p>First Name: ${user.first_name}</p>
                <p>Last Name: ${user.last_name}</p>
            `
            userCardContainer.append(card)
            return { username: user.username, first_name: user.first_name, last_name: user.last_name, element: card }
        })
    })
