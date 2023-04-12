const checkbox = document.getElementById('toggle-checkbox');
const text = document.getElementById('toggle-text');


checkbox.addEventListener('change', () => {
    if (checkbox.checked) {
        text.textContent = 'Listing';
    } else {
        text.textContent = 'Map';
    }
});



$(document).ready(function() {
    const button = $('#color-toggle');

    button.click(function() {
        if (button.hasClass('bg-white')) {
            button.removeClass('bg-white text-black');
            button.addClass('bg-gradient-to-r from-cyan-500 to-blue-500 hover:bg-gradient-to-bl focus:outline-none text-white');
            button.text('Attending');
        } else {
            button.removeClass('bg-gradient-to-r from-cyan-500 to-blue-500 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none text-white');
            button.addClass('bg-white text-black')
            button.text('Participate');
        }
    });
});