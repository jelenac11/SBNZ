<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md6>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">New Meal</h1>
                        <v-form class="mt-4 mr-2" v-model="isValid" ref="formAddNewMeal">
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="fastfood"
                                            name="name"
                                            label="Name"
                                            type="text"
                                            v-model="meal.name"
                                            :rules="[v => !!v || 'Name is required']"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="timer"
                                            name="time"
                                            label="Preparation time"
                                            type="number"
                                            step=1
                                            v-model="meal.time"
                                            :rules="timeRules"
                                            suffix="m"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12" sm="6" md="12" class="mt-n7">
                                        <v-textarea
                                            label="Description"
                                            no-resize
                                            counter
                                            clearable
                                            clear-icon="mdi-close-circle"
                                            prepend-icon="notes"
                                            rows="2"
                                            v-model="meal.description"
                                            :rules="[v => !!v || 'Description is required']"
                                            required
                                        ></v-textarea>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-form>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn 
                            color="green"
                            class="my-2" 
                            style="width: 95%; color: white;" 
                            :disabled="!isValid"
                            v-on:click="addNewMeal"
                        >Add</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import Navbar from './Navbar.vue'
    import { mapState } from 'vuex'

    export default {
        name: 'Home',
        components: {
            Navbar
        },
        data: () => ({
            meal: {
                name: null,
                time: null,
                description: null,
                ingredients: []
            },
            isValid: true,
            timeRules: [ 
                v => !!v || 'Preparation time is required!', 
                v => (v && v >= 1 && !v.startsWith(0) && !v.includes('.')) || 'Preparation time is not valid!' 
            ],
        }),
        methods : {
            addNewMeal : function () {
                console.log(this.meal);
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
    }
</script>

<style scoped>
    .home-container {
        background-image: url(../assets/222.png);
        height: 100%;
    }
</style>