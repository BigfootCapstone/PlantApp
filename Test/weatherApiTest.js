// User inputs city/state name into box, info retrieved


let button = document.querySelector('.button');
let inputValue = document.querySelector('.inputValue');
let name = document.querySelector('.name');
let temp = document.querySelector('.temp');
let desc = document.querySelector('.desc');
let humid = document.querySelector('.humid');
let rain = document.querySelector('.rain');

button.addEventListener('click', function (){
    fetch(`https://api.openweathermap.org/data/2.5/weather?q=${inputValue.value}&appid=${WEATHER_API_TOKEN}`).then(response => response.json()).then(data => {
        console.log(data)
        let nameValue = data['name'];
        let tempValue = data['main']['temp'];
        let descValue = data['weather'][0]['description'];
        let humidValue = data['main']['humidity'];
        // Rain???

        name.innerHTML = nameValue;
        temp.innerHTML = "Temperature: " + tempValue + " °F";
        desc.innerHTML = "Description: " + descValue;
        humid.innerHTML = "Humidity: " + humidValue + " %";
        rain.innerHTML = "Rain Chance: ";
    })

        .catch(error => alert("Wrong city name!"))
})


// OG link: `https://api.openweathermap.org/data/2.5/weather?q=${inputValue.value}&appid=${WEATHER_API_TOKEN}&units=imperial`
