$(document).ready(function () {
    let defaultImage = "/img/default-img.webp";
    const postUpload = $('#post-photoBtn');
    const postInput = $('#post-photo-input');
//     const inputEdit = $('#photo-edit');

    const  postThumbnail = $('#post-thumbnail');
//
//     //thumbnail function
//     function showThumbnail(url) {
//         if (thumbnail.attr('src') === '') {
//             thumbnail.src = url;
//         } else {
//             thumbnail.attr('src', url);
//         }breeds
//     }
//


    const postClient = filestack.init(keys.filestack);
    postUpload.on('click', function () {

        postClient.picker({
            accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
            fromSources: ['local_file_system', 'url'], // Limit the upload sources
            onUploadDone: function (res) {
                const url = res.filesUploaded[0].url;
                postInput.attr('value', url);
                postThumbnail.attr('value', url);
            }
        }).open();
    });
//
//     showThumbnail(defaultImage);
});