<template>
  <div class="flex min-h-screen">
    <div class="flex-1 border-x">
      <div class="border-y-2">
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
                    class="w-20 h-20 mt-2 rounded-full inline-block border"
                    :src="profileImageUrl"
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
          <div class="flex items-center justify-center min-h-[150px] text-left">
            <div class="w-1/6 text-base">
              <span>이름</span>
            </div>
            <div v-if="!changingName" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <span>
                  {{ userInfo.m_name }}
                </span>
              </div>
              <div class="w-1/3" @click="toggleName">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                  />
                </svg>
              </div>
            </div>
            <div v-if="changingName" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <input
                  class="bg-inherit w-3/4 h-7 resize-none focus:outline-none border px-3"
                  placeholder="이름"
                  v-model="userInfo.m_name"
                  @input="adjustHeight"
                />
                <div class="flex mt-2">
                  <button
                    @click="updateName"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    저장
                  </button>
                  <button
                    @click="toggleName"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    취소
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="flex items-center justify-center min-h-[150px] text-left">
            <div class="w-1/6 text-base max-h-[130px]">
              <span>비밀번호</span>
            </div>
            <div class="flex w-2/3 my-3 mx-5">
              <div class="flex flex-col w-2/3 bg-s min-h-100">
                <div class="flex items-center mb-4">
                  <!-- Add margin-bottom to create space between rows -->
                  <span class="mr-2 min-w-[115px]">현재 비밀번호</span>
                  <input
                    class="bg-inherit flex-grow h-7 resize-none focus:outline-none border px-3"
                    type="password"
                    v-model="cur_pwd"
                    @input="adjustHeight"
                  />
                </div>

                <div class="flex items-center mb-4">
                  <!-- Add margin-bottom to create space between rows -->
                  <span class="mr-2 min-w-[115px]">새 비밀번호</span>
                  <input
                    class="bg-inherit flex-grow h-7 resize-none focus:outline-none border px-3"
                    type="password"
                    v-model="new_pwd"
                    @input="adjustHeight"
                  />
                </div>

                <div class="flex items-center mb-4">
                  <!-- No margin-bottom for the last row -->
                  <span class="mr-2 min-w-[115px]">새 비밀번호 확인</span>
                  <input
                    class="bg-inherit flex-grow h-7 resize-none focus:outline-none border px-3"
                    type="password"
                    v-model="new_pwd_check"
                    @input="adjustHeight"
                  />
                </div>
                <div class="flex mt-2">
                  <button
                    @click="updatePassword"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    비밀번호 변경
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="flex items-center justify-center min-h-[150px] text-left">
            <div class="w-1/6 text-base">
              <span>Handle</span>
            </div>
            <div v-if="!changingHandle" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <span>
                  {{ userInfo.m_handle }}
                </span>
              </div>
              <div class="w-1/3" @click="toggleHandle">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                  />
                </svg>
              </div>
            </div>
            <div v-if="changingHandle" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <input
                  class="bg-inherit w-3/4 h-7 resize-none focus:outline-none border px-3"
                  placeholder="핸들"
                  v-model="userInfo.m_handle"
                  @input="adjustHeight"
                />
                <div class="flex mt-2">
                  <button
                    @click="updateHandle"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    저장
                  </button>
                  <button
                    @click="toggleHandle"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    취소
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="flex items-center justify-center min-h-[150px] text-left">
            <div class="w-1/6 text-base">
              <span>바이오</span>
            </div>
            <div v-if="!changingBio" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <span>
                  {{ userInfo.m_bio }}
                </span>
              </div>
              <div class="w-1/3" @click="toggleBio">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                  />
                </svg>
              </div>
            </div>
            <div v-if="changingBio" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <input
                  class="bg-inherit w-3/4 h-7 resize-none focus:outline-none border px-3"
                  placeholder="바이오"
                  v-model="userInfo.m_bio"
                  @input="adjustHeight"
                />
                <div class="flex mt-2">
                  <button
                    @click="updateBio"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    저장
                  </button>
                  <button
                    @click="toggleBio"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    취소
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="flex items-center justify-center min-h-[150px] text-left">
            <div class="w-1/6 text-base">
              <span>성별</span>
            </div>

            <div v-if="!changingGender" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <span>{{ userInfo.m_gender }}</span>
              </div>
              <div class="w-1/3" @click="toggleGender">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                  />
                </svg>
              </div>
            </div>

            <div v-if="changingGender" class="flex w-2/3 my-3 mx-5">
              <div class="flex flex-col w-2/3 bg-s h-[fit-content]">
                <div>
                  <label class="mr-2">
                    <input
                      type="radio"
                      value="male"
                      v-model="userInfo.m_gender"
                      class="mr-1"
                      :checked="userInfo.m_gender === 'male'"
                    />
                    남성
                  </label>

                  <label>
                    <input
                      type="radio"
                      value="female"
                      v-model="userInfo.m_gender"
                      class="mr-1"
                      :checked="userInfo.m_gender === 'female'"
                    />
                    여성
                  </label>
                </div>
                <div class="flex mt-3">
                  <button
                    @click="updateGender"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    저장
                  </button>
                  <button
                    @click="toggleGender"
                    class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                  >
                    취소
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="mb-3 mt-3">
            <router-link to="/">
              <input
                class="bg-[#64CCC5] w-1/5 h-8 rounded-md p-0.5 cursor-pointer"
                type="submit"
                value="나가기"
              />
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";

export default {
  data() {
    return {
      profileImageUrl: null,
      userInfo: {},
      changingName: false,
      changingPassword: false,
      changingHandle: false,
      changingBio: false,
      changingEmail: false,
      changingGender: false,
      cur_pwd: "",
      new_pwd: "",
      new_pwd_check: "",
    };
  },
  created() {
    this.getMemberInfo();
  },
  watch: {
    userInfo: {
      handler: 'updateProfileImageUrl',
      deep: true,
    },
  },
  methods: {
    updateProfileImageUrl() {
      this.profileImageUrl = `http://localhost:8083/${this.userInfo.m_img_path}${this.userInfo.m_img_name}`;
    },
    getMemberInfo() {
      this.userInfo = JSON.parse(this.$store.state.userInfo);
    },
    adjustHeight(e) {
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },
    // 이미지를 누르면 이미지 변경
    handleProfileImageUpload(event) {
      const file = event?.target?.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          this.profileImageUrl = reader.result;
        };
        reader.readAsDataURL(file);
      }

      let formData = new FormData();
      formData.append("m_profile", file);
      apiClient
        .put("/member/profile/image", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(() => {
          this.userInfo.m_img_name = file.name;
          this.$store.dispatch("updateUserProfileImage", file.name);
        })
        .catch((err) => {
          console.log(err, "프로필 이미지 변경 실패");
        });
    },
    // 프로필 편집을 누르면 이미지 변경
    editProfile() {
      this.$refs.fileInput.click();
    },
    toggleName() {
      this.changingName = !this.changingName;
    },
    togglePassword() {
      this.changingPassword = !this.changingPassword;
    },
    toggleHandle() {
      this.changingHandle = !this.changingHandle;
    },
    toggleBio() {
      this.changingBio = !this.changingBio;
    },
    toggleEmail() {
      this.changingEmail = !this.changingEmail;
    },
    toggleGender() {
      this.changingGender = !this.changingGender;
    },
    updateName() {
      this.$store.dispatch("updateUserName", this.userInfo.m_name);
      this.toggleName();
    },
    updateHandle() {
      this.$store.dispatch("updateUserHandle", this.userInfo.m_handle);
      this.toggleHandle();
    },
    updateBio() {
      this.$store.dispatch("updateUserBio", this.userInfo.m_bio);
      this.toggleBio();
    },
    updateGender() {
      this.$store.dispatch("updateUserGender", this.userInfo.m_gender);
      this.toggleGender();
    },
    updatePassword() {
      if (this.new_pwd !== this.new_pwd_check) {
        alert("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
        return;
      }
      // 비밀번호 양식

      apiClient
        .put(`/member/profile/password`, {
          m_pwd: this.userInfo.m_pwd,
          new_pwd: this.userInfo.new_pwd,
        })
        .then((res) => {
          console.log(res);
          this.togglePassword();
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
