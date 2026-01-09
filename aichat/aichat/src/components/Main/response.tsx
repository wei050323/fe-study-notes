import "./response.css";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { useEffect, useRef } from "react";

type Msg = {
  role: "user" | "assistant";
  content: string;
};
type Props = {
  messages: Msg[];
  streamingReply: string;
  loading: boolean;
  error: string | null;
};
function Response({ messages, streamingReply, loading, error }: Props) {
  const replyRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    replyRef.current?.scrollIntoView({ behavior: "smooth", block: "end" });
  }, [messages, streamingReply]);
  const visibleMessages = messages.filter(
    (m) => !(m.role === "assistant" && !m.content)
  );
  return (
    <div className="response">
      {visibleMessages.map((m, i) => (
        <div
          key={i}
          className="bottom-info"
          ref={i === visibleMessages.length - 1 ? replyRef : null}
        >
          {m.role === "assistant" ? (
            <ReactMarkdown remarkPlugins={[remarkGfm]}>
              {m.content}
            </ReactMarkdown>
          ) : (
            <div>{m.content}</div>
          )}
        </div>
      ))}

      {error && (
        <p className="bottom-info" style={{ color: "red" }}>
          {error}
        </p>
      )}

      {streamingReply && !error && (
        <div className="bottom-info" ref={replyRef}>
          <ReactMarkdown remarkPlugins={[remarkGfm]}>
            {streamingReply}
          </ReactMarkdown>
        </div>
      )}

      {loading && <p className="bottom-info">正在生成...</p>}
    </div>
  );
}
export default Response;
