window.addEventListener('load',()=>{
    const loader = document.querySelector('.loading');
    loader.classList.add('loading-hidden');
    loader.addEventListener('transitioned', ()=>{

        document.body.removeChild("loading");

    });
});