import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    redirect: "/index",
    name: "layout",
    component: () => import("@/views/layout/index"),
    children: [
      {
        path: "/index",
        name: "Index",
        component: () => import("@/views/index/index")
      },
      {
        path: "/blogList/:type",
        name: "BlogList",
        component: () => import("@/views/blog/blog-list")
      },
      {
        path: "/note/list",
        name: "NoteList",
        component: () => import("@/views/note/note-list")
      },
      {
        path: "/note/detail/:id",
        name: "NoteDetail",
        component: () => import("@/views/note/note-detail")
      },
      {
        path: "/about",
        name: "About",
        component: () => import("@/views/about/index")
      },
      {
        path: "/util/YGOJammingRate",
        name: "/util/YGOJammingRate",
        component: () => import("@/views/util/YGO-jamming-rate")
      },
      {
        path: "/info/:id",
        name: "Info",
        component: () => import("@/views/blog/index")
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
