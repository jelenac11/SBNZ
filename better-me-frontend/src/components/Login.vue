<template>
    <v-container fluid fill-height class="login-container">
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md4>
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
        <v-snackbar v-model="wrong" top color="red darken-4">
            Wrong email or password. Try again.
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
        token: null
    }),
    created() {
		localStorage.removeItem("token");
	},
    methods : {
		login : function () {
            this.axios
            .post('http://localhost:8081/auth/login', this.userLogin)
            .then(response => {
                this.wrong = false;
                this.token = response.data.accessToken;
                localStorage.setItem("token", this.token);
                this.axios
                .get('http://localhost:8081/auth/current-user', { headers: { Authorization: 'Bearer ' + this.token }} )
                .then(response => {
                    console.log(response.data);
                    this.$router.replace({ name: 'Home' });
                })
                .catch(error => { console.log(error); });
            })
            .catch(error => {
                console.log(error);
                this.wrong = true;
            });
		},
    }
}
</script>
<style scoped>
    .login-container {
        background-image: url(../assets/222.png);
    }
</style>