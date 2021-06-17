<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md6>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Age Template</h1>
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
                            :min="1"
                            :max="120"
                            :interval="1"
                        ></vue-slider>
                        <h2 style="margin-top: 60px; margin-left: 10px"><span style="color: #388E3C;">Child</span><span>: {{value[0]}} - {{value[1]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #880E4F;">Teen</span><span>: {{value[1]}} - {{value[2]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #1976D2;">Young Adult</span><span>: {{value[2]}} - {{value[3]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #FFA726;">Adult</span><span>: {{value[3]}} - {{value[4]}}</span></h2>
                        <h2 style="margin-top: 15px; margin-left: 10px"><span style="color: #D32F2F;">Elder</span><span>: {{value[4]}} - {{value[5]}}</span></h2>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn 
                            color="green"
                            class="my-2" 
                            style="width: 95%; color: white;" 
                            :disabled="!isValid"
                            v-on:click="addNewAgeTemplate"
                        >Submit</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
        <v-snackbar v-model="success" top color="green darken-4">
            Age template changed successfully.
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
            value: [1, 13, 20, 30, 60, 120],
            options: {
                process: () => [],
                enableCross: false
            },
            marks: [1, 20, 40, 60, 80, 100, 120],
            dotOptions: [{ disabled: true }, { disabled: false }, { disabled: false }, { disabled: false }, { disabled: false }, { disabled: true }],
            process1: dotsPos => [
                [dotsPos[0], dotsPos[1], { backgroundColor: '#388E3C' }],
                [dotsPos[1], dotsPos[2], { backgroundColor: '#880E4F' }],
                [dotsPos[2], dotsPos[3], { backgroundColor: '#1976D2' }],
                [dotsPos[3], dotsPos[4], { backgroundColor: '#FFA726' }],
                [dotsPos[4], dotsPos[5], { backgroundColor: '#D32F2F' }],
            ],
            success: false,
        }),
        methods : {
            addNewAgeTemplate : function () {
                let ageBoundaries = { teenFrom: this.value[1], youngAdultFrom: this.value[2], adultFrom: this.value[3], elderFrom: this.value[4] };
                console.log(ageBoundaries);
                this.$store.dispatch('addNewAgeTemplate', ageBoundaries)
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