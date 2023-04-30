document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();

        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

const startButton = document.querySelector(".start-button");

window.addEventListener("scroll", () => {
    const scrollPosition = window.scrollY;
    const halfViewportHeight = window.innerHeight / 2;
    const buttonOffsetTop = startButton.offsetTop;
    const buttonHeight = startButton.offsetHeight;

    if (scrollPosition > halfViewportHeight) {
        startButton.style.opacity = "1";
        startButton.style.transform = `translateY(-50px)`;
        startButton.style.transition = "opacity 0.5s ease-in-out, transform 0.5s ease-in-out";
        startButton.style.position = "fixed";
        startButton.style.bottom = "0";
    } else {
        startButton.style.opacity = "0";
        startButton.style.transform = `translateY(0)`;
        startButton.style.transition = "opacity 0.5s ease-in-out, transform 0.5s ease-in-out";
        startButton.style.position = "fixed";
        startButton.style.bottom = `calc(-${buttonHeight}px + 3em)`;
    }
});
//tilting image//
const img = document.getElementById('tilting-image');
const container = document.querySelector('.image-container');

const maxTilt = 10; // set the maximum tilt angle in degrees

document.addEventListener('mousemove', e => {
    const xAxis = (window.innerWidth / 2 - e.pageX) / (window.innerWidth / 2);
    const yAxis = (window.innerHeight / 2 - e.pageY) / (window.innerHeight / 2);
    const tiltX = Math.round(yAxis * maxTilt * 100) / 100; // round to 2 decimal places
    const tiltY = Math.round(xAxis * maxTilt * 100) / 100; // round to 2 decimal places
    img.style.transform = `perspective(1000px) rotateX(${tiltX}deg) rotateY(${tiltY}deg)`;
});

container.addEventListener('mouseleave', () => {
    img.style.transform = 'perspective(1000px) rotateX(0) rotateY(0)';
});



//Navbar scrolling effect
const navbar = document.getElementById('navbar');
const navbarContent = navbar.querySelector('.navbar-content');

function updateNavbar() {
    if (window.scrollY > 0) {
        navbar.classList.add('navbar-scrolled');
        navbar.classList.add('bg-white');
        navbar.classList.add('dark:bg-[#17181c]');
    } else {
        navbar.classList.remove('navbar-scrolled');
        navbar.classList.remove('bg-white');
        navbar.classList.remove('dark:bg-[#17181c]');
    }
}

window.addEventListener('scroll', updateNavbar);


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


