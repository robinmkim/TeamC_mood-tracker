import PostWrite from "/src/views/post/PostWrite_yh.vue";

export default [
  {
    path: "/postwrite/:lastResultId",
    name: "PostWriteWithAnalyzeResult",
    component: PostWrite,
    props: true,
  },
];
