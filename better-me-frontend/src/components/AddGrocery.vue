<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md6>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">New Grocery</h1>
                        <v-form class="mt-4" v-model="isValid" ref="formAddNewGrocery">
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="edit"
                                            name="name"
                                            label="Name"
                                            type="text"
                                            v-model="grocery.name"
                                            :rules="[v => !!v || 'Name is required']"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-select
                                            color="green"
                                            prepend-icon="restaurant"
                                            v-model="grocery.diet"
                                            :items="dietItems"
                                            label="Diet"
                                            data-vv-name="diet"
                                            :rules="[v => !!v || 'Diet is required']"
                                            required
                                        ></v-select>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="food_bank"
                                            name="calories"
                                            label="Calories"
                                            type="number"
                                            step=.1
                                            v-model="grocery.calories"
                                            :rules="[v => !!v || 'Calories are required']"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="rice_bowl"
                                            name="protein"
                                            label="Protein"
                                            type="number"
                                            step=.1
                                            v-model="grocery.proteins"
                                            :rules="[v => !!v || 'Protein is required']"
                                            suffix="g"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="cake"
                                            name="carbs"
                                            label="Carbs"
                                            type="number"
                                            step=.1
                                            v-model="grocery.carbs"
                                            :rules="[v => !!v || 'Carbs are required']"
                                            suffix="g"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="lunch_dining"
                                            name="Fats"
                                            label="Fats"
                                            type="number"
                                            step=.1
                                            v-model="grocery.fats"
                                            :rules="[v => !!v || 'Fats are required']"
                                            suffix="g"
                                            required
                                        ></v-text-field>
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
                            v-on:click="addNewGrocery"
                        >Add</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
        <v-snackbar v-model="wrong" top color="red darken-4">
            Grocery with given name already exists.
        </v-snackbar>
        <v-snackbar v-model="success" top color="green">
            Grocery added successfully.
        </v-snackbar>
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
            grocery: {
                name: null,
                diet: null,
                calories: null,
                proteins: null,
                carbs: null,
                fats: null
            },
            isValid: true,
            wrong: false,
            success: false,
            dietItems: ['Vegan', 'Vegetarian', 'Omnivore'],
            realDietItems: ['VEGAN', 'VEGETARIAN', 'OMNIVORE'],
        }),
        methods : {
            addNewGrocery : function () {
                const grocery = JSON.parse(JSON.stringify(this.grocery));
                grocery.diet = this.realDietItems[this.dietItems.indexOf(this.grocery.diet)];
                this.$store.dispatch('addNewGrocery', grocery)
                .then(resp => {
                    if (resp.data.statusCodeValue == 409) {
                        this.wrong = true;
                    } else {
                        this.wrong = false;
                        this.success = true;
                        this.$refs.formAddNewGrocery.reset();
                        this.grocery = {
                            name: null,
                            diet: null,
                            calories: null,
                            proteins: null,
                            carbs: null,
                            fats: null
                        };
                    }
                })
                .catch(() => {
                    this.wrong = true;
                });
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