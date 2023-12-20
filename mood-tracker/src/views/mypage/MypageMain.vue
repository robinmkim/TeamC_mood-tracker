<template>
    <div class="flex h-screen">
        <!-- 왼쪽 사이드 메뉴 -->
        <div class="w-1/5 bg-sky-200 p-4">
            <p class="text-xl font-semibold">사이드 메뉴</p>
            <!-- 왼쪽 사이드 메뉴의 내용을 여기에 추가하세요 -->
        </div>

        <!-- 메인 콘텐츠 영역 -->
        <div class="flex-1">
            <!-- 자기 소개 부분 -->
            <div class="flex w-full h-120 bg-slate-200">
                <div class="profil-img flex items-center justify-center">
                    <img
                        class="w-20 h-20 rounded-full"
                        src="@/assets/KakaoTalk_20231216_175822670.png"
                        alt="이미지 설명"></div>
                    <div class="w-full flex-col ml-4">
                      <div class="flex h-16 flex-col-reverse">
                          <div class="flex items-center">
                              <div class="font-semibold mr-2 text-2xl">Your Name</div>
                              <div class="text-sm text-gray-500 mr-2">your.email@example.com</div>
                              <div class="flex-grow"></div>
                              <div>
                                  <button class="bg-blue-500 text-white rounded-md mr-5">프로필 편집</button>
                              </div>
                          </div>
                      </div>

                        <div class="relative h-5 w-100">
                            <div class="inset-y-0 left-0 w-30 text-left">지금은 마이페이지를 만들고 있다.</div>
                        </div>

                        <div class="flex justify-center mr-16">
                            <div class="flex ">
                                <div class="">
                                    <button class="bg-green-500 text-white px-4 py-2 rounded-md">팔로잉</button>
                                </div>
                                <div class="w-16"></div>
                                <div>
                                    <button class="bg-gray-300 text-gray-700 px-4 py-2 rounded-md">팔로우</button>
                                </div>
                                
                            </div>
                        </div>

                    </div>
                </div>

                <!-- 탭 네비게이션 -->
                <div class="tab-box">
                    <ul class="flex tab-btn-list space-x-4 p-4">
                        <li
                            v-for="(tab, index) in tabList"
                            :key="index"
                            :class="{ active: currentTab === index }">
                            <a
                                href="#"
                                @click.prevent="currentTab = index"
                                class="text-blue-500 hover:underline px-4 py-2 rounded-lg transition duration-300 ease-in-out {{ currentTab === index ? 'bg-blue-500 text-white' : 'bg-white' }}">
                                {{ tab }}
                            </a>
                        </li>
                    </ul>
                </div>

                <!-- 탭 콘텐츠 -->
                <div v-show="currentTab == 0" class="tab-cont p-4">
                    <p class="text-lg font-semibold">내용 1</p>
                    <!-- 탭 1에 대한 내용을 여기에 추가하세요 -->
                </div>

                <div v-show="currentTab == 1" class="tab-cont p-4">
                    <p class="text-lg font-semibold">내용 2</p>
                    <!-- 탭 2에 대한 내용을 여기에 추가하세요 -->
                </div>

                <div v-show="currentTab == 2" class="tab-cont p-4">
                    <p class="text-lg font-semibold">내용 3</p>
                    <!-- 탭 3에 대한 내용을 여기에 추가하세요 -->
                </div>
            </div>

            <!-- 오른쪽 사이드 메뉴 -->
            <div class="w-1/5 bg-sky-200 p-4">
                <p class="text-xl font-semibold">사이드 메뉴</p>

            </div>
        </div>
    </template>

    <script>
        export default {
            data() {
                return {
                    currentTab: 0,
                    tabList: ['게시물', '북마크', '미디어']
                };
            },
            name: "PageHome"
        };
    </script>

    <style scoped="scoped">
        /* 추가적인 스타일이 필요하면 여기에 추가하세요 */

        .tab-btn-list a {
            transition: background-color 0.3s, color 0.3s;
            border-radius: 4px;
            margin-top: 8px;
            display: block;
            padding: 10px;
            text-align: center;
            font-weight: bold;
            border: 1px solid #ccc;
        }

        .tab-btn-list a:hover {
            background-color: #1a365d;
            /* 원하는 호버 배경색으로 변경하세요 */
            color: #fff;
        }

        .tab-cont {
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-top: 8px;
            padding: 20px;
        }


    </style>