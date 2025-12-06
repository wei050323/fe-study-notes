import { useState } from "react";

interface Props {
  addTodo: (text: string) => void;
}

function InputArea({ addTodo }: Props) {
  const [value, setvalue] = useState<string>("");

  //value.trim()把字符串前后空格去掉
  const handleAdd = () => {
    if (!value.trim()) return;
    addTodo(value.trim());
    setvalue("");
  };

  return (
    <div className="input-area">
      <input
        type="text"
        placeholder="Enter your thoughts..."
        value={value}
        onChange={(e) => setvalue(e.target.value)}
      />
      <button className="add-btn" onClick={handleAdd}>
        +
      </button>
    </div>
  );
}
export default InputArea;
