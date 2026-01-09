import { assets } from "../../assets/assets";
import "./nav.css";

function nav() {
  return (
    <div className="nav">
      <p>Gemini</p>
      <img src={assets.user_icon} alt="" />
    </div>
  );
}
export default nav;
