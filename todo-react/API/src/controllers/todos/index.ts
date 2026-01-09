import { Response, Request } from "express";
import { ITodo } from "./../../types/todo";
import Todo from "./../../models/todo";
//获取
//async：声明getTodos函数是异步的，内部可以用await。返回值是一个Promise
//(req: Request, res: Response)：这是 Express 处理器的标准参数，req 是请求对象，res 是响应对象。函数把它们的类型声明为 express 的 Request 和 Response（TypeScript 类型），方便代码提示与静态检查。
//:Promise<void>：声明函数返回类型为`Promise<void>`
const getTodos = async (req: Request, res: Response): Promise<void> => {
  //try:用来捕获异步操作中抛出的异常
  try {
    const todos: ITodo[] = await Todo.find();
    //`res.status(200)`设置HTTP的状态码为200(OK)
    //`.json({todos})`把一个对象序列化为JSON返回给客户端。
    res.status(200).json({ todos });
  } catch (error) {
    throw error;
  }
};

//新增
const addTodos = async (req: Request, res: Response): Promise<void> => {
  try {
    //req.body:来自客户端的请求体，通常由express.json()中间件解析成对象
    //as.Pick<ITodo,···>:ts类型断言。Pick从ITodo接口中挑选出name、description、status三个字段作为body的预期类型。
    const body = req.body as Pick<ITodo, "name" | "description" | "status">;
    const todo: ITodo = new Todo({
      name: body.name,
      description: body.description,
      status: body.status,
    });
    const newTodo: ITodo = await todo.save();
    const allTodos: ITodo[] = await Todo.find();
    res
      .status(201)
      .json({ message: "Todo added", todo: newTodo, todos: allTodos });
  } catch (error) {
    throw error;
  }
};

//更新
const updatedTodos = async (req: Request, res: Response): Promise<void> => {
  try {
    //从req中解构出params和body
    //相当于下面的两行：
    //const id = req.params.id
    //const body = req.body
    const {
      params: { id },
      body,
    } = req;
    //findByIdAndUpdate:根据id查找并更新对应的文档
    const updateTodo: ITodo | null = await Todo.findByIdAndUpdate(
      { _id: id },
      body
    );
    const allTodos: ITodo[] = await Todo.find();
    res
      .status(200)
      .json({ message: "Todo updated", todo: updateTodo, todos: allTodos });
  } catch (error) {
    throw error;
  }
};

//删除
const deleteTodos = async (req: Request, res: Response): Promise<void> => {
  try {
    const deleteTodo: ITodo | null = await Todo.findByIdAndDelete(
      req.params.id
    );
    const allTodos: ITodo[] = await Todo.find();
    res.status(200).json({
      message: "Todo deleted",
      todo: deleteTodo,
      todos: allTodos,
    });
  } catch (error) {
    throw error;
  }
};

export { getTodos, addTodos, updatedTodos, deleteTodos };
