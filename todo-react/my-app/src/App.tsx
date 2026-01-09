import React, { use } from "react";
import { useState, useEffect } from "react";
import AddTodo from "./components/AddTodo";
import TodoItem from "./components/TodoItem";
import { getTodos, addTodo, updateTodo, deleteTodo } from "./API";
import { get } from "http";
import exp from "constants";

const App: React.FC = () => {
  const [todos, setTodos] = useState<ITodo[]>([]);
  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = (): void => {
    getTodos()
      .then(({ data: { todos } }: ITodo | any) => setTodos(todos))
      .catch((error: Error) => {
        console.log(error);
      });
  };

  const handlesaveTodo = (e: React.FormEvent, formData: ITodo | any): void => {
    e.preventDefault();
    addTodo(formData)
      .then(({ status, data }) => {
        if (status !== 200) {
          throw new Error(`Error!Todo not saved`);
        }
        setTodos(data.todos);
      })
      .catch((error: Error) => {
        console.log(error);
      });
  };

  const handleUpdateTodo = (todo: ITodo): void => {
    updateTodo(todo)
      .then(({ status, data }) => {
        if (status !== 200) {
          throw new Error(`Error!Todo not updated`);
        }
        setTodos(data.todos);
      })
      .catch((error: Error) => {
        console.log(error);
      });
  };

  const handelDeleteTodo = (_id: string): void => {
    deleteTodo(_id)
      .then(({ status, data }) => {
        if (status !== 200) {
          throw new Error(`Error!Todo not deleted`);
        }
        setTodos(data.todos);
      })
      .catch((error: Error) => {
        console.log(error);
      });
  };
  return (
    <main className="App">
      <AddTodo saveTodo={handlesaveTodo} />
      {todos.map((todo: ITodo) => (
        <TodoItem
          key={todo._id}
          updateTodo={handleUpdateTodo}
          deleteTodo={handelDeleteTodo}
          todo={todo}
        />
      ))}
    </main>
  );
};
export default App;
