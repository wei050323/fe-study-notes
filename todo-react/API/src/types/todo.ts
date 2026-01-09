import { Document } from "mongoose";
//继承Document，除了定义的name、description、status还包含了Document的属性
export interface ITodo extends Document {
  name: string;
  description: string;
  status: boolean;
}
