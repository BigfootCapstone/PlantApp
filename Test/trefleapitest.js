
const fetch = require('node-fetch');

(async () => {
    const response = await fetch('https://trefle.io/api/v1/species/122772?token=${TREFLE_API_TOKEN}');
    const json = await response.json();
    console.log(json);
})();




