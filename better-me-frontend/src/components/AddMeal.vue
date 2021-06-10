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
                                <v-form v-model="isValidNewIngredient" ref="formAddNewIngredient">
                                    <v-row class="mt-8">
                                        <v-col cols="12" sm="6" md="5" class="mt-n7">
                                            <v-select
                                                color="green"
                                                prepend-icon="lunch_dining"
                                                v-model="newIngredient.grocery.name"
                                                :items="groceryNames"
                                                label="Ingredient"
                                                :rules="[v => !!v || 'Ingredient is required']"
                                                required
                                            ></v-select>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4" class="mt-n7">
                                            <v-text-field
                                                color="green"
                                                prepend-icon="monitor_weight"
                                                label="Grams"
                                                type="number"
                                                step=1
                                                v-model="newIngredient.grams"
                                                :rules="gramsRules"
                                                suffix="g"
                                                required
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="3" class="mt-n7">
                                            <v-btn
                                                class="mt-3"
                                                color="green"
                                                style="width: 100%; color: white;"
                                                :disabled="!isValidNewIngredient"
                                                v-on:click="addNewIngredient"
                                            >Add ingredient</v-btn>
                                        </v-col>
                                    </v-row>
                                </v-form>
                                <v-row>
                                    <v-col cols="12" sm="6" md="12" class="mt-n3">
                                        <v-select
                                            color="green"
                                            prepend-icon="fastfood"
                                            v-model="ingredientsAdded"
                                            :items="ingredientsAvailable"
                                            label="Added Ingredients"
                                            attach
                                            chips
                                            multiple
                                            @input='ingredientsChanged'
                                        >
                                        </v-select>
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
        <v-snackbar v-model="wrongIngredients" top color="red darken-4">
            You must add at least one ingredient.
        </v-snackbar>
        <v-snackbar v-model="wrong" top color="red darken-4">
            Meal with given name already exists.
        </v-snackbar>
        <v-snackbar v-model="success" top color="green">
            Meal added successfully.
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
            meal: {
                name: null,
                time: null,
                description: null,
                ingredients: []
            },
            wrongIngredients: false,
            wrong: false,
            success: false,
            groceryNames: [],
            ingredientsAdded: [],
            ingredientsAvailable: [],
            newIngredient: {
                grocery: {
                    name: null,
                },
                grams: null,
            },
            isValid: true,
            isValidNewIngredient: true,
            timeRules: [ 
                v => !!v || 'Preparation time is required!', 
                v => (v && v >= 1 && !v.startsWith(0) && !v.includes('.')) || 'Preparation time is not valid!' 
            ],
            gramsRules: [
                v => (v && v >= 1 && !v.startsWith(0) && !v.includes('.')) || 'Grams are not valid!' 
            ],
        }),
        methods : {
            addNewMeal : function () {
                if (this.meal.ingredients.length == 0) {
                    this.wrongIngredients = true;
                } else {
                    const meal = JSON.parse(JSON.stringify(this.meal));
                    this.$store.dispatch('addNewMeal', meal)
                    .then(() => {
                        this.wrong = false;
                        this.success = true;
                        this.$refs.formAddNewMeal.reset();
                        this.meal = {
                            name: null,
                            time: null,
                            description: null,
                            ingredients: []
                        };
                    })
                    .catch(() => {
                        this.wrong = true;
                    });
                }
            },
            addNewIngredient : function () {
                let grocery = this.groceries[this.allGroceryNames.indexOf(this.newIngredient.grocery.name)];
                this.newIngredient.grocery = grocery;
                this.meal.ingredients.push(this.newIngredient);
                this.newIngredient = {
                    grocery: {
                        name: null,
                    },
                    grams: null,
                };
                this.$refs.formAddNewIngredient.reset();
                this.groceryNames = this.groceryNames.filter(elem => elem != grocery.name);
                this.ingredientsAdded.push(grocery.name);
                this.ingredientsAvailable.push(grocery.name);
            },
            ingredientsChanged : function () {
                this.ingredientsAvailable = this.ingredientsAvailable.filter(elem => {
                    if (this.ingredientsAdded.indexOf(elem) === -1) {
                        this.groceryNames.push(elem);
                        this.meal.ingredients = this.meal.ingredients.filter(ingr => ingr.grocery.name != elem);
                    } else {
                        return elem;
                    }
                });
            },
        },
        computed: {
            ...mapState({
                currentUser: state => state.user,
                groceries: state => state.allGroceries,
                allGroceryNames: state => state.allGroceries.map(a => a.name),
            }),
        },
        created: function () {
            this.$store.dispatch('getCurrentUser');
            this.$store.dispatch('getAllGroceriesAdmin').then(resp => {
                this.groceryNames = resp.data.map(a => a.name);
            });
        }
    }
</script>

<style scoped>
    .home-container {
        background-image: url(../assets/222.png);
        height: 100%;
    }
</style>