<template>
    <div class="recipes-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md11>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px; margin-bottom: 40px;">
                    <v-card-text>
                        <v-container fluid>
                            <v-data-iterator
                                :items="meals"
                                :items-per-page.sync="itemsPerPage"
                                :page.sync="page"
                                hide-default-footer
                            >
                                <template v-slot:header>
                                    <v-toolbar dark color="green" class="mb-6">
                                        <v-text-field
                                            v-model="search"
                                            flat
                                            hide-details
                                            prepend-inner-icon="mdi-magnify"
                                            class="ml-2"
                                            label="Search by name"
                                        ></v-text-field>
                                        <template v-if="$vuetify.breakpoint.mdAndUp">
                                            <v-spacer></v-spacer>
                                            <v-text-field
                                                v-model="timeFrom"
                                                type="number"
                                                step=1
                                                clearable
                                                flat
                                                hide-details
                                                prepend-inner-icon="timer"
                                                class="ml-2"
                                                label="Time from"
                                            ></v-text-field>
                                            <v-text-field
                                                v-model="timeTo"
                                                type="number"
                                                step=1
                                                clearable
                                                flat
                                                hide-details
                                                prepend-inner-icon="timer"
                                                class="ml-2"
                                                label="Time to"
                                            ></v-text-field>
                                            <v-btn large depressed color="green" class="ml-2" @click="searchMeals()">
                                                Search
                                            </v-btn>
                                            <v-btn large depressed color="green" @click="descending(false)">
                                                <v-icon>mdi-arrow-up</v-icon>
                                            </v-btn>
                                            <v-btn large depressed color="green" @click="descending(true)">
                                                <v-icon>mdi-arrow-down</v-icon>
                                            </v-btn>
                                        </template>
                                    </v-toolbar>
                                </template>

                                <template v-slot:default="props">
                                    <v-row>
                                        <v-col v-for="item in props.items" :key="item.name" cols="12" sm="6" md="4" lg="3">
                                            <v-card class="pb-2">
                                                <v-card-title class="subheading font-weight-bold orange--text" v-if="item.allergen">
                                                    {{ item.name }}<v-icon class="ml-2" color="orange" medium>warning</v-icon>
                                                    <star-rating class="ml-4 mt-1" v-model="item.currentRating" :show-rating="false" @rating-selected="setRating(item)" :star-size="22"></star-rating>
                                                    <span class="black--text ml-2">{{ item.averageGrade }}</span>
                                                </v-card-title>
                                                <v-card-title class="subheading font-weight-bold green--text" v-else>
                                                    {{ item.name }}
                                                    <star-rating class="ml-4 mt-1" v-model="item.currentRating" :show-rating="false" @rating-selected="setRating(item)" :star-size="22"></star-rating>
                                                    <span class="black--text ml-2">{{ item.averageGrade }}</span>
                                                </v-card-title>
                                                <v-divider></v-divider>
                                                <v-list dense>
                                                    <v-list-item-content>
                                                        <span class="ml-4">Time: <b>{{ item.time }}m</b></span>
                                                    </v-list-item-content>
                                                    <v-list-item-content>
                                                        <span class="ml-4">Calories: <b>{{ item.calories }}</b></span>
                                                    </v-list-item-content>
                                                    <v-list-item-content>
                                                        <span class="ml-4">Fats: <b>{{ item.fats }}g</b></span>
                                                    </v-list-item-content>
                                                    <v-list-item-content>
                                                        <span class="ml-4">Carbs: <b>{{ item.carbs }}g</b></span>
                                                    </v-list-item-content>
                                                    <v-list-item-content>
                                                        <span class="ml-4">Proteins: <b>{{ item.proteins }}g</b></span>
                                                    </v-list-item-content>
                                                    <p class="ml-4 mr-6 mt-3" style="text-justify: inter-word;">Ingredients: <b>{{ printIngredients(item) }}</b></p>
                                                    <p class="ml-4 mr-6 mt-3" style="text-justify: inter-word;">{{ item.description }}</p>
                                                </v-list>
                                            </v-card>
                                        </v-col>
                                    </v-row>
                                </template>

                                <template v-slot:footer>
                                    <v-row class="mt-3" align="center" justify="center">
                                        <v-spacer></v-spacer>
                                        <span class="mr-4 grey--text">
                                            Page {{ page }} of {{ numberOfPages }}
                                        </span>
                                        <v-btn dark color="green" class="mr-1" @click="formerPage">
                                            <v-icon>mdi-chevron-left</v-icon>
                                        </v-btn>
                                        <v-btn dark color="green" class="ml-1" @click="nextPage">
                                            <v-icon>mdi-chevron-right</v-icon>
                                        </v-btn>
                                    </v-row>
                                </template>

                            </v-data-iterator>
                        </v-container>

                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import Navbar from './Navbar.vue'
    import { mapState } from 'vuex'
    import StarRating from 'vue-star-rating'

    export default {
        name: 'Recipes',
        components: {
            Navbar,
            StarRating
        },
        data: () => ({
            search: '',
            timeTo: null,
            timeFrom: null,
            desc: false,
            page: 1,
            itemsPerPage: 4,
            filter: { name: '', fromTime: 0, toTime: 0, descending: false },
            mealsItems: [],
        }),
        computed: {
            numberOfPages () {
                return Math.ceil(this.meals.length / this.itemsPerPage)
            },
            ...mapState({
                mealsState: state => state.allMeals
            }),
            meals () {
                return this.mealsItems;
            },
        },
        methods: {
            searchMeals () {
                this.filter.name = this.search;  
                if (this.timeTo == null) {
                    this.filter.toTime = -1
                } else {
                     this.filter.toTime = Math.round(this.timeTo);
                }
                this.filter.fromTime = Math.round(this.timeFrom);
                this.filter.descending = this.desc;
                this.$store.dispatch('getAllMeals', this.filter).then(resp => {
                    this.mealsItems = resp.data.sorted;
                    this.mealsItems.forEach(element => {
                        this.$store.dispatch('getRate', element.name).then(resp => {
                            this.$set(this.mealsItems[this.mealsItems.indexOf(element)], 'currentRating', resp.data.value)
                        });
                    });
                });
            },
            descending (des) {
                this.desc = des;
                this.searchMeals();
            },
            nextPage () {
                if (this.page + 1 <= this.numberOfPages) this.page += 1
            },
            formerPage () {
                if (this.page - 1 >= 1) this.page -= 1
            },
            updateItemsPerPage (number) {
                this.itemsPerPage = number
            },
            setRating: function(meal) {
                this.$store.dispatch('rateMeal', meal).then(resp => {
                    this.mealsItems[this.mealsItems.indexOf(meal)].averageGrade = resp.data;
                });
            },
            printIngredients : function (meal) {
                let ings = "";
                for (let i = 0; i < meal.ingredients.length; ++i) {
                    ings += meal.ingredients[i].grocery.name + ", ";
                }
                return ings.slice(0, ings.length - 2);
            },
        },
        created () {
            this.$store.dispatch('getAllMeals', this.filter).then(resp => {
                this.mealsItems = resp.data.sorted;
                this.mealsItems.forEach(element => {
                    this.$store.dispatch('getRate', element.name).then(resp => {
                        this.$set(this.mealsItems[this.mealsItems.indexOf(element)], 'currentRating', resp.data.value)
                    });
                });
            });
        }
    }
</script>

<style scoped>
    .recipes-container {
        background-image: url(../assets/222.png);
        background-repeat: repeat-y;
        height: 100%;
    }
</style>