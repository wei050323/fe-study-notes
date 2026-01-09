import { Router } from "express";
import {
  getTodos,
  addTodos,
  updatedTodos,
  deleteTodos,
} from "../controllers/todos/index";

const router: Router = Router();
//第一个参数：路由路径
//第二个参数：处理该路由请求的控制器函数
router.get("/todos", getTodos);
router.post("/add-todo", addTodos);
router.put("/update-todo/:id", updatedTodos);
router.delete("/delete-todo/:id", deleteTodos);
export default router;
