<template>
  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div>
      <h1>SSE Notification</h1>
      <ul>
        <li v-for="(notification, index) in notifications" :key="index">
          {{ notification }}
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import SideBar from "@/components/SideBar";
export default {
  name: "FaceAnalyze",
  components: {
    SideBar,
  },
  data() {
    return {
      notifications: [],
    };
  },
  created() {
    this.subscribeToSSE();
  },
  methods: {
    subscribeToSSE() {
      const eventSource = new EventSource(
        "http://192.168.0.93:8083/sse/subscribe"
      );
      eventSource.addEventListener("notification", (event) => {
        console.log(event.data);
        this.notifications.push(event.data);
      });
      eventSource.onopen = (event) => {
        console.log("SSE connection opened", event);
      };
      eventSource.onerror = (event) => {
        console.error("Error occurred:", event);
        // eventSource.close();
        // this.subscribeToSSE();
      };
    },
  },
};
</script>
<style></style>
