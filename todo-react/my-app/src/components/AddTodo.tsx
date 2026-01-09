import React, { useState } from "react";
type Props = {
  saveTodo: (e: React.FormEvent, FormData: ITodo | any) => void;
};

const AddTodo: React.FC<Props> = ({ saveTodo }) => {
  const [FormData, setFormData] = useState<ITodo | {}>();
  const handleForm = (e: React.FormEvent<HTMLInputElement>): void => {
    setFormData({
      ...FormData,
      [e.currentTarget.name]: e.currentTarget.value,
    });
  };
  return (
    <form className="Form" onSubmit={(e) => saveTodo(e, FormData)}>
      <div>
        <div>
          <label htmlFor="name">Name</label>
          <input type="text" onChange={handleForm} id="name" />
        </div>
        <div>
          <label htmlFor="description">Description</label>
          <input type="text" onChange={handleForm} id="description" />
        </div>
      </div>
      <button disabled={FormData === undefined ? true : false}>Add Todo</button>
    </form>
  );
};

export default AddTodo;
