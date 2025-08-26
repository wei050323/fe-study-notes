<template>
  <div class="status-bar">
    <div class="tasks-left">
      <span class="pending-count">{{ pendingCount }}</span
      >tasks left
    </div>
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
}>();
</script>

<style scoped></style>
