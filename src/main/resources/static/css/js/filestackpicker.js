
window.addEventListener('DOMContentLoaded', function () {

    var apiKey = [[${filestackKey}]];
    const options = {
        onFileUploadFinished: (file) => {
            console.log(file);
            const img = document.getElementById('profile-img');
            img.src = file.url;
        },
        accept: ["image/*"],
        transformations: {
            crop: true,
            circle: true
        },
    };

    const client = filestack.init(apiKey);
    const picker = client.picker(options);

    const openBtn = document.getElementById('open');
    openBtn.addEventListener('click',  (e) => {
        e.preventDefault();
        picker.open();
    });

})