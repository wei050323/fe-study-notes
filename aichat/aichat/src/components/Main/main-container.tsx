import "./main-container.css";
import Greet from "./greet";
import Cards from "./cards";

export default function MainContainer() {
  return (
    <div className="main-container">
      <div className="main-content">
        <Greet />
        <Cards />
      </div>
    </div>
  );
}
