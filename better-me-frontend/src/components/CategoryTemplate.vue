<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md6>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Category Template</h1>
                        <vue-slider
                            class="mt-12"
                            ref="slider"
                            v-model="value"
                            v-bind="options"
                            :min-range="1"
                            :marks="marks"
                            :dot-options="dotOptions"
                            :process="process1"
                            :tooltip="'active'"
                            :use-keyboard="false"
                            :min="0"
                            :max="5000"
                            :interval="10"
                        ></vue-slider>
                        <h2 style="margin-top: 60px; margin-left: 10px"><span style="color: #388E3C;">Beginner</span><span>: {{value[0]}} - {{value[1]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #FFA726;">Intermediate</span><span>: {{value[1]}} - {{value[2]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #1976D2;">Advanced</span><span>: {{value[2]}} - {{value[3]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #D32F2F;">Pro</span><span>: {{value[3]}} - {{value[4]}}</span></h2>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn 
                            color="green"
                            class="my-2" 
                            style="width: 95%; color: white;" 
                            :disabled="!isValid"
                            v-on:click="addNewCategoryTemplate"
                        >Submit</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
        <v-snackbar v-model="success" top color="green darken-4">
            Category template changed successfully.
        </v-snackbar>
    </div>
</template>

<script>
    import Navbar from './Navbar.vue'
    import { mapState } from 'vuex'
    import VueSlider from 'vue-slider-component'
    import 'vue-slider-component/theme/default.css'

    export default {
        name: 'Home',
        components: {
            Navbar,
            VueSlider
        },
        data: () => ({
            isValid: true,
            value: [0, 200, 500, 2000, 5000],
            options: {
                process: () => [],
                enableCross: false
            },
            marks: {
                '0': '0',
                '5000': '5000+'
            },
            dotOptions: [{ disabled: true }, { disabled: false }, { disabled: false }, { disabled: false }, { disabled: true }],
            process1: dotsPos => [
                [dotsPos[0], dotsPos[1], { backgroundColor: '#388E3C' }],
                [dotsPos[1], dotsPos[2], { backgroundColor: '#FFA726' }],
                [dotsPos[2], dotsPos[3], { backgroundColor: '#1976D2' }],
                [dotsPos[3], dotsPos[4], { backgroundColor: '#D32F2F' }],
            ],
            success: false,
        }),
        methods : {
            addNewCategoryTemplate : function () {
                let categoryBoundaries = { intermediateFrom: this.value[1], advancedFrom: this.value[2], proFrom: this.value[3] };
                console.log(categoryBoundaries);
                this.$store.dispatch('addNewCategoryTemplate', categoryBoundaries)
                .then(resp => {
                    console.log(resp.data);
                    this.success = true;
                })
                .catch(err => {
                    console.log(err);
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