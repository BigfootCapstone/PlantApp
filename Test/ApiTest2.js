const apiKey2 = 'sk-HD1n647e568e98fbe1165';
const speciesId2 = '269338'; // Replace [SPECIES_ID] with the actual ID

const url2 = `https://perenual.com/api/pest-disease-list?key=${apiKey2}`;
fetch(url2)
    .then(response => response.json())
    .then(data => {
        // Access and display the data
        console.log(data); // Output the data to the console

    })
    .catch(error => {
        // Handle any errors
        console.error(error);
    });
