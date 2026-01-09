// TypeScript 声明文件
import type { Stream } from "openai/streaming";
import type { ChatCompletionChunk } from "openai/resources/chat/completions";

export function sendMessage(userMessage: string): Promise<Stream<ChatCompletionChunk>>;
