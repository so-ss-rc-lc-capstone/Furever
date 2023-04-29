$(document).ready(() => {

//AI link is being initiated here
    const aiAssistantLink = $('#use-ai');
    const aiAssistantModal = $('#ai-assistant');

//on click open up the AI assistant
    aiAssistantLink.on('click', () => {
        aiAssistantModal.fadeIn(1000);
    });

//Grabbing the mood from AI assistant select
    const mood = $('#mood');

//Grabbing description
    const userDescription = $('#ai-description');

//the close button closes AI assistant on click
    let closeAIAssistant = $('#close-btn');
    closeAIAssistant.on('click', () => {
        // aiAssistantModal.css('display', 'none');
        aiAssistantModal.fadeOut(1000);
    });

//Generate button
    const generate = $('#generate');

//Fetching AI API
    async function generateText(choosenMood, WrittenDescription) {
        try {
            const response = await fetch('https://open-api.herokuapp.com/generate-text', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "prompt": "Re-write me a " + choosenMood + "description for a dog event use the following content to write the summary:" + WrittenDescription,
                    "engine": "text-davinci-002",
                    "maxTokens": 200
                })
            });
            const data = await response.json();
            const responseData = JSON.stringify(data);
            console.log('here =>', responseData);
            return responseData;
        } catch (error) {
            console.error(error);
        }
    }

//Running the Fetch function with some details
    generate.on('click', async () => {
        const userDescriptionParse = userDescription.val();
        const moodParse = mood.val();
        const responseData = await generateText(moodParse, userDescriptionParse);

        const eventDescription = $('#description');
        responseData.substring(1, responseData.length - 1);
        eventDescription.val(responseData.substring(1, responseData.length - 1));
        aiAssistantModal.fadeOut(1000);
    });
});