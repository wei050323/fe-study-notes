import { defineConfig } from "vite";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import vue from "@vitejs/plugin-vue";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()], // 修正拼写错误：resolevers -> resolvers
    }),
    Components({
      resolvers: [ElementPlusResolver()], // 修正拼写错误：resolevers -> resolvers
    }),
  ],
});
