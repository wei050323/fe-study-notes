<template>
  <div class="textarea">
    <input
      type="text"
      class="textbox"
      placeholder="Enter your thoughts···"
      v-model="inputText"
      @keyup.enter="addTask"
      @input="updateButtonState"
    />
    <button
      class="addbtn"
      :class="{ active: inputText.length > 0 }"
      @click="addTask"
    >
      +
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
const inputText = ref("");

const emit = defineEmits<{
  (e: "add-task", taskText: string): void;
}>();
const addTask = () => {
  if (inputText.value.length > 0) {
    emit("add-task", inputText.value);
    inputText.value = "";
  }
};
const updateButtonState = () => {};
</script>
