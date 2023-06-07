const apiKey = PERENUAL_API_TOKEN;
const speciesId = '1'; // Replace [SPECIES_ID] with the actual ID

const url = `https://perenual.com/api/species/details/${speciesId}?key=${apiKey}`;
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


