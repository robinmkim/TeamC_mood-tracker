<template>
    <div class="flex h-screen">
        <div class="w-1/5 bg-sky-200">side menu</div>

        <div id="chatroomList" class="flex  w-2/5 border-l border-r 1px solid border-gray-200 flex-col">
            <div class="flex items-center align-middle py-4 pl-6 pr-6">
                <img class="p-1" src="" alt="hi">
                <h2 class="p-1 text-lg ">Message</h2>
            </div>
            
            <div class="overflow-auto">
                <ul class="list-none">
                    <li v-for="(chat, index) in chatList" :key="index" 
                        
                        class="flex rounded-lg bg-gray-100 p-2 m-1 items-center hover:bg-gray-300">
                        <div class="flex items-center w-4/5"> 
                            <img class="rounded-full h-14 mr-2" :src="(``)">
                            <div class="font-bold">{{ chat.title }}</div>
                        </div>
                        

                        <div class="flex w-1/5"> 
                            <div class="flex-col ml-auto">
                                <button class="align-top" @click="toggleMenu(index, $event)">
                                    <img class="" src="">
                                </button>

                                <ul v-if="chat.isMenuOpen" class="menu-list" 
                                    :style="{ top: `${menuPosition.top}px`, left: `${menuPosition.left}px` }">
                                    <li><button type="button">대화삭제</button></li>
                                    <li><button type="button">신고하기</button></li>
                                </ul>

                                <div class="text-xs">{{ chat.lastchattime }}</div>
                            </div>
                        </div>
                        
                    </li>
                </ul>
            </div>
            
        </div>

        <div id="chatRoom" class="flex w-2/5 border-r  border-gray-200  flex-col">
            
            <div class="flex pl-2 pr-2 pt-3 pb-3 items-center border-b  border-gray-200">
                <img class="rounded-full h-12 mr-2" 
                    src="">
                <h2 class="text-lg font-bold">채팅방 1</h2>
            </div>
            
            <div id="chatMessages" class="mt-auto overflow-y-auto overflow-x-hidden" >

                <div class="flex pl-4 pr-4 py-1 justify-start m-1">
                    <div class="flex flex-col">
                        <div class="flex rounded-lg bg-gray-300 p-2">
                            <div>message01</div>
                        </div>
                        <div class="justify-start self-start text-xs mt-1 ">
                            오후 11:10
                        </div>
                    </div>
                </div>


                <div class="flex pl-4 pr-4 py-1 justify-end m-1 w-full">
                    <div class="flex flex-col">
                        <div class="flex rounded-lg bg-gray-300 p-2 w-2/3 self-end">
                            <div class="overflow-auto">
                                <div class="whitespace-normal break-all">
                                    mes saage0ge0messageage0ge0messageage0ge0mes sageage0ge0messag eage0ge0messageage0ge0messageage0ge0message0ge0messageage0ge0messageage0ge0messageessage0message0ge0mage0ge0messageage0ge0messageessage0message0ge0message0message0
                                </div>
                            </div>
                        </div>
                        <div class="self-end text-xs mt-1 ">
                            오후 11:33
                        </div>
                    </div>
                </div>


                <div class="flex pl-4 pr-4 py-1 justify-start m-1 w-full">
                    <div class="flex flex-col">
                        <div class="flex rounded-lg bg-gray-300 p-2 w-2/3 self-start">
                            <div class="overflow-auto">
                                <div class="whitespace-normal break-all">
                                    mes saage0geage0ge0messag eage0ge0messageage0ge0messageage0ge0message0ge0messageage0ge0messageage0ge0messageessage0message0ge0mage0ge0messageage0ge0messageessage0message0ge0message0message0
                                </div>
                            </div>
                        </div>
                        <div class="self-start text-xs mt-1 ">
                            오후 11:33
                        </div>
                    </div>
                </div>

                <div class="flex pl-4 pr-4 py-1 justify-start m-1">
                    <div class="flex flex-col">
                        <div class="flex rounded-lg bg-gray-300 p-2 w-2/3">
                            <img class=""
                                    src="">
                        </div>
                        <div class="self-start text-xs mt-1 ">
                            오후 11:33
                        </div>
                    </div>
                </div>

                <div class="flex pl-4 pr-4 py-1 justify-start m-1">
                    <div class="flex flex-col">
                        <div class="flex rounded-lg bg-gray-300 p-2 w-2/3">
                            <img class=""
                                    src="">
                        </div>
                        <div class="self-start text-xs mt-1 ">
                            오후 11:33
                        </div>
                    </div>
                </div>


            </div>

            <div id="inputMessage" class="flex flex-grow items-center text-center p-1">
                <span class="rounded-lg hover:bg-gray-100">

                    <label for="file" class="">
                        <img src="">
                    </label>
                    <input type="file" name="file" id="file">

                </span>
                <span class="flex-grow mr-0.5">
                    <textarea id="myTextarea" 
                                class="autoExpand bg-gray-300 block w-full rounded-md border-0 py-1.5 pl-3 pr-3 text-gray-900 
                                ring-1 ring-inset ring-gray-300 placeholder:text-gray-600 
                                focus:ring-2 focus:ring-inset focus:ring-gray-100 sm:text-sm sm:leading-6"
                                rows="1"
                                placeholder="메세지 입력.."
                                @input="autoExpand"></textarea>
                </span>
                <span class="flex items-center w-10">
                    <button type="submit"
                            class="flex rounded-lg hover:bg-gray-100">
                        <img class=" self-center m-1"
                            src="">
                    </button>
                </span>
            </div>

        </div>
    </div>
  </template>
  
  <script>
  import { reactive } from 'vue';
  export default {
    name: "ChatMain",
    data() {
        return {
            menuPosition: { top: 0, left: 0 },
            chatList:[
                {title:"채팅방11", lastchattime:"7일 전", profileimage:"profileimage.png", isMenuOpen:false},
                {title:"채팅방11", lastchattime:"7일 전", profileimage:"profileimage.png", isMenuOpen:false},
                {title:"채팅방11", lastchattime:"7일 전", profileimage:"profileimage.png", isMenuOpen:false},

            ],
        }; 
    },  
    methods: {
        autoExpand(event) {
            event.target.style.height = 'auto';
            event.target.style.height = (event.target.scrollHeight) + 'px';
        },
        toggleMenu(index, event) {
            // 모든 채팅방의 메뉴 상태를 닫음
            this.chatList.forEach((chat, i) => {
                if (i !== index && chat.isMenuOpen) {
                    this.chatList[i] = reactive({
                        ...chat,
                        isMenuOpen: false,
                    });
                }
            });

            // 선택한 채팅방의 메뉴 상태를 토글
            const rect = event.target.getBoundingClientRect();
            this.chatList[index] = reactive({
                ...this.chatList[index],
                menuPosition: {
                    top: rect.bottom + window.scrollY + 5,
                    left: rect.left + window.scrollX - 50,
                },
                isMenuOpen: !this.chatList[index].isMenuOpen,
            });

            // 메뉴 상태 업데이트 후 컴포넌트의 일반 상태를 업데이트
            this.menuPosition = {
                top: rect.bottom + window.scrollY + 5,
                left: rect.left + window.scrollX - 50,
            };
        },
    },
  };
    </script>
  
  <style scoped>
    .menu-list {
        list-style-type: none;
        padding: 0;
        margin: 0;
        position: absolute;
        background-color: #f9f9f9;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        z-index: 1;
        top: 0; /* 버튼과의 상대적인 위치 조절 */
        left: 100%;
    }

    .menu-list li {
        padding: 6px;
        cursor: pointer;
        border-bottom: 1px solid #ddd;
    }

    .menu-list li:last-child {
        border-bottom: none;
    }

    #file {
    display: none;
    }
    #inputMessage {
        min-height: 1em;
        height: auto;
        overflow: auto;
    }

    #myTextarea {
        overflow: auto;
        resize: none;
        min-height: 1em;
        max-height: 8.6em;
    }
  </style>