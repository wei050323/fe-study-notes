export interface TodoItem {
  id: number;
  text: string;
  completed: boolean;
  createdAt: Date;
}
export type FilterType = "all" | "active" | "completed";

export interface StoredTodoItem {
  id: number;
  text: string;
  completed: boolean;
  createdAt: string; // Stored as ISO string
}
