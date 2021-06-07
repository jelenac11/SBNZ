<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md10 v-if="currentUser.height !== 0">
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">My Nutrition Plan</h1>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        
                    </v-card-actions>
                </v-card>
            </v-flex>

            <v-flex xs12 sm8 md6 v-else>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Fill Information About Yourself</h1>
                        <v-form class="mt-4" v-model="isValid" ref="formFillInfo">
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-select
                                            color="green"
                                            prepend-icon="person"
                                            v-model="userInfo.sex"
                                            :items="genderItems"
                                            label="Gender"
                                            data-vv-name="sex"
                                            :rules="genderRules"
                                            required
                                        ></v-select>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="person"
                                            name="age"
                                            label="Age"
                                            type="number"
                                            step=1
                                            v-model="userInfo.age"
                                            :rules="ageRules"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="person"
                                            name="height"
                                            label="Height"
                                            type="number"
                                            step=.1
                                            v-model="userInfo.height"
                                            :rules="heightRules"
                                            suffix="cm"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-text-field
                                            color="green"
                                            prepend-icon="person"
                                            name="weight"
                                            label="Weight"
                                            type="number"
                                            step=.1
                                            v-model="userInfo.weight"
                                            :rules="weightRules"
                                            suffix="kg"
                                            required
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-select
                                            color="green"
                                            prepend-icon="person"
                                            v-model="userInfo.bodyType"
                                            :items="bodyTypeItems"
                                            label="Body Type"
                                            data-vv-name="bodyType"
                                            :rules="bodyTypeRules"
                                            :disabled="!bodyTypeDetermined"
                                            required
                                            @input='bodyTypeChanged'
                                        ></v-select>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-select
                                            color="green"
                                            prepend-icon="person"
                                            v-model="userInfo.activityLevel"
                                            :items="activityLevelItems"
                                            label="Activity Level"
                                            data-vv-name="activityLevel"
                                            :rules="activityLevelRules"
                                            required
                                        ></v-select>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-select
                                            color="green"
                                            prepend-icon="person"
                                            v-model="userInfo.diet"
                                            :items="dietItems"
                                            label="Diet"
                                            data-vv-name="diet"
                                            :rules="dietRules"
                                            required
                                        ></v-select>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-form>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn 
                            color="green"
                            class="mb-2 mt-n3" 
                            style="width: 95%; color: white;" 
                            :disabled="!isValid"
                            v-on:click="fillInfo"
                        >Finish</v-btn>
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
            userInfo: {
                sex: null,
                age: null,
                height: null,
                weight: null,
                bodyType: null,
                activityLevel: null,
                diet: null,
                allergens: null,
            },
            bodyTypeDetermined: true,
            genderItems: ['MALE', 'FEMALE'],
            bodyTypeItems: ['ECTOMORPH', 'MESOMORPH', 'ENDOMORPH', 'I am not sure'],
            activityLevelItems: ['INACTIVE', 'SEDENTARY', 'LIGHTLY_ACTIVE', 'MODERATELY_ACTIVE', 'VERY_ACTIVE'],
            dietItems: ['VEGAN', 'VEGETARIAN', 'OMNIVORE'],
            isValid: true,
            genderRules: [ 
                v => !!v || 'Gender is required!',
            ],
            ageRules: [ 
                v => !!v || 'Age is required!', 
                v => (v && v >= 1 && v <= 110 && !v.startsWith(0) && !v.includes('.')) || 'Age is not valid!' 
            ],
            heightRules: [ 
                v => !!v || 'Height is required!', 
                v => (v && v >= 60 && v <= 240 && !v.startsWith(0)) || 'Height is not valid!' 
            ],
            weightRules: [ 
                v => !!v || 'Weight is required!', 
                v => (v && v >= 20 && v <= 250 && !v.startsWith(0)) || 'Weight is not valid!' 
            ],
            bodyTypeRules: [ 
                v => !!v || 'Body Type is required!',
            ],
            activityLevelRules: [ 
                v => !!v || 'Activity Level is required!',
            ],
            dietRules: [ 
                v => !!v || 'Diet is required!',
            ],
        }),
        methods : {
            fillInfo : function () {
                console.log(this.userInfo);
            },
            bodyTypeChanged : function () {
                if (this.userInfo.bodyType === 'I am not sure') {
                    this.bodyTypeDetermined = false;
                    console.log("Determine");
                }
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