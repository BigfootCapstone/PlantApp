let loadFile = function(event) {
    let output = document.getElementById('output');
    output.src = URL.createObjectURL(event.target.files[0]);
    output.onload = function() {
        URL.revokeObjectURL(output.src) // free memory
    }
};

let reader = new FileReader();
reader.onload = function (r_event) {
    document.getElementById('profile-img').setAttribute('src', r_event.target.result);
}
document.getElementsByName('image')[0].addEventListener('change', function (event){
    reader.readAsDataURL(this.files[0]);
});