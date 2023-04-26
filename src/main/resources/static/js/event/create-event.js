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


const mood = document.getElementById('mood');
const moodParse = mood.value;
const description = document.getElementById('description');
let descriptionParse = description.value; // data would go here
//button
const generate = document.getElementById('generate');
let responseData;

//GPT
function generateText(mood , description) {
    fetch('https://open-api.herokuapp.com/generate-text', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "prompt": "Re-write me a "+ mood + "description for a dog event use the following content to write the summary:" + description,
            "engine": "text-davinci-002",
            "maxTokens": 200
        })
    })
        .then(response => response.json())
        .then(data =>  {
            responseData =  JSON.stringify(data)
            console.log('here =>',responseData)
        })
        .catch(error => console.error(error));
}

generate.addEventListener('click', () => {

    const aiDescription = document.getElementById('ai-description').value;
    const selectedOption = mood.options[mood.selectedIndex];
    const selectedValue = selectedOption.value;
    console.log(aiDescription)
    console.log(selectedValue)

    generateText(selectedValue, aiDescription);
    setTimeout(function () {
        description.value = responseData.substring(1, responseData.length - 1);
    }, 3000);
    console.log(responseData)
    modal.style.display = 'none';


});