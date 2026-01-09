import "./SiderBar.css";
import { assets } from "../../assets/assets";
import { useState } from "react";
type Conversation = {
  id: string;
  title: string;
};

type Props = {
  conversations: Conversation[];
  activeConvID: string | null;
  onNewChat: () => void;
  onSelectChat: (convID: string) => void;
};
function SiderBar({
  conversations,
  activeConvID,
  onNewChat,
  onSelectChat,
}: Props) {
  const [extended, setExtended] = useState(true);

  const toggleExtended = () => {
    setExtended(!extended);
  };

  return (
    <div className="sidebar">
      <div className="top">
        <img
          className="menu"
          src={assets.menu_icon}
          alt=""
          onClick={toggleExtended}
        />
        <div className="new-chat" onClick={onNewChat}>
          <img src={assets.plus_icon} alt="" />
          {extended ? <p>New Chat</p> : null}
        </div>
        {extended ? (
          <div className="recent">
            <p className="recent-title">Recent</p>
            {conversations.map((conv) => (
              <div
                className={`recent-entry ${
                  activeConvID === conv.id ? "active" : ""
                }`}
                key={conv.id}
                onClick={() => onSelectChat(conv.id)}
              >
                <img src={assets.message_icon} alt="" />
                <p>{conv.title}</p>
              </div>
            ))}
          </div>
        ) : null}
      </div>
      <div className="bottom">
        <div className="bottom-item recent-entry">
          <img src={assets.question_icon} alt="" />
          {extended ? <p>Help</p> : null}
        </div>
        <div className="bottom-item recent-entry">
          <img src={assets.history_icon} alt="" />
          {extended ? <p>Activity</p> : null}
        </div>
        <div className="bottom-item recent-entry">
          <img src={assets.setting_icon} alt="" className="" />
          {extended ? <p>Settings</p> : null}
        </div>
      </div>
    </div>
  );
}
export default SiderBar;
