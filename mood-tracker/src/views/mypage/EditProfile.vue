<template>
  <div class="flex min-h-screen">
    <div class="w-1/5">
      <side-bar> side menu</side-bar>
    </div>
    <div class="flex-1 border-x">
      <home-post>
        <div class="w-full mt-3 flex justify-center items-center">
          <div class="profil-img">
            <div>
              <div>
                <!-- 이미지를 누른경우 -->
                <label for="profile-image" class="cursor-pointer text-center">
                  <input
                    ref="fileInput"
                    type="file"
                    id="profile-image"
                    class="hidden"
                    @change="handleProfileImageUpload"
                  />

                  <!-- 프로필 이미지 -->
                  <img
                    class="w-20 h-20 mt-2 rounded-full inline-block"
                    :src="profileImageUrl || require('./../../assets/logo.png')"
                    alt="나의 프로필 이미지"
                  />
                </label>

                <!-- 프로필 편집 버튼 -->
                <div
                  class="text-center text-blue-500 text-sm mt-2 mb-2 cursor-pointer rounded-md"
                  @click="editProfile"
                >
                  프로필 사진 바꾸기
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="w-full h-2/4 items-center justify-center">
          <div class="flex-grow h-3"></div>
          <div class="flex items-center justify-center">
            <div class="w-1/2 border mb-3 mt-3 mr-5 ml-5 border-cyan-950">
              <input
                class="bg-inherit w-full h-5 resize-none focus:outline-none"
                placeholder="이름"
                @input="adjustHeight"
              />
            </div>
          </div>

          <div class="flex items-center justify-center">
            <div class="w-1/2 border mb-3 mt-3 mr-5 ml-5 border-cyan-950">
              <input
                class="bg-inherit w-full h-5 resize-none focus:outline-none"
                placeholder="이름"
                @input="adjustHeight"
              />
            </div>
          </div>
          <div class="flex items-center justify-center">
            <div class="w-1/2 border mb-3 mt-3 mr-5 ml-5 border-cyan-950">
              <input
                class="bg-inherit w-full h-5 resize-none focus:outline-none"
                placeholder="사용자 이름"
                @input="adjustHeight"
              />
            </div>
          </div>
          <div class="flex items-center justify-center">
            <div class="w-1/2 border mb-3 mt-3 mr-5 ml-5 border-cyan-950">
              <input
                class="bg-inherit w-full h-5 resize-none focus:outline-none"
                placeholder="웹사이트"
                @input="adjustHeight"
              />
            </div>
          </div>
          <div class="mb-3 mt-3">
            <input
              class="bg-[#64CCC5] w-1/2 h-8 rounded-md p-0.5 cursor-pointer"
              type="submit"
              value="입력"
            />
          </div>
        </div>
      </home-post>
    </div>
    <div class="w-1/5">side menu</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profileImageUrl: null,
    };
  },
  methods: {
    adjustHeight(e) {
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },
    // 이지지를 누르면 이미지 변경
    handleProfileImageUpload(event) {
      const file = event?.target?.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          this.profileImageUrl = reader.result;
        };
        reader.readAsDataURL(file);
      }
    },
    // 프로필 편집을 누르면 이미지 변경
    editProfile() {
      this.$refs.fileInput.click();
    },
  },
};
</script>
