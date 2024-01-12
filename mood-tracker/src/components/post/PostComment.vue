<template>
  <div
    class="flex overflow-y-auto h-3/4"
    @scroll="handleScroll"
    :class="childClass"
  >
    <!-- min-h-screen -->
    <!-- post 내용 -->
    <!-- <postDetail :b_id="b_id"></postDetail> -->

    <div class="flex-1 border-l border-l-slate-300">
      <!-- 댓글창 -->
      <div v-if="comment != null">
        <div v-for="(item, index) in comment[0]" :key="index">
          <div class="p-3 pb-0 border-b border-t-slate-300">
            <div class="postHerder flex flex-row">
              <div class="h-14 w-14 overflow-hidden relative rounded-full">
                <img
                  class="postDetailUserImg object-contain rounded-full"
                  :src="
                    getUserImageUrl(
                      item.member.m_img_path,
                      item.member.m_img_name
                    )
                  "
                  alt="user icon"
                />
              </div>
              <div class="flex flex-row items-center mx-3">
                <div class="notiUserName font-bold text-lg">
                  {{ item.member.m_name }}
                </div>
                <div class="userHandle text-sm text-slate-500 ml-1">
                  {{ item.member.m_handle }}
                </div>
                <div class="text-slate-400 text-sm ml-2">
                  {{ formatTime(item.regdate) }}
                </div>
                <!-- <div class="text-slate-400 text-sm ml-2">2분전</div> -->
              </div>
              <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
                <!-- 미트볼 아이콘-->
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6 pt-1"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z"
                  />
                </svg>
              </div>
            </div>
            <div class="text-left ml-6 mt-3 mb-3">
              {{ item.cm_content }}
            </div>

            <div v-if="item.replyList.length > 0">
              <div v-for="(rItem, rIndex) in item.replyList" :key="rIndex">
                <div v-if="rItem.re_id !== 0">
                  <div class="p-3 border-t border-b-slate-300 pl-10">
                    <div class="postHerder flex flex-row m">
                      <div
                        class="h-14 w-14 overflow-hidden relative rounded-full"
                      >
                        <img
                          class="postDetailUserImg object-contain rounded-full"
                          :src="
                            getUserImageUrl(
                              rItem.member.m_img_path,
                              rItem.member.m_img_name
                            )
                          "
                          alt="user icon"
                        />
                      </div>
                      <div class="flex flex-row items-center mx-3">
                        <div class="notiUserName font-bold text-lg">
                          {{ rItem.member.m_name }}
                        </div>
                        <div class="userHandle text-sm text-slate-500 ml-1">
                          {{ rItem.member.m_handle }}
                        </div>
                        <div class="text-slate-400 text-sm ml-2">
                          {{ formatTime(rItem.regdate) }}
                        </div>
                      </div>
                      <div
                        class="icon ml-auto -mr-3 mt-3 relative inline-block"
                      >
                        <!-- 미트볼 아이콘-->
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke-width="1.5"
                          stroke="currentColor"
                          class="w-6 h-6 pt-1"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z"
                          />
                        </svg>
                      </div>
                    </div>
                    <div class="text-left ml-6 mt-2">
                      {{ rItem.re_content }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- <div class="w-1/5">side menu</div> -->
</template>

<script>
import apiClient from "@/utils/apiClient";
export default {
  data() {
    return {
      b_id: this.$route.query.b_id,
      comment: [],
    };
  },
  methods: {
    handleScroll() {
      this.$emit("post-comment-scroll");
    },
    getCommentListDetail() {
      apiClient
        .get(`/jh_comment/getCommentListDetail?cm_bid=${this.b_id}`)
        .then((response) => {
          this.comment.push(response.data);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getUserImageUrl(m_img_path, m_img_name) {
      return "http://localhost:8083/" + m_img_path + m_img_name;
    },
    formatTime(dateString) {
      const now = new Date();
      const postDate = new Date(dateString);
      const diffInSeconds = Math.floor((now - postDate) / 1000);
      if (diffInSeconds < 60) {
        return `${diffInSeconds}초 전`;
      }

      const diffInMinutes = Math.floor(diffInSeconds / 60);
      if (diffInMinutes < 60) {
        return `${diffInMinutes}분 전`;
      }

      const diffInHours = Math.floor(diffInMinutes / 60);
      if (diffInHours < 24) {
        return `${diffInHours}시간 전`;
      }

      return postDate.toLocaleDateString("ko-KR");
    },
  },
  props: { childClass: String },
  name: "PostDetailPage",
  components: {},
  created() {
    this.getCommentListDetail();
  },
};
</script>

<style scoped></style>
