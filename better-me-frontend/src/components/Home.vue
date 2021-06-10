<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md10 v-if="fillInfoCompleted === true">
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">My Nutrition Plan</h1>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        
                    </v-card-actions>
                </v-card>
            </v-flex>

            <v-flex xs12 sm8 md6 v-if="fillInfoCompleted === false">
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <h1 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Fill Information About Yourself</h1>
                        <v-form class="mt-4" v-model="isValid" ref="formFillInfo">
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="6">
                                        <v-select
                                            color="green"
                                            prepend-icon="transgender"
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
                                            prepend-icon="elderly"
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
                                            prepend-icon="height"
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
                                            prepend-icon="monitor_weight"
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
                                        <v-text-field
                                            v-if="bodyTypeCompleted"
                                            color="green"
                                            prepend-icon="accessibility"
                                            label="Body Type"
                                            type="text"
                                            v-model="bodyType"
                                            disabled
                                        ></v-text-field>
                                        <v-select
                                            v-else
                                            color="green"
                                            prepend-icon="accessibility"
                                            v-model="userInfo.bodyType"
                                            :items="bodyTypeItems"
                                            label="Body Type"
                                            :rules="bodyTypeRules"
                                            :disabled="!bodyTypeDetermined"
                                            required
                                            @input='bodyTypeChanged'
                                        ></v-select>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-select
                                            color="green"
                                            prepend-icon="directions_run"
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
                                            prepend-icon="restaurant"
                                            v-model="userInfo.diet"
                                            :items="dietItems"
                                            label="Diet"
                                            data-vv-name="diet"
                                            :rules="dietRules"
                                            required
                                        ></v-select>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="6" class="mt-n7">
                                        <v-select
                                            color="green"
                                            prepend-icon="no_food"
                                            v-model="userInfo.allergens"
                                            :items="allergenNames"
                                            label="Select Allergens"
                                            multiple
                                        >
                                            <template v-slot:selection="{ item, index }">
                                                <v-chip v-if="index === 0">
                                                    <span>{{ item }}</span>
                                                </v-chip>
                                                <span v-if="index === 1" class="grey--text text-caption">
                                                    (+{{ userInfo.allergens.length - 1 }} others)
                                                </span>
                                            </template>
                                        </v-select>
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
        <BodyTypeDialog v-model="showBodyTypeDialog"></BodyTypeDialog>
    </div>
</template>

<script>
    import Navbar from './Navbar.vue'
    import BodyTypeDialog from './BodyTypeDialog.vue'
    import { mapState } from 'vuex'

    export default {
        name: 'Home',
        components: {
            Navbar,
            BodyTypeDialog
        },
        data: () => ({
            userInfo: {
                sex: null,
                height: null,
                weight: null,
                age: null,
                bodyType: null,
                activityLevel: null,
                diet: null,
                allergens: []
            },
            showBodyTypeDialog: false,
            bodyTypeDetermined: true,
            fillInfoCompleted: null,
            genderItems: ['Male', 'Female'],
            realGenderItems: ['MALE', 'FEMALE'],
            bodyTypeItems: ['Ectomorph', 'Mesomorph', 'Endomorph', 'I am not sure'],
            realBodyTypeItems: ['ECTOMORPH', 'MESOMORPH', 'ENDOMORPH', 'I am not sure'],
            activityLevelItems: ['Inactive', 'Sedentary', 'Lightly active', 'Moderately active', 'Very active'],
            realActivityLevelItems: ['INACTIVE', 'SEDENTARY', 'LIGHTLY_ACTIVE', 'MODERATELY_ACTIVE', 'VERY_ACTIVE'],
            dietItems: ['Vegan', 'Vegetarian', 'Omnivore'],
            realDietItems: ['VEGAN', 'VEGETARIAN', 'OMNIVORE'],
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
                const userInfo = JSON.parse(JSON.stringify(this.currentUser));
                let allergs = this.userInfo.allergens.map((a) => this.allergens[this.allergenNames.indexOf(a)]);
                console.log(allergs);
                userInfo.allergens = allergs;
                userInfo.height = this.userInfo.height;
                userInfo.weight = this.userInfo.weight;
                userInfo.age = this.userInfo.age;
                userInfo.sex = this.realGenderItems[this.genderItems.indexOf(this.userInfo.sex)];
                userInfo.bodyType = this.realBodyTypeItems[this.bodyTypeItems.indexOf(this.userInfo.bodyType)];
                if (this.bodyType !== '') {
                    userInfo.bodyType = this.bodyType;
                }
                userInfo.activityLevel = this.realActivityLevelItems[this.activityLevelItems.indexOf(this.userInfo.activityLevel)];
                userInfo.diet = this.realDietItems[this.dietItems.indexOf(this.userInfo.diet)];
                this.$store.dispatch('fillInfo', userInfo)
                .then(resp => {
                    console.log(resp.data);
                    this.fillInfoCompleted = true;
                })
                .catch(err => {
                    console.log(err);
                });
            },
            bodyTypeChanged : function () {
                if (this.userInfo.bodyType === 'I am not sure') {
                    this.bodyTypeDetermined = false;
                    this.showBodyTypeDialog = true;
                }
            },
        },
        computed: {
            ...mapState({
                currentUser: state => state.user,
                allergens: state => state.allergens,
                allergenNames: state => state.allergens.map(a => a.name),
                bodyType: state => state.bodyType,
                bodyTypeCompleted: state => state.bodyType !== '',
            }),
        },
        mounted: function () {
            this.$store.dispatch('getCurrentUser').then((resp) => {
                this.fillInfoCompleted = resp.data.age !== 0;
            });
            this.$store.dispatch('getAllergens');
        }
    }
</script>

<style scoped>
    .home-container {
        background-image: url(../assets/222.png);
        height: 100%;
    }
</style>