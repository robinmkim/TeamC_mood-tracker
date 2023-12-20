<template>
    <div class="flex h-screen">
        <div class="w-1/5 bg-sky-200">side menu</div>

        <div class="flex-1 w-full bg-[#E7F1E5]">
            <!-- 여기서부터 ~~~ -->
            <div class="flex flex-col items-center justify-center">


                <span @click="openFileInput" class="cursor-pointer">
                    <div class="flex w-100 h-100 p-3 item-center text-center bg-slate-200"
                        @dragover.prevent="handleDragOver" @drop="handleDrop">

                        <p>사진 선택 <br />
                            또는 <br />
                            드래그 하여 등록하기</p>
                        <input type="file" ref="fileInput" class="hidden" @change="handleFileSelect"
                            accept=".jpg, .jpeg, .png" />

                    </div>
                </span>

                <div class="object-scale-down  w-70 h-70 ">
                    <img v-if="image" :src="image" alt="Uploaded" class="flex mt-4">
                </div>

                <div class="flex justify-center mt-3">
                    <span id="submit" class="bg-[#88AB8E] w-24 h-18 rounded-full cursor-pointer"
                        @click="goToResult">분석하기</span>
                </div>
            </div>
            <!-- 여까지 -->
        </div>

        <div class="w-1/5 bg-sky-200">side menu</div>
    </div>
</template>
  
<script>
export default {
    name: "FaceAnalyze",

    data() {
        return {
            image: null,
        };
    },
    methods: {
        handleDragOver(event) {
            event.dataTransfer.dropEffect = 'copy';
        },
        handleDrop(event) {
            event.preventDefault();
            const file = event.dataTransfer.files[0];
            this.handleImageUpload(file);
        },
        handleFileSelect() {
            const fileInput = this.$refs.fileInput;
            const file = fileInput.files[0];
            this.handleImageUpload(file);
        },
        handleImageUpload(file) {
            if (file) {
                const reader = new FileReader();
                reader.onload = () => {
                    this.image = reader.result;
                };
                reader.readAsDataURL(file);
            }
        },
        openFileInput() {
            const fileInput = this.$refs.fileInput;
            fileInput.click();
        },

        goToResult() {
            this.$router.push('/AnalyzeResult');
        },
    },
};
</script>
  
<style scoped>
/* 추가적인 스타일링을 위해 필요한 경우 여기에 작성하세요 */
</style>
  