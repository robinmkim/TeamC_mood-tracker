<template>
    <div class="flex min-h-screen">
      <div class="w-1/5">
        <side-bar></side-bar>
      </div>
      <div class="flex-1 border-x flex">
        <div class="flex-1">
          <!-- post 내용 -->
          <postDetail></postDetail>
        </div>
        <div class="flex-1 border-l border-l-slate-300">
          <!-- 댓글창 -->
          <div class="p-3 border-b border-b-slate-300">
            <div class="postHerder flex flex-row m">
              <div class="h-14 w-14 overflow-hidden relative">
                  <img class="postDetailUserImg object-contain rounded-full" src="..\..\assets\notiProfileImage01.jpg" alt="user icon"/>
              </div>
              <div class="flex flex-row items-center mx-3 ">
                  <div class="notiUserName font-bold text-lg">UserName</div>
                  <div class="text-slate-400 text-sm ml-2">2분전</div>
              </div>
              <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
                  <!-- 미트볼 아이콘-->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 pt-1">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z" />
                  </svg>
              </div>
            </div>
            <div class="text-left ml-6 mt-2">
              댓글내용~~~ 01
            </div>
          </div>

          <div class="p-3 border-b border-b-slate-300 pl-10">
            <div class="postHerder flex flex-row m">
              <div class="h-14 w-14 overflow-hidden relative">
                  <img class="postDetailUserImg object-contain rounded-full" src="..\..\assets\notiProfileImage01.jpg" alt="user icon"/>
              </div>
              <div class="flex flex-row items-center mx-3 ">
                  <div class="notiUserName font-bold text-lg">UserName2</div>
                  <div class="text-slate-400 text-sm ml-2">2분전</div>
              </div>
              <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
                  <!-- 미트볼 아이콘-->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 pt-1">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z" />
                  </svg>
              </div>
            </div>
            <div class="text-left ml-6 mt-2">
              댓글내용~~~ 02
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="w-1/5">side menu</div> -->
    </div>
  </template>
  
  <script>
  import SideBar from "@/components/SideBar"
  import postDetail from "@/components/post/postDetail"
  export default {
    components: {
      SideBar,
      postDetail,
    }
  };
  </script>
  
  <style scoped>
  </style>