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
        allergens: [],
        bodyType: '',
    },
    getters : {
        authenticated: state => !!state.token,
        currentUser: state => state.user,
    },
    mutations: {
        setMeals (state, meals) {
            state.allMeals = meals;
        },

        setAllergens (state, allergens) {
            state.allergens = allergens;
        },

        setGroceries (state, groceries) {
            state.allGroceries = groceries;
        },

        setBodyType (state, bodyType) {
            state.bodyType = bodyType;
        },

        auth_success_token(state, token) {
            state.token = token;
        },

        setCurrentUser(state, user) {
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
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/filter/meals', data: { name: '', fromTime: 0, toTime: 0, descending: false }, method: 'POST' })
                .then(resp => {
                    commit('setMeals', resp.data.sorted);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        getAllergens ({ commit }) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/nutrition/all-allergens', method: 'GET' })
                .then(resp => {
                    commit('setAllergens', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        getAllGroceriesAdmin ({ commit }) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/nutrition/all-groceries', method: 'GET' })
                .then(resp => {
                    commit('setGroceries', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        determineBodyType({ commit }, bodyType) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/body-type/determine', data: bodyType, method: 'POST' })
                .then(resp => {
                    commit('setBodyType', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
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
                    commit('setCurrentUser', user);
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

        fillInfo({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/registered-users/fill-info', data: user, method: 'POST' })
                .then(resp => {
                    const user = resp.data;
                    commit('setCurrentUser', user);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        addNewGrocery({ commit }, grocery) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/nutrition/new-grocery', data: grocery, method: 'POST' })
                .then(resp => {
                    commit('setGroceries', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        addNewMeal({ commit }, meal) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/meals', data: meal, method: 'POST' })
                .then(resp => {
                    commit('setAllergens', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        addNewAgeTemplate({ commit }, ageBoundaries) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/age/change-age-boundaries', data: ageBoundaries, method: 'POST' })
                .then(resp => {
                    commit('setGroceries', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
                });
            });
        },

        addNewCategoryTemplate({ commit }, categoryBoundaries) {
            return new Promise((resolve, reject) => {
                axios({url: 'http://localhost:8081/api/categories/change-boundaries', data: categoryBoundaries, method: 'POST' })
                .then(resp => {
                    commit('setGroceries', resp.data);
                    resolve(resp);
                })
                .catch(err => {
                    reject(err);
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
                    commit('setCurrentUser', user);
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
