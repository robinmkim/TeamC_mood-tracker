<template>
  <div
    class="flex flex-col items-center justify-center h-full p-6 mt-[220px] w-[420px]"
  >
    <div class="bg-white rounded-lg p-8 shadow-md w-full max-w-sm">
      <h2 class="text-center text-2xl font-semibold mb-6">Basic Information</h2>
      <form @submit.prevent="onNextClick" class="space-y-4">
        <div class="flex flex-col">
          <div class="flex mb-2">
            <div class="mr-2">
              <label
                for="month"
                class="block mb-2 text-sm font-medium text-gray-900"
                >Month</label
              >
              <select
                id="month"
                v-model="month"
                class="border-2 border-gray-300 text-gray-900 text-sm rounded-lg focus:border-[#64CCC5] block w-[85px] p-2.5"
              >
                <option value="">Select a month</option>
                <option v-for="month in months" :key="month" :value="month">
                  {{ month }}
                </option>
              </select>
              <span v-if="monthError">{{ monthError }}</span>
            </div>
            <div class="">
              <label
                for="day"
                class="block mb-2 text-sm font-medium text-gray-900"
                >Day</label
              >
              <select
                id="day"
                v-model="day"
                class="border-2 border-gray-300 text-gray-900 text-sm rounded-lg focus:border-[#64CCC5] block w-[85px] p-2.5"
              >
                <!-- Day options -->
                <option value="">Select a day</option>
                <option v-for="day in days" :key="day" :value="day">
                  {{ day }}
                </option>
              </select>
              <span v-if="dayError">{{ dayError }}</span>
            </div>
            <div class="ml-2">
              <label
                for="year"
                class="block mb-2 text-sm font-medium text-gray-900"
                >Year</label
              >
              <select
                id="year"
                v-model="year"
                class="border-2 border-gray-300 text-gray-900 text-sm rounded-lg focus:border-[#64CCC5] block w-full p-2.5"
              >
                <!-- Year options -->
                <option value="">Select a year</option>
                <option v-for="year in years" :key="year" :value="year">
                  {{ year }}
                </option>
              </select>
              <span v-if="yearError">{{ yearError }}</span>
            </div>
          </div>

          <div>
            <div class="flex items-center mt-2">
              <label
                for="gender"
                class="block mb-1 ml-1 text-sm font-medium text-gray-900"
                >Gender</label
              >
              <!--s-->
              <div class="flex ml-11" id="gender">
                <button
                  type="button"
                  class="border-2 border-gray-300 mr-0 text-gray-900 text-sm rounded-l-lg block w-[75px] p-2.5"
                  @click="gender = 'male'"
                  :class="{
                    'bg-[#64CCC5] text-white': gender === 'male',
                  }"
                >
                  Male
                </button>
                <button
                  type="button"
                  class="border-t-2 border-b-2 border-gray-300 text-gray-900 text-sm block w-[75px] p-2.5"
                  @click="gender = 'female'"
                  :class="{
                    'bg-[#64CCC5] text-white': gender === 'female',
                  }"
                >
                  Female
                </button>
                <button
                  type="button"
                  class="border-2 border-gray-300 text-gray-900 text-sm rounded-r-lg block w-[75px] p-2.5"
                  @click="gender = 'other'"
                  :class="{
                    'bg-[#64CCC5] text-white': gender === 'other',
                  }"
                >
                  Other
                </button>
              </div>
              <!--s-->
            </div>
            <span v-if="genderError">{{ genderError }}</span>
          </div>
        </div>

        <button
          type="submit"
          class="bg-[#64CCC5] text-white p-2 rounded-lg shadow hover:bg-[#3f827e] w-full"
        >
          다음
        </button>
      </form>
    </div>
  </div>
</template>
<script>
import { useForm, useField } from "vee-validate";
import * as yup from "yup";
import { useRouter } from "vue-router";
import { ref, onMounted, computed } from "vue";

const currentYear = new Date().getFullYear();
const years = Array.from({ length: 100 }, (_, i) => String(currentYear - i));
const months = Array.from({ length: 12 }, (_, i) =>
  String(i + 1).padStart(2, "0")
);
const days = Array.from({ length: 31 }, (_, i) =>
  String(i + 1).padStart(2, "0")
);

export default {
  name: "SignupBday",
  setup() {
    const router = useRouter();
    const userInfo = ref({});
    onMounted(() => {
      const state = history.state;
      if (state) {
        userInfo.value = state.userInfo;
      }
    });

    const { handleSubmit, errors } = useForm({});

    const { value: month, errorMessage: monthError } = useField(
      "month",
      yup.string().required("월을 선택해주세요")
    );
    const { value: day, errorMessage: dayError } = useField(
      "day",
      yup.string().required("일을 선택해주세요")
    );
    const { value: year, errorMessage: yearError } = useField(
      "year",
      yup.string().required("년도를 선택해주세요")
    );
    const { value: gender, errorMessage: genderError } = useField(
      "gender",
      yup.string().required("성별을 선택해주세요")
    );

    const formattedBirthday = computed(() => {
      if (!year.value || !month.value || !day.value) {
        return "";
      }
      return `${year.value}-${month.value}-${day.value}`;
    });

    const onNextClick = handleSubmit(() => {
      goToNextStep();
    });

    const goToNextStep = () => {
      router.push({
        name: "SignupProfileImg",
        state: {
          userInfo: {
            ...userInfo.value,
            m_bdate: formattedBirthday.value,
            m_gender: gender.value,
          },
        },
      });
    };

    return {
      month,
      monthError,
      day,
      dayError,
      year,
      yearError,
      gender,
      genderError,
      formattedBirthday,
      errors,
      onNextClick,
      years,
      months,
      days,
    };
  },
};
</script>
<style lang=""></style>
