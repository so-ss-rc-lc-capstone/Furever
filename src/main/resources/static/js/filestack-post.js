$(document).ready(function () {
    let defaultImage = "/img/default-img.webp";
    const postUpload = $('#post-photoBtn');
    const postInput = $('#post-photo-input');
    const  postThumbnail = $('#post-thumbnail');

    //thumbnail function
    function showPostThumbnail(url) {
        if (postThumbnail.attr('src') === '') {
            postThumbnail.attr('src', url);
        } else {
            postThumbnail.attr('src', url);
        }
    }
//

    const postClient = filestack.init(keys.filestack);
    postUpload.on('click', function () {

        postClient.picker({
            accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
            fromSources: ['local_file_system', 'url'], // Limit the upload sources
            onUploadDone: function (res) {
                const url = res.filesUploaded[0].url;
                postInput.attr('value', url);
                postThumbnail.attr('src', url);

            }
        }).open();
    });
    showPostThumbnail(defaultImage);
});