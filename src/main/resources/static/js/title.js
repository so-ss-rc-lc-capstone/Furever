var currentPageUrl = window.location.href;

// Identify the page based on the URL
if (currentPageUrl.includes("profile")) {
    // Change the h1 header to "Profile Page"
    document.querySelector("h1").textContent = "Profile";
} else if (currentPageUrl.includes("/friends")) {
    // Change the h1 header to "Messaging Page"
    document.querySelector("h1").textContent = "Messaging";
} else if (currentPageUrl.includes("/find?event=")) {
    // Change the h1 header to "Event Details Page"
    document.querySelector("h1").textContent = "Event Details";
} else if (currentPageUrl.includes("posts")) {
    // Change the h1 header to "Posts Page"
    document.querySelector("h1").textContent = "Feed";
} else if (currentPageUrl.includes("/show?event=")) {
    // Change the h1 header to "View Posts Page"
    document.querySelector("h1").textContent = "View Posts Page";
} else if (currentPageUrl.includes("events")) {
    // Change the h1 header to "View Posts Page"
    document.querySelector("h1").textContent = "Events";
} else if (currentPageUrl.includes("profile/edit?")) {
    // Change the h1 header to "Event Details Page"
    document.querySelector("h1").textContent = "Edit Profile";
}