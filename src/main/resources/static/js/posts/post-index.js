



(function(t,a,l,k,j,s){
    s=a.createElement('script');s.async=1;s.src="https://cdn.talkjs.com/talk.js";a.head.appendChild(s)
    ;k=t.Promise;t.Talk={v:3,ready:{then:function(f){if(k)return new k(function(r,e){l.push([f,r,e])});l
                .push([f])},catch:function(){return k&&new k()},c:l}};})(window,document,[]);




const closeButton = document.getElementById('close-icon');
const aiLink = document.getElementById('ai');
const modal1 = document.getElementById('ai-assistant');

closeButton.addEventListener('click', () => {
    modal1.style.display = 'none';
});

aiLink.addEventListener('click', () => {
    modal1.style.display = 'block';
});

// add event listener to close modal when clicking outside of it
window.addEventListener('click', (event) => {
    if (event.target == modal1) {
        modal1.style.display = 'none';
    }
});



const mood = document.getElementById('mood');
const description = document.getElementById('chat');
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
            "prompt": "Write me a "+ mood + "tweet use the following content to write the summary:" + description,
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
    modal1.style.display = 'none';


});