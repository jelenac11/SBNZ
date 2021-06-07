<template>
    <v-app-bar color="green darken-2" dark>
        <v-toolbar-title class="mx-4">BetterME</v-toolbar-title>
        <v-btn class="mx-4" outlined @click="goToHome()" v-if="currentUser.role ==='ROLE_REGISTERED_USER'">
            my nutrition plan
        </v-btn>
        <v-btn class="mr-4" outlined @click="goToRecipes()" v-if="currentUser.role ==='ROLE_REGISTERED_USER'">
            recipes
        </v-btn>
        <v-btn class="mr-4" outlined @click="goToAddMeal()" v-if="currentUser.role ==='ROLE_ADMIN'">
            add meal
        </v-btn>
        <v-btn class="mr-4" outlined @click="goToAddGrocery()" v-if="currentUser.role ==='ROLE_ADMIN'">
            add grocery
        </v-btn>
        <v-btn class="mr-4" outlined @click="goToAgeTemplate()" v-if="currentUser.role ==='ROLE_ADMIN'">
            age template
        </v-btn>
        <v-btn class="mr-4" outlined @click="goToCategoryTemplate()" v-if="currentUser.role ==='ROLE_ADMIN'">
            category template
        </v-btn>
        <v-spacer></v-spacer>
        <v-menu open-on-click transition="slide-y-transition" bottom left offset-y>
            <template v-slot:activator="{ on, attrs }">
                <v-btn style="margin-right: 1px;" class="account" icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-account</v-icon>
                </v-btn>
                <h2 class="mr-4" style="font-weight: 400;">
                    {{currentUser.username}}
                </h2>
            </template>
            <v-card class="mx-auto" max-width="300" tile>
                <v-list dense>
                    <v-subheader id="log-out" @click="logOut()" class="mr-2"><v-icon class="mr-2">mdi-exit-to-app</v-icon><b>Log out</b></v-subheader>
                </v-list>
            </v-card>
        </v-menu>
    </v-app-bar>
</template>

<script>
import { mapState } from 'vuex'

export default {

    name: 'Navbar',

    data: () => ({
    
    }),
    methods: {
        logOut: function() {
            this.$store.dispatch('logout')
            .then(() => {
                this.$router.push('/login')
            });
        },
        goToHome: function() {
            let path = '/';
            if (this.$route.path !== path) this.$router.push(path);
        },
        goToRecipes: function() {
            let path = '/recipes';
            if (this.$route.path !== path) this.$router.push(path);
        },
        goToAddMeal: function() {
            let path = '/add-meal';
            if (this.$route.path !== path) this.$router.push(path);
        },
        goToAddGrocery: function() {
            let path = '/add-grocery';
            if (this.$route.path !== path) this.$router.push(path);
        },
        goToAgeTemplate: function() {
            let path = '/age-template';
            if (this.$route.path !== path) this.$router.push(path);
        },
        goToCategoryTemplate: function() {
            let path = '/category-template';
            if (this.$route.path !== path) this.$router.push(path);
        },
    },
    computed: {
        ...mapState({
            currentUser: state => state.user
        }),
    },
    created: function () {
        this.$store.dispatch('getCurrentUser');
    }
};
</script>

<style scoped>
    #log-out {
        cursor: pointer;
    }
</style>