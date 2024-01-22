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
                    :src="getPrfileImgUrl()"
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
              <div class="w-1/3">
                <img src="./../../assets/profile/edit-button1.png" alt="수정" @click="toggleName">
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
                  <button @click="updateName" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">저장</button>
                  <button @click="toggleName" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">취소</button>
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
                <div class="flex items-center mb-4"> <!-- Add margin-bottom to create space between rows -->
                  <span class="mr-2 min-w-[115px]">현재 비밀번호</span>
                  <input
                    class="bg-inherit flex-grow h-7 resize-none focus:outline-none border px-3"
                    type="password"
                    @input="adjustHeight"
                  />
                </div>

                <div class="flex items-center mb-4"> <!-- Add margin-bottom to create space between rows -->
                  <span class="mr-2 min-w-[115px]">새 비밀번호</span>
                  <input
                    class="bg-inherit flex-grow h-7 resize-none focus:outline-none border px-3"
                    type="password"
                    v-model="new_pwd"
                    @input="adjustHeight"
                  />
                </div>

                <div class="flex items-center mb-4"> <!-- No margin-bottom for the last row -->
                  <span class="mr-2 min-w-[115px]">새 비밀번호 확인</span>
                  <input
                    class="bg-inherit flex-grow h-7 resize-none focus:outline-none border px-3"
                    type="password"
                    v-model="new_pwd_check"
                    @input="adjustHeight"
                  />
                </div>
                <div class="flex mt-2">
                  <button @click="updatePassword" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">비밀번호 변경</button>
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
              <div class="w-1/3">
                <img src="./../../assets/profile/edit-button1.png" alt="수정" @click="toggleHandle">
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
                  <button @click="updateHandle" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">저장</button>
                  <button @click="toggleHandle" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">취소</button>
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
              <div class="w-1/3">
                <img src="./../../assets/profile/edit-button1.png" alt="수정" @click="toggleBio">
              </div>
            </div>
            <div v-if="changingBio" class="flex w-2/3 my-3 mx-5">
              <div class="w-2/3 bg-s h-8">
                <input
                  class="bg-inherit w-3/4 h-7 resize-none focus:outline-none border px-3"
                  placeholder="핸들"
                  v-model="userInfo.m_bio"
                  @input="adjustHeight"
                />
                <div class="flex mt-2">
                  <button @click="updateBio" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">저장</button>
                  <button @click="toggleBio" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">취소</button>
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
              <div class="w-1/3">
                <img src="./../../assets/profile/edit-button1.png" alt="수정" @click="toggleGender">
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
                  <button @click="updateGender" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">저장</button>
                  <button @click="toggleGender" class="flex items-center justify-center mr-3 h-7 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium text-sm px-5 py-2 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">취소</button>
                </div>
              </div>
            </div>
          </div>
          <div class="mb-3 mt-3">
            <input
              class="bg-[#64CCC5] w-1/5 h-8 rounded-md p-0.5 cursor-pointer"
              type="submit"
              value="나가기"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { jwtDecode } from "jwt-decode";
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
      new_pwd: "",
      new_pwd_check: "",
    };
  },
  created() {
    this.getMemberInfo();
    this.getPrfileImgUrl();
    console.log(this.userInfo);
  },
  methods: {
    getPrfileImgUrl() {
      return `http://localhost:8083/${this.userInfo.m_img_path}${this.userInfo.m_img_name}`;
    },
    getMemberInfo() {
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      const memberId = decoded.m_id;

      apiClient
        .get(`/member/info/${memberId}`)
        .then((res) => {
          this.userInfo = res.data;
        })
        .catch((err) => {
          console.log(err, "유저 정보 못불러옴");
        });
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
        .then((res) => {
          console.log(res);
          console.log(file);
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
      apiClient
        .put(`/member/profile/name`, {
          m_name: this.userInfo.m_name,
        })
        .then((res) => {
          console.log(res);
          this.toggleName();
        })
        .catch((err) => {
          console.log(err, "이름 변경 실패");
        });
    },
    updateHandle() {
      apiClient
        .put(`/member/profile/handle`, {
          m_handle: this.userInfo.m_handle,
        })
        .then((res) => {
          console.log(res);
          this.toggleHandle();
        })
        .catch((err) => {
          console.log(err, "핸들 변경 실패");
        });
    },
    updateBio() {
      apiClient
        .put(`/member/profile/bio`, {
          m_bio: this.userInfo.m_bio,
        })
        .then((res) => {
          console.log(res);
          this.toggleBio();
        })
        .catch((err) => {
          console.log(err, "바이오 변경 실패");
        });
    },
    updateGender() {
      apiClient
        .put(`/member/profile/gender`, {
          m_gender: this.userInfo.m_gender,
        })
        .then((res) => {
          console.log(res);
          this.toggleGender();
        })
        .catch((err) => {
          console.log(err, "성별 변경 실패");
        });
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
          console.log(err, "비밀번호 변경 실패");
        });
    },
  },
};
</script>
