const closeButton = document.getElementById('close');
const modal = document.getElementById('ai-assistant');
const ai = document.getElementById('ai');

closeButton.addEventListener('click', () => {
    modal.style.display = 'none';
});

const aiLink = document.getElementById('ai');
const modal1 = document.getElementById('ai-assistant');

aiLink.addEventListener('click', () => {
    modal1.style.display = 'block';
});