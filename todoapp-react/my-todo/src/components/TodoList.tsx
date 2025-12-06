import TodoItem from "./TodoItem";
import type { Todo } from "../types";

interface Props {
  todos: Todo[];
  toogleTodo: (id: number) => void;
  removeTodo: (id: number) => void;
  priority: (id: number) => void;
}

function TodoList({ todos, toogleTodo, removeTodo, priority }: Props) {
  return (
    <div className="todo-list">
      {todos.map((t) => (
        <TodoItem
          key={t.id}
          todo={t}
          toogleTodo={toogleTodo}
          removeTodo={removeTodo}
          priority={priority}
        />
      ))}
    </div>
  );
}
export default TodoList;
