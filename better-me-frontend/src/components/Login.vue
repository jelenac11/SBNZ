<template>
    <v-container fluid fill-height class="login-container">
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md3>
                <v-card class="elevation-12" style="padding: 20px">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Login</h1>
                        <v-form class="mt-4" v-model="isValid">
                            <v-text-field
                                color="green"
                                prepend-icon="person"
                                name="email"
                                label="Email"
                                type="text"
                                v-model="userLogin.email"
                                :rules="[v => !!v || 'Email is required']"
                                required
                            ></v-text-field>
                            <v-text-field
                                color="green"
                                id="password"
                                prepend-icon="lock"
                                name="password"
                                label="Password"
                                type="password"
                                v-model="userLogin.password"
                                :rules="[v => !!v || 'Password is required']"
                                required
                            ></v-text-field>
                        </v-form>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn 
                            color="green"
                            class="my-2" 
                            style="width: 95%; color: white;" 
                            :disabled="!isValid"
                            v-on:click="login"
                        >Login</v-btn>
                    </v-card-actions>
                    <router-link to="/register" class="ml-5" style="color: #4CAF50;">Don't have an account? Register here.</router-link>
                </v-card>
            </v-flex>
        </v-layout>
        <v-snackbar v-model="wrong" top color="red darken-3">
            Wrong email or password. Try again.
        </v-snackbar>
        <v-snackbar v-model="forbidden" top color="red darken-3">
            You can not login after three failed attempts. Try again after 5 minutes.
        </v-snackbar>
    </v-container>
</template>

<script>
  export default {
    name: 'Login',
    props: {
        source: String,
    },
    data: () => ({
        userLogin: {
            email: null,
            password: null,
        },
        isValid: true,
        wrong: false,
        forbidden: false,
        token: null
    }),
    created() {
		localStorage.removeItem("token");
	},
    methods : {
		login : function () {
            let email = this.userLogin.email;
            let password = this.userLogin.password;
            this.$store.dispatch('login', { email, password })
            .then(() => this.$router.push('/'))
            .catch(err => {
                if (err.response.status === 403) {
                    this.forbidden = true;
                } else {
                    this.wrong = true;
                }
            });
		},
    }
}
</script>
<style scoped>
    .login-container {
        background-image: url(../assets/666.png);
    }
</style>