

const renderCarouselImages = (images, parent) =>{
    images.forEach(image =>{
        const element = document.createElement('div');
        if(image.id === 1){
            element.classList.add('slide', 'active');
        }else{
            element.classList.add('slide', 'right');
        }
        element.innerHTML= `
          <div class="column shrink movie-container">
           <div class="column shrink movie-container-left">
          <div class="column shrink align-center">
            <img src="" alt="" class="movie-poster">
          </div>
        </div>
`
    })
}












//CAROUSEL FUNCTIONALITY
let arrows = document.querySelectorAll('.arrow');
// carousel forEach
arrows.forEach(function(arrow){
    // start a direction variable
    let direction;
    let slideMask = document.querySelector('.slider-mask');
    let slidesContainer = document.querySelector('.slides');
    arrow.addEventListener('click', function(event){
        // if the arrow clicked has a class of left, set direction to left, otherwise set to right
        if (event.target.classList.contains('arrow-left')) {
            direction = 'left';
        } else {
            direction = 'right';
        }
        // get the active slide
        let activeSlide = slideMask.querySelector('.slide.active');
        // remove active class from active slide
        activeSlide.classList.remove('active');
        // if direction is left, get previous sibling, otherwise get next sibling and reassign to activeSlide
        if (direction === 'left') {
            activeSlide = activeSlide.previousElementSibling;
        } else {
            activeSlide = activeSlide.nextElementSibling;
        }
        //if activeSlide is null, we are at the end of the carousel
        if (!activeSlide) {
            //if direction is left, get last slide, otherwise get first slide
            activeSlide = direction === 'left' ? slidesContainer.lastElementChild : slidesContainer.firstElementChild;
        }
        //add active to the new active slide
        activeSlide.classList.add('active');
        // remove left and right classes from active slide
        activeSlide.classList.remove('left', 'right');

        let previousSibling = activeSlide.previousElementSibling;
        //get all previous element siblings
        let previousSlides = [];
        while (previousSibling) {
            previousSlides.unshift(previousSibling);
            previousSibling = previousSibling.previousElementSibling;
        }
        //loop through previous slides and add left class, remove right class
        previousSlides.forEach(function(slide){
            slide.classList.remove('right');
            slide.classList.add('left');
        });
        // get next sibling of active slide
        let nextSibling = activeSlide.nextElementSibling;
        // get all next element siblings
        let nextSlides = [];
        while (nextSibling) {
            nextSlides.push(nextSibling);
            nextSibling = nextSibling.nextElementSibling;
        }
        // loop through next slides and add right class, remove left class
        nextSlides.forEach(function(slide){
            slide.classList.remove('left');
            slide.classList.add('right');
        });
    });
    //MODAL POP UP FUNCTIONALITY
    // target the modal
    let modal = document.getElementById("myModal");
    // target modal button
    let btn = document.getElementById("myBtn");
    // target span that closes the modal
    let span = document.getElementsByClassName("close")[0];
    // display modal on click
    btn.onclick = function() {
        modal.style.display = "block";
    }
    // when X is clicked, close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }
    // click anywhere outside of modal close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    //MODAL FUNCTIONALITY END

});

