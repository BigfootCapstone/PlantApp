const { Configuration, OpenAIApi } = require("openai");

async function run() {
    const configuration = new Configuration({
        headers: {
            organization: "org-zEo7FbMYDlcAINcYlH3JseJg",
            Authorization: "Bearer sk-HD1n647e568e98fbe1165",
        },
    });

    const openai = new OpenAIApi(configuration);

    const response = await openai.createCompletion({
        model: "text-davinci-003",
        prompt: "Say this is a test",
        max_tokens: 7,
        temperature: 1,
    });

    console.log(response);
}

run().catch((error) => console.error(error));
