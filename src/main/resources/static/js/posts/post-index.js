

(function(t,a,l,k,j,s){
    s=a.createElement('script');s.async=1;s.src="https://cdn.talkjs.com/talk.js";a.head.appendChild(s)
    ;k=t.Promise;t.Talk={v:3,ready:{then:function(f){if(k)return new k(function(r,e){l.push([f,r,e])});l
                .push([f])},catch:function(){return k&&new k()},c:l}};})(window,document,[]);




//AI link is being initiated here
const postAILink = $('#post-ai');
const postAIModal = $('#post-ai-assistant');


//on click open up the AI assistant
postAILink.on('click', () => {
    postAIModal.fadeIn(500);
});

const postMood = $('#post-mood');
const postDescription = $('#ai-post-description');


//the close button closes AI assistant on click
let closePostAI = $('#close-icon');

closePostAI.on('click', () => {
    postAIModal.fadeOut(500);
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
        return responseData;
    } catch (error) {
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
    postAIModal.fadeOut(500);
});


function confirmCommentDelete() {
    if (confirm("Are you sure you want to delete this comment?")) {
        return true;
    } else {
        return false;
    }
}



