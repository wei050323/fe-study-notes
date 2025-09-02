export interface TodoItem {
  id: number;
  text: string;
  completed: boolean;
  createdAt: Date;
  priority: FilterType;
}
export type FilterType = "all" | "low" | "medium" | "high";

export interface StoredTodoItem {
  id: number;
  text: string;
  completed: boolean;
  createdAt: string; // Stored as ISO string\
  priority: string;
}
