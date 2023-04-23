

// $(document).ready(function() {
//     let defaultImage = "/img/default-img.webp";
//
//     let petUpload = $('#pet-upload');
//
//     let petInput = $('#pet-image-input');
//     let petThumbnail = $('#pet-thumbnail');
//     //on edit
//     let petEditThumbnail = $("#new-pet-thumbnail");
//     let petInputVal = $("#new-pet-input");
//     let petInputValue = $(".new-pet-input").val();
//     function petThumbnailsOnEdit() {
//         if (petInputValue.trim() === '') {
//             petEditThumbnail.attr('src', defaultImage);
//         } else {
//             petEditThumbnail.attr('src', petInputValue)
//         }
//     }
// petThumbnailsOnEdit();
//
// function petImageUpload(img) {
//     if (petThumbnail.attr('src') === '') {
//         petThumbnail.src = img;
//     } else {
//         petThumbnail.attr('src', img);
//     }
// }
// petImageUpload(defaultImage);
//
//     const petClient = filestack.init(keys.filestack);
//     petUpload.on('click', function () {
//         petClient.picker({
//             accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
//             fromSources: ['local_file_system', 'url'], // Limit the upload sources
//             onUploadDone: function (res) {
//                 const url = res.filesUploaded[0].url;
//                 petInput.attr('value', url);
//                 petImageUpload(url);
//                 petInputVal.attr('value', url);
//                 petEditThumbnail.attr('src', url);
//             }
//         }).open();
//     });
//


//
//     const uploadBtn = $('#upload-btn');
//     const input = $('#photo-input');
//     const inputEdit = $('#photo-edit');
//     const thumbnail = $('#thumbnail');
//     // const thumbnailEditEventPage = $('#thumbnail-eventEdit');
//
//
//
//
//     const client = filestack.init(keys.filestack);
//     uploadBtn.on('click', function () {
//         client.picker({
//             accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
//             fromSources: ['local_file_system', 'url'], // Limit the upload sources
//             onUploadDone: function (res) {
//                 const url = res.filesUploaded[0].url;
//                 input.attr('value', url);
//                 inputEdit.attr('value', url);
//                 showThumbnail(url);
//             }
//         }).open();
//     });
//
//
// //thumbnail function
//     function showThumbnail(url) {
//         if (thumbnail.attr('src') === '') {
//             thumbnail.src = url;
//         } else {
//             thumbnail.attr('src', url);
//         }
//     }
//
//     showThumbnail(defaultImage);
//
//     //User photo on edit
//     const uploadButton = $('#uploadUser');
//     let userInputThumbnail = $('#user-thumbnail');
//     let inputVal = $('#user-input');
//     let inputValue = $('.user-image').val();
//
//     function thumbnailsOnEdit() {
//         if (inputValue.trim() === '') {
//             userInputThumbnail.attr('src', defaultImage);
//         } else {
//             userInputThumbnail.attr('src', inputValue)
//         }
//     }
//
//     thumbnailsOnEdit();
//
//     const userClient = filestack.init(keys.filestack);
//     uploadButton.on('click', function () {
//         userClient.picker({
//             accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
//             fromSources: ['local_file_system', 'url'], // Limit the upload sources
//             onUploadDone: function (res) {
//                 const url = res.filesUploaded[0].url;
//                 inputVal.attr('value', url);
//                 userInputThumbnail.attr('src', url);
//             }
//         }).open();
//     });
//
//
// });


