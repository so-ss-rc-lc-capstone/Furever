$(document).ready(function () {
    let defaultImage = "/img/default-img.webp";
//User photo on edit
    const uploadButton = $('#uploadUser');
    let userInputThumbnail = $('#user-thumbnail');
    let inputVal = $('#user-input');
    let inputValue = $('.user-image').val();

    function thumbnailsOnEdit() {
        if (inputValue.trim() === '') {
            userInputThumbnail.attr('src', defaultImage);
        } else {
            userInputThumbnail.attr('src', inputValue)
        }
    }

    const userClient = filestack.init(keys.filestack);
    uploadButton.on('click', function () {
        userClient.picker({
            accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
            fromSources: ['local_file_system', 'url'], // Limit the upload sources
            onUploadDone: function (res) {
                const url = res.filesUploaded[0].url;
                inputVal.attr('value', url);
                userInputThumbnail.attr('src', url);
            }
        }).open();
    });
    thumbnailsOnEdit();
});
