import PostWrite from "/src/views/post/PostWrite_yh.vue";

export default [
  {
    path: "/postwrite/:resultId",
    name: "PostWriteWithAnalyzeResult",
    component: PostWrite,
    props: true,
  },
];
