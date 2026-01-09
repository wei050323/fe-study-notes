import OpenAI from "openai";

function getApiKey() {
  const key = localStorage.getItem("DEEPSEEK_API_KEY");
  if (!key) {
    throw new Error("缺少 API 密钥。请先设置后再发送消息。");
  }
  return key;
}

function createClient() {
  const apiKey = getApiKey();
  return new OpenAI({
    apiKey,
    baseURL: "https://dashscope.aliyuncs.com/compatible-mode/v1",
    dangerouslyAllowBrowser: true,
  });
}

export async function sendMessage(userMessage) {
  const openai = createClient();
  const messages = [{ role: "user", content: userMessage }];
  const stream = await openai.chat.completions.create({
    model: "deepseek-r1-distill-qwen-7b",
    messages,
    stream: true,
  });
  return stream;
}
