const apiKey = 'sk-HD1n647e568e98fbe1165';
const speciesId = '269338'; // Replace [SPECIES_ID] with the actual ID

const url = `https://perenual.com/api/species-list?page=1&key=${apiKey}&q=tomato`;
fetch(url)
    .then(response => response.json())
    .then(data => {
        // Access and display the data
        console.log(data); // Output the data to the console

    })
    .catch(error => {
        // Handle any errors
        console.error(error);
    });


