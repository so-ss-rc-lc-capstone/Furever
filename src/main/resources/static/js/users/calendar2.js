(
    async function() {


    const response = await fetch(`${window.location.protocol}//${window.location.host}/api/allevents`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    });
    const data = await response.json();

    let eventDates = [];
    let eventMonths = [];
    let eventYears = [];

    for (let i =0; i<data.length; i++){

        let eventDate = new Date(data[i].event_DateAndTime);

        let eventMonth = eventDate.getMonth();
        eventMonths.push(eventMonth);

        let eventDay = eventDate.getDate();
        eventDates.push(eventDay)

        let eventYear = eventDate.getDate();
        eventYears.push(eventYear)
    }

    const daysTag = document.querySelector(".days"),
        currentDate = document.querySelector(".current-date"),
        prevNextIcon = document.querySelectorAll(".icons span");
    // getting new date, current year and month
    let date = new Date(),
        currYear = date.getFullYear(),
        currMonth = date.getMonth();
    // storing full name of all months in array
    const months = ["January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"];


    const renderCalendar = () => {
        let firstDayofMonth = new Date(currYear, currMonth, 1).getDay(), // getting first day of month
            lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate(), // getting last date of month
            lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay(), // getting last day of month
            lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate(); // getting last date of previous month
        let liTag = "";
        for (let i = firstDayofMonth; i > 0; i--) { // creating li of previous month last days
            liTag += `<li class="inactive">${lastDateofLastMonth - i + 1}</li>`;
        }

        for (let i = 1; i <= lastDateofMonth; i++) { // creating li of all days of current month
            // adding active class to li if the current day, month, and year matched

            // let isToday =
            //     i === date.getDate() &&
            //     currMonth === new Date().getMonth() &&
            //     currYear === new Date().getFullYear() ? "active" : "inactive";
            // liTag += `<li class="${isToday}">${i}</li>`;
            //

            let isEvent = "inactive";
            for (let j = 0; j < eventDates.length; j++) {
                if(i === eventDates[j] && currMonth === eventMonths[j] && i === date.getDate() && currMonth === date.getMonth() && currYear === date.getFullYear()){
                    isEvent = "dDay";
                } else if (i === eventDates[j] && currMonth === eventMonths[j]) {
                    isEvent = "active";
                } else if (i === date.getDate() && currMonth === date.getMonth() && currYear === date.getFullYear()) {
                    isEvent = "today";
                }
            }
            liTag += `<li class="${isEvent}">${i}<a href="/friends"></a></li>`;

        }
        for (let i = lastDayofMonth; i < 6; i++) { // creating li of next month first days
            liTag += `<li class="inactive">${i - lastDayofMonth + 1}</li>`
        }
        currentDate.innerText = `${months[currMonth]} ${currYear}`; // passing current mon and yr as currentDate text
        daysTag.innerHTML = liTag;
    }
    renderCalendar();
    prevNextIcon.forEach(icon => { // getting prev and next icons
        icon.addEventListener("click", () => { // adding click event on both icons
            // if clicked icon is previous icon then decrement current month by 1 else increment it by 1
            currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;
            if(currMonth < 0 || currMonth > 11) { // if current month is less than 0 or greater than 11
                // creating a new date of current year & month and pass it as date value
                date = new Date(currYear, currMonth, new Date().getDate());
                currYear = date.getFullYear(); // updating current year with new date year
                currMonth = date.getMonth(); // updating current month with new date month
            } else {
                date = new Date(); // pass the current date as date value
            }
            renderCalendar(); // calling renderCalendar function
        });
    });

})();




















