<template>
    <div class="m-4 bg-yellow-100">
        <!-- 게시글 헤더 영역 -->
        <div class="flex flex-row mb-5 ">
            <div class="h-14 w-14 overflow-hidden relative">
                <img class="postDetailUserImg object-contain rounded-full" src="..\..\assets\notiProfileImage01.jpg" alt="user icon"/>
            </div>
            <div class="flex flex-row items-center mx-3 ">
                <div class="notiUserName font-bold text-lg">UserName</div>
                <div class="text-slate-400 text-sm ml-2">2분전</div>
            </div>
            <div class="icon ml-auto -mr-3 mt-3">
                <img class="rotate-90 w-1/2" src="..\..\assets\meatvallMenuIcon.png">
            </div>
        </div>
        <!-- 게시글 이미지 영역 -->
        <div class="relative mb-3">
            <div class="flex items-center justify-center relative">
              <img src="..\..\assets\dog01.jpg" alt="Post image" class=""/> 
              <!-- <a v-if="post.activeImageIndex > 0" class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer left-2.5"
                @click="prevImage(post)">
                &#10094;
              </a> -->
              <a class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer right-2.5"
              >
                &#10095;
              </a>
            </div>
        </div>
    </div>
</template>

<script>

</script>

<style scoped>
</style>