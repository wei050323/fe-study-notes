import SiderBar from "./components/Siderbar/SiderBar";
import Nav from "./components/Main/nav";
import MainContainer from "./components/Main/main-container";
import "./app.css";
import Response from "./components/Main/response";
import { useState, useEffect } from "react";
import { sendMessage } from "./config/deepseek";
import MainBottom from "./components/Main/main-bottom";

type Role = "user" | "assistant";
type Message = {
  role: Role;
  content: string;
  ts: number;
};
type Conversation = {
  id: string;
  title: string;
  messages: Message[];
  createdAt: number;
  updatedAt: number;
};

function App() {
  const [conversations, setConversations] = useState<Conversation[]>([]);
  const [activeId, setActiveId] = useState<string | null>(null);
  const [hasSent, setHasSent] = useState(false);
  const [prompt, setPrompt] = useState("");
  const [lastPrompt, setLastPrompt] = useState("");
  const [reply, setReply] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const raw = localStorage.getItem("AICHAT_CONVERSATIONS");
    if (!raw) return;
    try {
      const data: Conversation[] = JSON.parse(raw);
      setConversations(data);
      if (data.length > 0) {
        setActiveId(data[0].id);
      }
    } catch {}
  }, []);
  useEffect(() => {
    localStorage.setItem("AICHAT_CONVERSATIONS", JSON.stringify(conversations));
  }, [conversations]);
  const activeConversation =
    conversations.find((item) => item.id === activeId) || null;
  const messages = activeConversation?.messages || [];
  const ensureApiKey = () => {
    const existing = localStorage.getItem("DEEPSEEK_API_KEY");
    if (existing) return true;
    const key = window.prompt("请输入 DeepSeek API Key");
    if (key && key.trim()) {
      localStorage.setItem("DEEPSEEK_API_KEY", key.trim());
      return true;
    }
    return false;
  };

  const appendToConversations = (
    conversationId: string,
    updater: (c: Conversation) => Conversation
  ) => {
    setConversations((prev) =>
      prev.map((c) => (c.id === conversationId ? updater(c) : c))
    );
  };

  const handleNewChat = () => {
    const id = crypto.randomUUID();
    const now = Date.now();
    const newConv: Conversation = {
      id,
      title: "new chat",
      messages: [],
      createdAt: now,
      updatedAt: now,
    };
    setConversations((prev) => [...prev, newConv]);
    setActiveId(id);
    setHasSent(false);
    setLastPrompt("");
    setPrompt("");
    setReply("");
  };

  const handleSend = async () => {
    setError(null);
    const currentPrompt = prompt.trim();
    if (!currentPrompt) return;
    setLastPrompt(currentPrompt);

    let id = activeId;
    if (!id) {
      id = crypto.randomUUID();
      const now = Date.now();
      const newConv: Conversation = {
        id,
        title: currentPrompt.slice(0, 10),
        messages: [],
        createdAt: now,
        updatedAt: now,
      };
      setConversations((prev) => [...prev, newConv]);
      setActiveId(id);
    }
    const convID = id as string;
    appendToConversations(convID, (c) => {
      const hasMessages = c.messages.length > 0;
      const nextTitle =
        hasMessages && c.title !== "new chat"
          ? c.title
          : currentPrompt.slice(0, 10);
      return {
        ...c,
        title: nextTitle,
        messages: [
          ...c.messages,
          {
            role: "user",
            content: currentPrompt,
            ts: Date.now(),
          },
        ],
        updatedAt: Date.now(),
      };
    });
    if (!ensureApiKey()) {
      setError("未设置 API Key，已取消发送。");
      return;
    }
    setLoading(true);
    setPrompt("");
    setReply("");
    setHasSent(true);
    try {
      const stream = await sendMessage(currentPrompt);
      appendToConversations(convID, (c) => {
        return {
          ...c,
          messages: [
            ...c.messages,
            {
              role: "assistant",
              content: "",
              ts: Date.now(),
            },
          ],
          updatedAt: Date.now(),
        };
      });
      for await (const chunk of stream) {
        const delta =
          chunk?.choices?.[0]?.delta?.content ?? // 如果chunk存在choices[0].delta.content，就用它；这个流式返回的chunk中，choices[0].delta.content是增量内容
          ""; // 如果chunk不存在choices[0].delta.content，就用空字符串
        if (delta) setReply((prev: string) => prev + delta);
      }
      appendToConversations(convID, (c) => {
        const last = c.messages[c.messages.length - 1];
        if (!last || last.role !== "assistant") {
          return c;
        }
        const updatedLast: Message = {
          ...last,
          content: last.content + reply,
        };
        return {
          ...c,
          messages: [...c.messages.slice(0, -1), updatedLast],
          updatedAt: Date.now(),
        };
      });
    } catch (e: any) {
      setError(e?.message || "请求失败");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <SiderBar
        conversations={conversations}
        activeConvID={activeId}
        onNewChat={handleNewChat}
        onSelectChat={(id) => {
          setActiveId(id);
        }}
      />
      <div className="main">
        <Nav />
        {messages.length > 0 ? (
          <Response
            messages={messages}
            streamingReply={reply}
            loading={loading}
            error={error}
          />
        ) : (
          <MainContainer />
        )}
        <MainBottom prompt={prompt} setPrompt={setPrompt} onSend={handleSend} />
      </div>
    </div>
  );
}
export default App;
