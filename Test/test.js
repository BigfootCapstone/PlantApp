const fetch = require('node-fetch');
const readline = require('readline');

// Function to perform the search
async function searchPlants(query) {
    const response = await fetch(`https://trefle.io/api/v1/plants/search?token=R9ZQAdQb2ONsTahrV0mG36COlvbszoCnxHg89PfBfRI&q=${encodeURIComponent(query)}`);
    const data = await response.json();

    // Extract the array of plant objects from the response
    const plants = data.data;

    // Iterate over each plant object and extract the relevant information
    const plantList = plants.map(plant => {
        return {
            id: plant.id,
            scientificName: plant.scientific_name,
            commonName: plant.common_name,
            family: plant.family,
            genus: plant.genus
            // Add more properties as needed
        };
    });

    return plantList;
}

// Function to prompt the user for a search query
function promptSearchQuery() {
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });

    return new Promise(resolve => {
        rl.question('Enter a search query: ', query => {
            rl.close();
            resolve(query);
        });
    });
}

// Main function
(async () => {
    const query = await promptSearchQuery();
    const plantList = await searchPlants(query);

    console.log('Search results:');
    console.log(plantList);
})();
