import { assets } from "../../assets/assets";
import "./main-bottom.css";

type Props = {
  prompt: string;
  setPrompt: (v: string) => void;
  onSend: () => void;
};

function MainBottom({ prompt, setPrompt, onSend }: Props) {
  return (
    <div className="main-bottom">
      <div className="search-box">
        <input
          type="text"
          placeholder="Enter a prompt"
          value={prompt}
          onChange={(e) => setPrompt(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              onSend();
            }
          }}
        />
        <div>
          <img src={assets.gallery_icon} alt="" />
          <img src={assets.mic_icon} alt="" />
          <img
            src={assets.send_icon}
            alt=""
            style={{ marginLeft: 8 }}
            onClick={() => {
              onSend();
            }}
          />
        </div>
      </div>
    </div>
  );
}
export default MainBottom;
