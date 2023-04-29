


if (currentPage.indexOf("/events") !== -1) {
    // Update the page title
    document.getElementById("page-title").innerHTML = "Events";
} else if (currentPage.indexOf("/profile") !== -1) {
    // Update the page title
    document.getElementById("page-title").innerHTML = "Profile";
} else if (currentPage.indexOf("/messaging") !== -1) {
    // Update the page title
    document.getElementById("page-title").innerHTML = "Messaging";
} else if (currentPage.indexOf("/posts") !== -1) {
    // Update the page title
    document.getElementById("page-title").innerHTML = "Posts";}


(function(t,a,l,k,j,s){
    s=a.createElement('script');s.async=1;s.src="https://cdn.talkjs.com/talk.js";a.head.appendChild(s)
    ;k=t.Promise;t.Talk={v:3,ready:{then:function(f){if(k)return new k(function(r,e){l.push([f,r,e])});l
                .push([f])},catch:function(){return k&&new k()},c:l}};})(window,document,[]);




//AI link is being initiated here
const postAILink = $('#post-ai');
const postAIModal = $('#post-ai-assistant');


//on click open up the AI assistant
postAILink.on('click', () => {
    postAIModal.fadeIn(1000);
});

const postMood = $('#post-mood');
const postDescription = $('#ai-post-description');


//the close button closes AI assistant on click
let closePostAI = $('#close-icon');

closePostAI.on('click', () => {
    postAIModal.fadeOut(1000);
});

//Generate button

//Fetching AI API
async function generateUserPost(choosenMood, WrittenDescription) {
    try {
        const response = await fetch('https://open-api.herokuapp.com/generate-text', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "prompt": "Re-write me a " + choosenMood + "description for my social media post. Use the following content to write the summary:" + WrittenDescription,
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
const generatePost = $('#post-generate');
generatePost.on('click', async () => {
    const postMoodParse = postMood.val();
    const postDescriptionParse = postDescription.val();
    const responseData = await generateUserPost(postMoodParse, postDescriptionParse);
    const userPost = $('#user-post');
    userPost.val(responseData.substring(1, responseData.length - 1));
    postAIModal.fadeOut(1000);
});


