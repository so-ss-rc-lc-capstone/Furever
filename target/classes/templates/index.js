    fetch('http://localhost:8080/api/users')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            // do something with the data, such as rendering it in the HTML
        })
        .catch(error => {
            console.error('Error:', error);
        });


    const checkbox = document.getElementById('toggle-checkbox');
    const text = document.getElementById('toggle-text');

    checkbox.addEventListener('change', () => {
        if (checkbox.checked) {
            text.textContent = 'Map';
        } else {
            text.textContent = 'Checked toggle';
        }
    });


