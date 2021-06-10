<template>
    <div class="recipes-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md10>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px; margin-bottom: 40px;">
                    <v-card-text>
                        <v-container fluid>
                            <v-data-iterator
                                :items="meals"
                                :items-per-page.sync="itemsPerPage"
                                :page.sync="page"
                                :search="search"
                                :sort-by="sortBy.toLowerCase()"
                                :sort-desc="sortDesc"
                                hide-default-footer
                            >
                                <template v-slot:header>
                                    <v-toolbar dark color="green" class="mb-6">
                                        <v-text-field
                                            v-model="search"
                                            clearable
                                            flat
                                            hide-details
                                            prepend-inner-icon="mdi-magnify"
                                            class="ml-2"
                                            label="Search"
                                        ></v-text-field>
                                        <template v-if="$vuetify.breakpoint.mdAndUp">
                                            <v-spacer></v-spacer>
                                            <v-select
                                                v-model="sortBy"
                                                flat
                                                hide-details
                                                :items="keys"
                                                prepend-inner-icon="mdi-magnify"
                                                label="Sort by"
                                                class="mr-5"
                                            ></v-select>
                                            <v-btn-toggle v-model="sortDesc" mandatory>
                                            <v-btn large depressed color="green" :value="false">
                                                <v-icon>mdi-arrow-up</v-icon>
                                            </v-btn>
                                            <v-btn large depressed color="green" :value="true">
                                                <v-icon>mdi-arrow-down</v-icon>
                                            </v-btn>
                                            </v-btn-toggle>
                                        </template>
                                    </v-toolbar>
                                </template>

                                <template v-slot:default="props">
                                    <v-row>
                                        <v-col v-for="item in props.items" :key="item.name" cols="12" sm="6" md="4" lg="3">
                                            <v-card>
                                                <v-card-title class="subheading font-weight-bold orange--text" v-if="item.allergen">
                                                    {{ item.name }}<v-icon class="ml-2" color="orange" medium>warning</v-icon>
                                                </v-card-title>
                                                <v-card-title class="subheading font-weight-bold green--text" v-else>
                                                    {{ item.name }}
                                                </v-card-title>
                                                <v-divider></v-divider>
                                                <v-list dense>
                                                    <v-list-item v-for="(key, index) in filteredKeys" :key="index">
                                                        <v-list-item-content :class="{ 'green--text': sortBy === key }" v-if="key !== 'Description'">
                                                            {{ key }}:
                                                        </v-list-item-content>
                                                        <v-list-item-content class="align-end" :class="{ 'green--text': sortBy === key }" v-if="key !== 'Description'">
                                                            <span>
                                                                <b v-if="key !== 'Description'">
                                                                    {{ item[key.toLowerCase()] }}<span v-if="key === 'Proteins' || key === 'Carbs' || key === 'Fats'">g</span><span v-if="key === 'Time'">m</span>
                                                                </b>
                                                                <span v-else>{{ item[key.toLowerCase()] }}</span>
                                                            </span>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                    <p class="ml-4 mr-6 mt-n7" style="text-justify: inter-word;">{{ item["description"] }}</p>
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

    export default {
        name: 'Recipes',
        components: {
            Navbar
        },
        data: () => ({
            search: '',
            filter: {},
            sortDesc: false,
            page: 1,
            itemsPerPage: 4,
            sortBy: 'name',
            keys: [
                'Name',
                'Time',
                'Calories',
                'Fats',
                'Carbs',
                'Proteins',
                'Description',
            ],
        }),
        computed: {
            numberOfPages () {
                return Math.ceil(this.meals.length / this.itemsPerPage)
            },
            filteredKeys () {
                return this.keys.filter(key => key !== 'Name')
            },
            ...mapState({
                meals: state => state.allMeals
            }),
        },
        methods: {
            nextPage () {
                if (this.page + 1 <= this.numberOfPages) this.page += 1
            },
            formerPage () {
                if (this.page - 1 >= 1) this.page -= 1
            },
            updateItemsPerPage (number) {
                this.itemsPerPage = number
            },
        },
        created () {
            this.$store.dispatch('getAllMeals');
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