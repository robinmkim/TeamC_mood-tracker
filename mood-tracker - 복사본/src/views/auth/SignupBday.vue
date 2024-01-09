<template>
  <div class="flex flex-col items-center justify-center h-full p-6">
    <div class="bg-white rounded-lg p-8 shadow-md w-full max-w-sm">
      <h2 class="text-center text-2xl font-semibold mb-6">Basic Information</h2>
      <form @submit.prevent="onNextClick" class="space-y-4">
        <div>
          <label
            for="month"
            class="block mb-2 text-sm font-medium text-gray-900"
            >Month</label
          >
          <select
            id="month"
            v-model="month"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
          >
            <option value="">Select a month</option>
            <option v-for="month in months" :key="month" :value="month">
              {{ month }}
            </option>
          </select>
          <span v-if="monthError">{{ monthError }}</span>
        </div>
        <div>
          <label for="day" class="block mb-2 text-sm font-medium text-gray-900"
            >Day</label
          >
          <select
            id="day"
            v-model="day"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
          >
            <!-- Day options -->
            <option value="">Select a month</option>
            <option v-for="day in days" :key="day" :value="day">
              {{ day }}
            </option>
          </select>
          <span v-if="dayError">{{ dayError }}</span>
        </div>
        <div>
          <label for="year" class="block mb-2 text-sm font-medium text-gray-900"
            >Year</label
          >
          <select
            id="year"
            v-model="year"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
          >
            <!-- Year options -->
            <option value="">Select a month</option>
            <option v-for="year in years" :key="year" :value="year">
              {{ year }}
            </option>
          </select>
          <span v-if="yearError">{{ yearError }}</span>
        </div>
        <div>
          <label
            for="gender"
            class="block mb-2 text-sm font-medium text-gray-900"
            >Gender</label
          >
          <select
            id="gender"
            v-model="gender"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
          >
            <option value="">Select...</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
            <option value="other">Other</option>
          </select>
          <span v-if="genderError">{{ genderError }}</span>
        </div>
        <button
          type="submit"
          class="bg-[#64CCC5] text-white p-2 rounded-lg shadow hover:bg-[#3f827e]"
        >
          Next
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
      console.log(state.userInfo);
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
      console.log(formattedBirthday.value);
      goToNextStep();
    });

    const goToNextStep = () => {
      router.push({
        name: "SignupSuccess",
        state: {
          userInfo: {
            ...userInfo.value,
            birthday: formattedBirthday.value,
            gender: gender.value,
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
