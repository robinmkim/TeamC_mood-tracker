<template>
  <div class="flex justify-center mx-10 mt-3">
    <div v-if="samplePosts.length">
      <div v-for="post in samplePosts" :key="post.id" class="my-5">
        <article class="w-full md:w-[500px] min-w-[500px] min-h-[200px] h-auto border-b-2 pb-5">
          <!-- 게시글 헤더 영역 -->
          <div class="flex flex-row mb-5">
            <img @click="$router.push({path: '/mypage', params: {id: post.id}})" :src="post.userIcon" class="rounded-full w-9 h-9 cursor-pointer border" alt="user icon"/>
            <div class="flex flex-row items-center mx-3">
              <div class="mr-2 cursor-pointer">{{ post.publisher }}</div>
              <div class="text-slate-400">{{ post.date }}</div>
            </div>
          </div>
          <!-- 게시글 이미지 영역 -->
          <div v-if="post.images.length" class="relative mb-3">
            <div class="flex items-center justify-center relative">
              <img :src="post.images[post.activeImageIndex].url" alt="Post image" />
              <a v-if="post.activeImageIndex > 0" class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer left-2.5"
                @click="prevImage(post)">
                &#10094;
              </a>
              <a v-if="post.activeImageIndex < post.images.length - 1" class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer right-2.5"
                @click="nextImage(post)">
                &#10095;
              </a>
            </div>
          </div>
          <!-- 게시글 본문 영역 -->
          <div>
            <div class="flex flex-row">
              <div class="flex flex-col mr-2">
                <img @click="increaseLike(post.id)" src="https://file.notion.so/f/f/4aa7ab7f-5a28-4d2f-a3a5-e523c0267871/a7cc1067-ebfb-46ff-b02e-aa5c6c894249/Untitled.png?id=64421960-e22e-4f05-8dba-a427436af456&table=block&spaceId=4aa7ab7f-5a28-4d2f-a3a5-e523c0267871&expirationTimestamp=1702994400000&signature=00JHDUUGvP6f105Co7zDyhDzeQtEix6pmu30Ux6a7Fs&downloadName=Untitled.png" alt="like">
                <span>{{ post.like }}</span>
              </div>
              <div>
                <img src="https://file.notion.so/f/f/4aa7ab7f-5a28-4d2f-a3a5-e523c0267871/2cd4e0de-ef93-4c7c-808e-8cfa7fe693e4/Untitled.png?id=6ed994aa-362b-409a-9e5a-d372b09ec1b5&table=block&spaceId=4aa7ab7f-5a28-4d2f-a3a5-e523c0267871&expirationTimestamp=1702994400000&signature=pH3une7RGKSDlfEPnUR9LemDu1dzg0emQ1E8bNbX6Oc&downloadName=Untitled.png" alt="comment">
              </div>
            </div>
          <div>
              {{ post.content }}
          </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePost",
  created() {
    this.makePost();
  },
  methods: {
    // 테스트 게시글 생성용 메서드
    makePost() {
      let k = 1;
      for (let i = 1; i <= 20; i++) {
        var imgCnt = Math.floor(Math.random() * 3) + 1;
        let post = {
          id: i,
          publisher: `사용자${i}`,
          date: new Date(Date.now() - Math.floor(Math.random() * 5000000) - 1000),
          content: `게시글 내용 ${i}`,
          images: [],
          activeImageIndex: 0, // 현재 활성화된 이미지 인덱스
          userIcon: `https://picsum.photos/id/${106+k}/200/300`,
          like: 0,
        };
        for (let j = 0; j < imgCnt; j++) {
          post.images.push({
            url: `https://picsum.photos/id/${i+j}/490/600`,
          });
          k += 1;
        }
        this.samplePosts.push(post);
      }
      this.calcTime();
    },
    calcTime() {
      for (let i =0; i<this.samplePosts.length; i++){
        const postDate = this.samplePosts[i].date;
        const now = new Date();
        const diff = now - postDate;
        let result = "";
        if (diff < 60000) {
          result = `${Math.floor(diff / 1000)}초전`;
        } else if (diff < 3600000) {
          result = `${Math.floor(diff / 60000)}분전`;
        } else {
          result = `${Math.floor(diff / 3600000)}시간전`;
        }
        this.samplePosts[i].date = result;
      }
    },
    // carousel 다음 이미지 표시
    nextImage(post) {
      if (post.activeImageIndex < post.images.length - 1) {
        post.activeImageIndex++;
      }
    },

    // carousel 이전 이미지 표시
    prevImage(post) {
      if (post.activeImageIndex > 0) {
        post.activeImageIndex--;
      }
    },
    // 좋아요 수 증가
    increaseLike(postId) {
      this.samplePosts[postId-1].like += 1;
      // 추후 DB 연동 시 좋아요 수 증가 처리 및 이미 좋아요를 눌렀는지 확인하는 로직 추가
    },
  },
  data() {
    return {
      samplePosts: []
    };
  },
};
</script>

<style scoped>
</style>
