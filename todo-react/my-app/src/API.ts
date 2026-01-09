import axios, { AxiosResponse } from "axios";
const baseURL = "https://localhost:4000";

export const getTodos = async (): Promise<AxiosResponse<ApiDataType>> => {
  try {
    const todos: AxiosResponse<ApiDataType> = await axios.get(
      baseURL + `/todos`
    );
    return todos;
  } catch (error) {
    if (error instanceof Error) {
      throw error;
    }
    throw new Error(String(error));
  }
};

export const addTodo = async (
  formData: ITodo
): Promise<AxiosResponse<ApiDataType>> => {
  try {
    const todo: Omit<ITodo, "_id"> = {
      name: formData.name,
      description: formData.description,
      status: false,
    };
    const savedTodo: AxiosResponse<ApiDataType> = await axios.post(
      baseURL + `/add-todo`,
      todo
    );
    return savedTodo;
  } catch (error) {
    throw new Error(String(error));
  }
};

export const updateTodo = async (
  todo: ITodo
): Promise<AxiosResponse<ApiDataType>> => {
  try {
    const todoUpdate: Pick<ITodo, "status"> = {
      status: true,
    };
    const updateTodo: AxiosResponse<ApiDataType> = await axios.put(
      baseURL + `/update-todo/${todo._id}`,
      todo
    );
    return updateTodo;
  } catch (error) {
    throw new Error(String(error));
  }
};

export const deleteTodo = async (
  _id: string
): Promise<AxiosResponse<ApiDataType>> => {
  try {
    const deleteTodo: AxiosResponse<ApiDataType> = await axios.delete(
      baseURL + `/delete-todo/${_id}`
    );
    return deleteTodo;
  } catch (error) {
    throw new Error(String(error));
  }
};
