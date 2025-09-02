<template>
  <ul class="listbox">
    <todoItem
      v-for="task in tasks"
      :key="task.id"
      :task="task"
      @delete-task="emit('delete-task', $event)"
      @toggle-completed="emit('toggle-completed', $event)"
      @change-priority="(taskId, newPriority) => emit('change-priority', taskId, newPriority)"
    />
  </ul>
</template>

<script setup lang="ts">
import type { TodoItem } from "../types/index.ts";
import todoItem from "./todoItem.vue";

defineProps<{
  tasks: TodoItem[];
}>();

const emit = defineEmits<{
  (e: "delete-task", taskId: number): void;
  (e: "toggle-completed", taskId: number): void;
  (e: "change-priority", taskId: number, newPriority: string): void;
}>();
</script>
<style scoped></style>
