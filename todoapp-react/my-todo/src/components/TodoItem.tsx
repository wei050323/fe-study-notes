import type { Todo } from "../types";

interface Props {
  todo: Todo;
  toogleTodo: (id: number) => void;
  removeTodo: (id: number) => void;
  priority: (id: number) => void;
}

function TodoItem({ todo, toogleTodo, removeTodo, priority }: Props) {
  const handleToogle = () => {
    toogleTodo(todo.id);
  };
  const handleRemove = () => {
    removeTodo(todo.id);
  };
  const handlePriority = () => {
    priority(todo.id);
  };
  return (
    <div className={todo.completed ? "todo-item completed" : "todo-item"}>
      <div className="todo-left">
        <input
          className="todo-checkbox"
          type="checkbox"
          onClick={handleToogle}
        />
        <span className="todo-text">{todo.text}</span>
      </div>
      <div className="todo-actions">
        <button onClick={handleRemove}>dele</button>
        <input type="checkbox" onClick={handlePriority} />
      </div>
    </div>
  );
}
export default TodoItem;
