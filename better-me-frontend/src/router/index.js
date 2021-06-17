import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../components/Home.vue";
import Login from "../components/Login.vue";
import Recipes from "../components/Recipes.vue";
import Register from "../components/Register.vue";
import AddMeal from "../components/AddMeal.vue";
import AddGrocery from "../components/AddGrocery.vue";
import AgeTemplate from "../components/AgeTemplate.vue";
import CategoryTemplate from "../components/CategoryTemplate.vue";
import AdminMeals from "../components/AdminMeals.vue";
import AdminGroceries from "../components/AdminGroceries.vue";
import AdminReport from "../components/AdminReport.vue";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/recipes",
    name: "Recipes",
    component: Recipes
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/register",
    name: "Register",
    component: Register
  },
  {
    path: "/add-meal",
    name: "AddMeal",
    component: AddMeal
  },
  {
    path: "/add-grocery",
    name: "AddGrocery",
    component: AddGrocery
  },
  {
    path: "/age-template",
    name: "AgeTemplate",
    component: AgeTemplate
  },
  {
    path: "/category-template",
    name: "CategoryTemplate",
    component: CategoryTemplate
  },
  {
    path: "/all-meals",
    name: "AdminMeals",
    component: AdminMeals
  },
  {
    path: "/all-groceries",
    name: "AdminGroceries",
    component: AdminGroceries
  },
  {
    path: "/admin-report",
    name: "AdminReport",
    component: AdminReport
  },
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if ((to.name !== 'Login' && to.name !== 'Register') && !store.getters.authenticated) next({ name: 'Login' })
  else next()
  if ((to.name === 'Login' || to.name === 'Register') && store.getters.authenticated) next({ name: from.name })
  else next()

  if ((to.name === 'Home' || to.name === 'Recipes') && store.state.user.role === 'ROLE_ADMIN') next({ name: 'AdminReport' })
  else next()

  if ((to.name === 'AddMeal' || to.name === 'AddGrocery' || to.name === 'AgeTemplate' || to.name === 'CategoryTemplate' || to.name === 'AdminMeals' || to.name === 'AdminGroceries' || to.name === 'AdminReport') && store.state.user.role === 'ROLE_REGISTERED_USER') next({ name: 'Home' })
  else next()
})

export default router;
