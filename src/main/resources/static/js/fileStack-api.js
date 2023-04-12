import {keys} from "./keys.js";

$(document).ready(function() {
    const uploadBtn = $('#upload-btn');
    const input = $('#photo-input');
    const inputEdit = $('#photo-edit');
    const client = filestack.init(keys.filestack);
    uploadBtn.on('click', function () {
        client.picker({
            accept: ['image/*', 'video/*'], // Allow only photos and videos to be uploaded
            fromSources: ['local_file_system', 'url'], // Limit the upload sources
            onUploadDone: function(res) {
                const url = res.filesUploaded[0].url;
                input.attr('value', url);
                inputEdit.attr('value', url);
            }
        }).open();
    });
});