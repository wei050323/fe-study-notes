//mongoose：Node.js常用的MongoDB ODM,把MongoDB的文档映射成js/ts对象，让你像操作对象一样操作数据库数据。
//Schema:用于定义集合中文档的“结构/约束”
//model:基于Schema生成的类/构造器。Model是用来对数据库进行：增删改查，创建示例、查询。
import { ITodo } from "./../types/todo";
import { model, Schema } from "mongoose";

//创建一个新的Schema即todoSchema
//`:Schema`是类型注解
const todoSchema: Schema = new Schema(
  {
    name: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      required: true,
    },
    status: {
      type: Boolean,
      required: true,
    },
  },
  //Schema的第二个参数(对象),自动在文档内添加`createdAt`和`updatedAt`两个字段（类型为Data),Mongoose会在创建/更新时自动维护它们。
  { timestamps: true }
);
//将基于`TodoSchema`的Model导出为默认导出
//`model<ITodo>`：ts的泛型，让生成的Model在ts中知道其操作的文档是什么类型
//第一个参数"Todo":模型名称
//第二个参数"todoSchema":我们定义的Schema
export default model<ITodo>("Todo", todoSchema);
//导出的东西是一个用于数据库操作的Model,例如:
/*
import Todo from "../models/todo"
// 创建一个新 todo
await Todo.create({ name: "x", description: "y", status: false })
// 查询
const list = await Todo.find()
*/
