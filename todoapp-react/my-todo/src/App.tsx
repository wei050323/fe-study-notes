import { useState } from "react";
import type { Todo } from "./types";
import StatusBar from "./components/StatusBar";
import InputArea from "./components/InputArea";
import Header from "./components/Header";
import TodoList from "./components/TodoList";
import EmptyState from "./components/EmptyState";
import "./main.css";

function App() {
  const [totalcount, settotalcount] = useState<number>(0);
  const [todos, settodos] = useState<Todo[]>([]);

  const addTodo = (text: string) => {
    settotalcount((c) => c + 1);
    settodos([
      ...todos,
      {
        id: totalcount,
        text: text,
        completed: false,
        createdAt: new Date(),
        isimportant: false,
      },
    ]);
  };

  const priority = (id: number) => {
    settodos(
      todos.map((t) =>
        t.id === id ? { ...t, isimportant: !t.isimportant } : t
      )
    );
  };
  const toggleTodo = (id: number) => {
    settodos(
      todos.map((t) => (t.id === id ? { ...t, completed: !t.completed } : t))
    );
  };

  const removeTodo = (id: number) => {
    settodos(todos.filter((t) => t.id !== id));
  };

  const clearAll = () => {
    settodos([]);
  };

  return (
    <div className="container">
      <Header />
      <StatusBar
        left={todos.filter((t) => !t.completed).length}
        clearAll={clearAll}
      />
      <InputArea addTodo={addTodo} />
      {todos.length !== 0 ? (
        <TodoList
          todos={todos}
          toogleTodo={toggleTodo}
          removeTodo={removeTodo}
          priority={priority}
        />
      ) : (
        <EmptyState />
      )}
    </div>
  );
}

export default App;
