$(document).ready(function () {
    let defaultImage = "/img/default-img.webp";

    let petUpload = $('#pet-upload');
    let petInput = $('#pet-image-input');
    let petThumbnail = $('#pet-thumbnail');
    //on edit
    let petEditThumbnail = $("#new-pet-thumbnail");
    let petInputVal = $("#new-pet-input");
    let petInputValue = $(".new-pet-input").val();

    function petThumbnailsOnEdit() {
        if (petInputValue.trim() === '') {
            petEditThumbnail.attr('src', defaultImage);
        } else {
            petEditThumbnail.attr('src', petInputValue)
        }
    }

    function petImageUpload(img) {
        if (petThumbnail.attr('src') === '') {
            petThumbnail.src = img;
        } else {
            petThumbnail.attr('src', img);
        }
    }


    const petClient = filestack.init(keys.filestack);
    petUpload.on('click', function () {
        petClient.picker({
            accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
            fromSources: ['local_file_system', 'url'], // Limit the upload sources
            onUploadDone: function (res) {
                const url = res.filesUploaded[0].url;
                petInput.attr('value', url);
                petImageUpload(url);
                petInputVal.attr('value', url);
                petEditThumbnail.attr('src', url);
            }
        }).open();
    });

    petImageUpload(defaultImage);
    petThumbnailsOnEdit();

});

