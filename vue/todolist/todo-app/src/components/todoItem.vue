<template>
  <li>
    <span>
      <label>
        <input
          type="checkbox"
          class="check"
          :checked="task.completed"
          @change="toggleCompleted"
        />
        <span class="done"><i class="uil uil-check"></i></span>
        <p :class="{ completed: task.completed }">{{ task.text }}</p>
      </label>
    </span>
    <i class="uil uil-trash-alt" @click="emit('delete-task', task.id)"></i>
  </li>
</template>

<script setup lang="ts">
import type { TodoItem } from "../types/index.ts";

const props = defineProps<{
  task: TodoItem;
}>();
const emit = defineEmits<{
  (e: "delete-task", taskId: number): void;
  (e: "toggle-completed", taskId: number): void;
}>();

const toggleCompleted = () => {
  emit("toggle-completed", props.task.id);
};
</script>

<style scoped>
.completed {
  text-decoration: line-through;
  opacity: 0.4;
}
</style>
