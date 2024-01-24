const { defineConfig } = require("@vue/cli-service");
const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  publicPath: "/",
  transpileDependencies: true,
  outputDir: "../mood-tracker-be/src/main/resources/static",
  devServer: {
    port: 3000,
    proxy: "http://54.180.102.205",
  },
});
