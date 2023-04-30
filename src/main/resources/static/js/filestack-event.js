$(document).ready(function () {
    let defaultImage = "/img/default-img.webp";
    const uploadBtn = $('#upload-btn');
    const input = $('#photo-input');
    const thumbnail = $('#thumbnail');

    //on edit
    const inputEditVal = $('#photo-edit');
    let eventInputValue = $('.input-edit-photo').val();
    const thumbnailEditEventPage = $('#thumbnail-eventEdit');

    //thumbnail function
    function showThumbnail(url) {
        if (thumbnail.attr('src') === '' || thumbnailEditEventPage.attr('src') === '' ) {
            thumbnail.src = url;
            thumbnailEditEventPage.src = url;
        } else {
            thumbnail.attr('src', url);
            thumbnailEditEventPage.attr('src', url);
        }
    }

    //thumbnail on edit
    function eventThumbnailsOnEdit() {
        if (eventInputValue.trim() === '') {
            thumbnailEditEventPage.attr('src', defaultImage);
        } else {
            thumbnailEditEventPage.attr('src', eventInputValue)
        }
    }

    const client = filestack.init(keys.filestack);
    uploadBtn.on('click', function () {
        client.picker({
            accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
            fromSources: ['local_file_system', 'url'], // Limit the upload sources
            onUploadDone: function (res) {
                const url = res.filesUploaded[0].url;
                input.attr('value', url);
                inputEditVal.attr('value', url);
                thumbnail.attr('src', url);
                showThumbnail(url);
                thumbnail.removeClass('hidden');
            }
        }).open();
    });
    showThumbnail(defaultImage);
    eventThumbnailsOnEdit();
});