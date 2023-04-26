document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();

        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

const track = document.getElementById("image-track");


const handleOnDown = e => track.dataset.mouseDownAt = e.clientX;

const handleOnUp = () => {
    track.dataset.mouseDownAt = "0";
    track.dataset.prevPercentage = track.dataset.percentage;
}

const handleOnMove = e => {
    if(track.dataset.mouseDownAt === "0") return;

    const mouseDelta = parseFloat(track.dataset.mouseDownAt) - e.clientX,
        maxDelta = window.innerWidth / 2;

    const percentage = (mouseDelta / maxDelta) * -100,
        nextPercentageUnconstrained = parseFloat(track.dataset.prevPercentage) + percentage,
        nextPercentage = Math.max(Math.min(nextPercentageUnconstrained, 0), -100);

    track.dataset.percentage = nextPercentage;

    track.animate({
        transform: `translate(${nextPercentage}%)`
    }, { duration: 1200, fill: "forwards" });

    for(const image of track.getElementsByClassName("image")) {
        image.animate({
            objectPosition: `${100 + nextPercentage}% center`
        }, { duration: 1200, fill: "forwards" });
    }
}

/* -- Had to add extra lines for touch events -- */

window.onmousedown = e => handleOnDown(e);

window.ontouchstart = e => handleOnDown(e.touches[0]);

window.onmouseup = e => handleOnUp(e);

window.ontouchend = e => handleOnUp(e.touches[0]);

window.onmousemove = e => handleOnMove(e);

window.ontouchmove = e => handleOnMove(e.touches[0]);

const blob = document.getElementById('blob');

function moveBlob(event) {
    const x = event.clientX;
    const y = event.clientY;

    blob.animate({
            left: blob.style.left = x + 'px',
            top: blob.style.top = y + 'px',
        },{duration:3000, fill: 'forwards'}

    )
}

document.addEventListener('mousemove', moveBlob);


const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('show')
        }
    });
});


const hiddenElements = document.querySelectorAll('.hiddens');
hiddenElements.forEach(element => {
    observer.observe(element);
});

const hiddenElementsUp = document.querySelectorAll('.hidden-up');
hiddenElementsUp.forEach(element => {
    observer.observe(element);
});
const hiddenElementsRight = document.querySelectorAll('.hidden-right');
hiddenElementsRight.forEach(element => {
    observer.observe(element);
});





const loginLink = document.getElementById('login-link');
loginLink.addEventListener('click', () => {
    const url = loginLink.dataset.url;
    window.location.href = url;
});

const logoutLink = document.getElementById('logout-link');
logoutLink.addEventListener('click', () => {
    const url = logoutLink.dataset.url;
    window.location.href = url;
});


// const anchors = document.querySelectorAll('a');
//
// anchors.forEach(anchor => {
//     anchor.addEventListener('click', (event) => {
//         event.preventDefault();
//         anchors.forEach(a => a.classList.remove('text-blue-700'));
//         anchor.classList.add('text-blue-700');
//         const id = anchor.getAttribute('href').substring(1);
//         const element = document.getElementById(id);
//         element.scrollIntoView({ behavior: 'smooth' });
//     });
// });


