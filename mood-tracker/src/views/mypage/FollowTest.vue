<template>
  <div>
    <button class="mt-10 bg-slate-50" @click="follow(4)">팔로우</button>
  </div>
</template>

<script>
import { jwtDecode } from "jwt-decode";
import apiClient from "@/utils/apiClient";
export default {
  data() {
    return {
      mid: null,
    };
  },
  created() {
    this.mid = this.getLoggedInMember();
  },
  methods: {
    getLoggedInMember() {
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      return decoded.m_id;
    },
    follow(followedId) {
      if (this.mid === followedId) {
        alert("자기 자신을 팔로우할 수 없습니다.");
        return;
      }
      apiClient
        .post("/follow/test", {
          followedId: followedId,
          followerId: this.mid,
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
