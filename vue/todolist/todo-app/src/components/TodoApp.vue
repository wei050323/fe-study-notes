<template>
  <div class="container">
    <Header />
    <StatusBar
      :pending-count="pendingCount"
      :total-count="tasks.length"
      @clear-all="clearAllTasks"
      @select-priority="selectPriority"
    />
    <InputArea @add-task="addTask" />
    <TodoList
      v-if="tasks.length > 0"
      :tasks="filteredTasks"
      @delete-task="deleteTask"
      @toggle-completed="toggleCompleted"
      @change-priority="changePriority"
    />
    <EmptyState v-else />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import type { TodoItem, FilterType } from "../types";
import Header from "./Header.vue";
import InputArea from "./InputArea.vue";
import StatusBar from "./StatusBar.vue";
import TodoList from "./TodoList.vue";
import EmptyState from "./EmptyState.vue";

const tasks = ref<TodoItem[]>([]);
const filter = ref<FilterType>("all");

const pendingCount = computed(() => {
  return tasks.value.filter((task) => !task.completed).length;
});
const filteredTasks = computed(() => {
  switch (filter.value) {
    case "low":
      return tasks.value.filter((tasks) => tasks.priority === "low");
    case "medium":
      return tasks.value.filter((tasks) => tasks.priority === "medium");
    case "high":
      return tasks.value.filter((tasks) => tasks.priority === "high");
    default:
      return tasks.value;
  }
});

const addTask = (text: string) => {
  const newTask: TodoItem = {
    id: Date.now(),
    text,
    completed: false,
    createdAt: new Date(),
    priority: "low",
  };
  tasks.value.push(newTask);
  saveTasksToLocalStorage();
};

const toggleCompleted = (taskId: number) => {
  const task = tasks.value.find((t) => t.id === taskId);
  if (task) {
    task.completed = !task.completed;
    saveTasksToLocalStorage();
  }
};

const changePriority = (taskId: number, newPriority: string) => {
  let priority = newPriority as FilterType;
  const task = tasks.value.find((t) => t.id === taskId);
  if (task) {
    task.priority = priority;
    saveTasksToLocalStorage();
  }
};
const deleteTask = (taskId: number) => {
  tasks.value = tasks.value.filter((t) => t.id !== taskId);
  saveTasksToLocalStorage();
};
const clearAllTasks = () => {
  tasks.value = [];
  saveTasksToLocalStorage();
};

const selectPriority = (priority: string) => {
  filter.value = priority as FilterType;
};

const saveTasksToLocalStorage = () => {
  localStorage.setItem("todotasks", JSON.stringify(tasks.value));
};

const loadTasksFromLocalStorage = () => {
  const savedTasks = localStorage.getItem("todotasks");
  if (savedTasks) {
    tasks.value = JSON.parse(savedTasks);
  }
};

onMounted(() => {
  loadTasksFromLocalStorage();
});
</script>
