interface ITodo {
  name: string;
  description: string;
  status: boolean;
  _id: string;
  //可选属性
  createdAt?: string;
  updatedAt?: string;
}
//给组件传递的props类型
interface TodoProps {
  todo: ITodo;
}
type ApiDataType = {
  message: string;
  status: string;
  //有的接口（例如GET/todos）返回多个todo
  todos: ITodo[];
  //有的接口r(如POST /add-todo)返回单个todo
  todo?: ITodo;
};
