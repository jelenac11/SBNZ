import simulator from "./simulator";
import Vue from 'vue'
import Vuex from "vuex";
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(Vuex)
Vue.use(VueAxios, axios)

Vue.prototype.$axios = axios;
const token = localStorage.getItem('token')
if (token) {
    Vue.prototype.$axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
}

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('token') || '',
        user: {},
        allMeals: [],
        allGroceries: [],
    },
    getters : {
        authenticated: state => !!state.token,
        currentUser: state => state.user,
    },
    mutations: {
        setMeals (state, meals) {
            state.allMeals = meals;
        },

        auth_success_token(state, token) {
            state.token = token;
        },

        auth_success_user(state, user) {
            state.user = user;
        },

        logout(state) {
            localStorage.removeItem('token');
            state.token = '';
            state.user = {};
        },
    },
    actions: {
        getAllMeals ({ commit }) {
            simulator.getMeals(meals => {
              commit('setMeals', meals);
            });
        },

        login({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/auth/login', data: user, method: 'POST' })
                .then(resp => {
                    const token = resp.data.token;
                    const user = resp.data.user;
                    localStorage.setItem('token', token);
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                    commit('auth_success_token', token);
                    commit('auth_success_user', user);
                    resolve(resp);
                })
                .catch(err => {
                    localStorage.removeItem('token');
                    reject(err);
                });
            });
        },

        register({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/auth/sign-up', data: user, method: 'POST' })
                .then(resp => resolve(resp))
                .catch(err => {
                    reject(err);
                    commit('logout');
                });
            });
        },

        logout({ commit }) {
            return new Promise((resolve) => {
                commit('logout');
                localStorage.removeItem('token');
                delete Vue.prototype.$axios.defaults.headers.common['Authorization'];
                resolve();
            });
        },

        getCurrentUser({ commit }) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/auth/current-user', method: 'GET' })
                .then(resp => {
                    const user = resp.data;
                    commit('auth_success_user', user);
                    resolve(resp);
                })
                .catch(err => {
                    commit('logout');
                    reject(err);
                });
            });
        },
    },
    modules: {}
});
