const addressInput = document.getElementById("address");
const suggestionsDiv = document.getElementById("suggestions");
addressInput.addEventListener("input", () => {
    const address = addressInput.value.trim();
    if (address.length > 0) {
        fetch(`https://api.address-validator.com/suggestions?q=${address}`)
            .then(response => response.json())
            .then(data => {
                suggestionsDiv.innerHTML = "";
                data.forEach(suggestion => {
                    const suggestionDiv = document.createElement("div");
                    suggestionDiv.textContent = suggestion.address;
                    suggestionDiv.addEventListener("click", () => {
                        addressInput.value = suggestion.address;
                        suggestionsDiv.innerHTML = "";
                    });
                    suggestionsDiv.appendChild(suggestionDiv);
                });
            })
            .catch(error => console.error(error));
    } else {
        suggestionsDiv.innerHTML = "";
    }
});