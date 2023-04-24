$(document).ready(function () {
    let defaultImage = "/img/default-img.webp";
    const uploadBtn = $('#upload-btn');
    const input = $('#photo-input');
    const inputEdit = $('#photo-edit');
    const thumbnail = $('#thumbnail');
// const thumbnailEditEventPage = $('#thumbnail-eventEdit');

    //thumbnail function
    function showThumbnail(url) {
        if (thumbnail.attr('src') === '') {
            thumbnail.src = url;
        } else {
            thumbnail.attr('src', url);
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
                inputEdit.attr('value', url);
                showThumbnail(url);
            }
        }).open();
    });

    showThumbnail(defaultImage);
});