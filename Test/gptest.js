import { Configuration, OpenAIApi} from "openai";

const configuration = new Configuration({
    organization: "org-zEo7FbMYDlcAINcYlH3JseJg",
    apiKey: "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",
});

const openai = new OpenAIApi(configuration);

const complpetion = await openai.createCompletion({
    model: "text-davinci-003",
    prompt: "Give me a guide on how to care for a strawberry plant.",
    max_tokens: 100,
    temperature: 0
});

console.log(complpetion.data.choices[0].text);