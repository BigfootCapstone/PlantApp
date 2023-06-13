let reader = new FileReader();
reader.onload = function (r_event) {
    document.getElementById('profile-img').setAttribute('src', r_event.target.result);
}
document.getElementsByName('image')[0].addEventListener('change', function (event){
    reader.readAsDataURL(this.files[0]);
});
