<template>
  <div class="status-bar">
    <div class="tasks-left">
      <span class="pending-count">{{ pendingCount }}</span
      >tasks left
    </div>
    <select
      name="priority"
      class="priority"
      @change="
        emit('select-priority', ($event.target as HTMLSelectElement).value)
      "
    >
      <option value="all">all</option>
      <option value="low">low</option>
      <option value="medium">medium</option>
      <option value="high">high</option>
    </select>

    <button
      class="clear-all"
      @click="emit('clear-all')"
      :disabled="pendingCount === 0"
    >
      Clear All
    </button>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  pendingCount: number;
  totalCount: number;
}>();

const emit = defineEmits<{
  (e: "clear-all"): void;
  (e: "select-priority", priority: string): void;
}>();
</script>

<style scoped></style>
