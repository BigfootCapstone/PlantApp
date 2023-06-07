
const fetch = require('node-fetch');

(async () => {
    const response = await fetch('https://trefle.io/api/v1/species/321?token=R9ZQAdQb2ONsTahrV0mG36COlvbszoCnxHg89PfBfRI');
    const json = await response.json();
    console.log(json);
})();




