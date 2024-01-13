<template>
  <div class="bg-[#64CCC5] w-full py-3 flex justify-end items-center relative">
    <div id="title" class="absolute left-1/2 transform -translate-x-1/2">
      <router-link to="/">
        <span>Inside Out</span>
      </router-link>
    </div>
    <div id="menu" class="flex mr-4">
      <div class="relative">
        <button @click="toggleDropdown">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-8 h-8 px-1"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z"
            />
          </svg>
        </button>
        <div
          v-show="isDropdownOpen"
          class="absolute flex flex-col bg-white shadow-md mt-2 rounded-md py-2 w-32 right-[1px]"
        >
          <router-link to="/"
            ><span class="border-b" @click="toggleDropdown"
              >Home</span
            ></router-link
          >
          <router-link to="/login"
            ><span class="border-b" @click="toggleDropdown"
              >Log In</span
            ></router-link
          >
          <router-link to="/timeline"
            ><span class="border-b" @click="toggleDropdown"
              >타임라인</span
            ></router-link
          >
        </div>
      </div>
      <router-link to="/chat">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-8 h-8 px-1"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75"
          />
        </svg>
      </router-link>
      <router-link to="/noti">
        <!-- notiDisplay: 확인하지 않은 알림 표시 -->
        <div
          class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
        ></div>
        <div
          class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
        ></div>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-8 h-8 px-1"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0"
          />
        </svg>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "PageHeader",
  data() {
    return {
      isDropdownOpen: false,
    };
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    logout() {
      localStorage.removeItem("jwtToken");
      this.$router.push({ path: "/login" });
    },
  },
};
</script>
<style scoped></style>
