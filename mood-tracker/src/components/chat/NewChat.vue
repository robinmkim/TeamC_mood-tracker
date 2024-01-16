<template>
    <div class="modal-background" v-if="show" @click="closeModal">
        <div class="modal-content" @click.stop>
            <div class="flex my-6 mx-4">
                <span class="text-xl font-semibold">대화상태 선택</span>
            </div>
            <div class="border-b-2"></div>
            <div class="relative">
                <div>
                    <!-- 검색창 -->
                    <input type="text" class="bg-gray-50 border-b-2 text-gray-900 text-sm rounded-lg w-full p-2.5">
                </div>
                <div>
                    <!-- 검색버튼 -->
                    <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                        <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                        </svg>
                    </div>
                </div>
            </div>
            <div>
                <!-- 대화상태 리스트 -->
                <div v-for="member in members" :key="member.mem_num" class="flex justify-between border-b-2">
                    <div class="my-2 mx-3">
                        <!-- 상대 이름 -->
                        {{ member.m_name }}
                    </div>
                    <div>
                        <!-- 선택 버튼 -->
                        <button class="my-2 mx-3" @click="startChatting(member.m_id, member.m_name)">채팅</button>
                    </div>
                </div>
            </div>
            <!-- 닫기 버튼 -->
            <button @click="closeModal">Close</button>
        </div>
    </div>
</template>

<script>
export default {
    props: ['show', 'members'],
    methods: {
        closeModal() {
            this.$emit('close');
        },
        startChatting(memberId, memberName) {
            const eventData = {
                memberId: memberId,
                memberName: memberName,
            };
            this.$emit('start-chat', eventData);
        }
    },
};
</script>

<style>
/* 모달 백그라운드 스타일 */
.modal-background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* 어두운 배경색 (반투명) */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999; /* 모달을 다른 요소 위에 표시하기 위한 z-index 설정 */
}

/* 모달 내용 스타일 */
.modal-content {
    background-color: white; /* 흰 배경색 */
    /* size */
    width: 500px;
    min-height: 600px;
    /* border */
    border: 1px solid #ddd;
    border-radius: 15px;
}
</style>
