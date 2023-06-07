
const fetch = require('node-fetch');

(async () => {
    const response = await fetch('https://trefle.io/api/v1/species/321?token=');
    const json = await response.json();
    console.log(json);
})();




