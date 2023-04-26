const likeContainer = document.querySelector('.like-container');

likeContainer.addEventListener('click', function() {
    likeContainer.classList.toggle('clicked');
});