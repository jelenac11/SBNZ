<template>
    <div class="home-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
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

            <v-flex xs12 sm8 md11 v-if="fillInfoCompleted === true">
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px; margin-bottom: 20px;">
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12" sm="6" md="7">
                                    <h1 style="color: #4CAF50; margin-bottom: 40px; margin-top: 10px">Week <span style="color: #616161">{{ this.weekNumber }}</span>, day <span style="color: #616161">{{ this.dayNumber }}</span></h1>
                                </v-col>                                
                                <v-col cols="auto">
                                    <h2 style="color: #4CAF50; margin-top: 10px"><span style="color: #616161">Your category: </span>{{this.currentUser.category}}</h2>
                                    <v-row>
                                        <v-col cols="auto">
                                            <h2 style="color: #4CAF50; margin-top: 7px"><span style="color: #616161">Your score: </span>{{this.currentUser.score}}</h2>
                                        </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="auto">
                                            <h2 v-if="this.week.goal == 'LOSE_WEIGHT'" style="color: #4CAF50; margin-top: -15px"><span style="color: #616161">Your goal for this week is to </span>lose weight.</h2>
                                            <h2 v-if="this.week.goal == 'MAINTAIN_WEIGHT'" style="color: #4CAF50; margin-top: -15px"><span style="color: #616161">Your goal for this week is to </span>maintain weight.</h2>
                                            <h2 v-if="this.week.goal == 'GAIN_WEIGHT'" style="color: #4CAF50; margin-top: -15px"><span style="color: #616161">Your goal for this week is to </span>gain weight.</h2>
                                        </v-col>
                                    </v-row>
                                </v-col>
                            </v-row>

                            <v-row>
                                <v-col cols="12" sm="6" md="7">
                                    <vue-ellipse-progress v-if="this.day.calories < this.week.goalCalories" :progress="(this.day.calories/this.week.goalCalories)*100" color="#388E3C" emptyColor="#43A047" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#388E3C" fontSize="1.5rem" :legend="true" :legendValue="this.day.calories" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalCalories)}}<br>Calories</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-else :progress="100" color="#C62828" emptyColor="#C62828" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#C62828" fontSize="1.5rem" :legend="true" :legendValue="this.day.calories" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalCalories)}}<br>Calories</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-if="this.day.proteins < this.week.goalProteins" class="ml-13" :progress="(this.day.proteins/this.week.goalProteins)*100" color="#388E3C" emptyColor="#43A047" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#388E3C" fontSize="1.5rem" :legend="true" :legendValue="this.day.proteins" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalProteins)}}<br>Protein</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-else class="ml-13" :progress="100" color="#C62828" emptyColor="#C62828" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#C62828" fontSize="1.5rem" :legend="true" :legendValue="this.day.proteins" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalProteins)}}<br>Protein</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-if="this.day.carbs < this.week.goalCarbs" class="ml-13" :progress="(this.day.carbs/this.week.goalCarbs)*100" color="#388E3C" emptyColor="#43A047" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#388E3C" fontSize="1.5rem" :legend="true" :legendValue="this.day.carbs" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalCarbs)}}<br>Carbs</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-else class="ml-13" :progress="100" color="#C62828" emptyColor="#C62828" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#C62828" fontSize="1.5rem" :legend="true" :legendValue="this.day.carbs" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalCarbs)}}<br>Carbs</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-if="this.day.fats < this.week.goalFats" class="ml-13" :progress="(this.day.fats/this.week.goalFats)*100" color="#388E3C" emptyColor="#43A047" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#388E3C" fontSize="1.5rem" :legend="true" :legendValue="this.day.fats" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalFats)}}<br>Fat</span>
                                    </vue-ellipse-progress>
                                    <vue-ellipse-progress v-else class="ml-13" :progress="100" color="#C62828" emptyColor="#C62828" :size="180" :thickness="10" emptyThickness="2" lineMode="out 5" animation="rs 700 1000" fontColor="#C62828" fontSize="1.5rem" :legend="true" :legendValue="this.day.fats" :legendFormatter="({currentValue}) => new Intl.NumberFormat('de-DE').format(Math.round(currentValue))" legendClass="legend-custom-style">
                                        <span slot="legend-value">/{{Math.round(this.week.goalFats)}}<br>Fat</span>
                                    </vue-ellipse-progress>
                                </v-col>
                                <v-col cols="12" sm="6" md="5">
                                    <v-card v-if="this.alarm.message == ''" style="width: 100%; height: 70%; background-color: #388E3C; color: #FFFFFF" class="pa-4" outlined>
                                        <h2 class="mb-2">Alarms:</h2>
                                        <h4>No alarms for today.</h4>
                                    </v-card>
                                    <v-card v-else style="width: 100%; height: 70%; background-color: #C62828; color: #FFFFFF" class="pa-4" outlined>
                                        <h2 class="mb-2">Alarms:</h2>
                                        <h4>{{this.alarm.message}}</h4>
                                    </v-card>
                                    <v-row>
                                        <v-spacer></v-spacer>
                                        <v-col cols="12" sm="6" md="3" align-self="end">
                                            <v-btn class="mt-2" style="width: 100%" depressed outlined color="green" @click="showReport = true">
                                                Report
                                            </v-btn>
                                        </v-col>
                                    </v-row>
                                </v-col>
                            </v-row>
                            <v-row class="mt-4">
                                <v-radio-group v-model="customForm" row>
                                    <v-radio label="Existing meal" color="green" @change="radioChanged" value='false'></v-radio>
                                    <v-radio label="Custom meal" color="green" @change="radioChanged" value='true'></v-radio>
                                </v-radio-group>
                            </v-row>

                            <v-row class="mt-n7">
                                <v-col cols="12" sm="6" md="7">
                                    <v-form v-model="isValid1" ref="formEatNewMeal" v-if="this.customForm == 'false'">
                                        <v-row>
                                            <v-col cols="12" sm="6" md="5">
                                                <v-select
                                                    color="green"
                                                    prepend-icon="lunch_dining"
                                                    v-model="newMealName"
                                                    :items="allMealsNames"
                                                    label="Meal"
                                                    :rules="[v => !!v || 'Meal is required']"
                                                    required
                                                ></v-select>
                                            </v-col>
                                            <v-col cols="12" sm="6" md="4">
                                                <v-text-field
                                                    color="green"
                                                    prepend-icon="monitor_weight"
                                                    label="Grams"
                                                    type="number"
                                                    step=1
                                                    v-model="newMeal.grams"
                                                    :rules="gramsRules"
                                                    suffix="g"
                                                    required
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" sm="6" md="3">
                                                <v-btn 
                                                    color="green"
                                                    class="my-2"
                                                    style="width: 100%; color: white;" 
                                                    :disabled="!isValid1"
                                                    v-on:click="eatNewMeal"
                                                >Eat</v-btn>
                                            </v-col>
                                        </v-row>
                                        <h3 v-if="this.allMeals[this.allMealsNames.indexOf(this.newMealName)] != null && this.allMeals[this.allMealsNames.indexOf(this.newMealName)].allergen" class="mt-n6 orange--text">Be careful. This meal has allergen.</h3>
                                    </v-form>

                                    <v-form v-model="isValid2" ref="formAddNewCustomMeal" v-if="this.customForm == 'true'">
                                        <v-form v-model="isValidNewIngredient" ref="formAddNewIngredient">
                                            <v-row>
                                                <v-col cols="12" sm="6" md="5">
                                                    <v-select
                                                        color="green"
                                                        prepend-icon="lunch_dining"
                                                        v-model="newIngredientName"
                                                        :items="groceryNames"
                                                        label="Ingredient"
                                                        :rules="[v => !!v || 'Ingredient is required']"
                                                        required
                                                    ></v-select>
                                                </v-col>
                                                <v-col cols="12" sm="6" md="4">
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
                                                <v-col cols="12" sm="6" md="3">
                                                    <v-btn
                                                        class="my-2"
                                                        color="green"
                                                        style="width: 100%; color: white;"
                                                        :disabled="!isValidNewIngredient"
                                                        v-on:click="addNewIngredient"
                                                    >Add ingredient</v-btn>
                                                </v-col>
                                            </v-row>
                                        </v-form>
                                        <v-row v-if="this.customForm == 'true'">
                                            <v-col cols="12" sm="6" md="9" class="mt-n3">
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
                                            <v-col cols="12" sm="6" md="3" class="mt-n5">
                                                <v-btn 
                                                    color="green"
                                                    class="mt-5"
                                                    style="width: 100%; color: white;" 
                                                    :disabled="!isValid2"
                                                    v-on:click="eatCustomMeal"
                                                >Eat</v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-form>

                                    <v-row>
                                        <h1 style="margin-bottom: 20px; margin-top: 30px">Recommended for you</h1>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-card class="pb-2" outlined>
                                                <div v-if="this.recommended.length >= 1">
                                                    <v-card-title class="subheading font-weight-bold green--text">
                                                        {{this.rec1.name}}
                                                    </v-card-title>
                                                    <v-divider></v-divider>
                                                    <v-list dense>
                                                        <v-list-item-content>
                                                            <span class="ml-4">Time: <b>{{ this.recMeal1.time }}m</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Calories: <b>{{ this.recMeal1.calories }}</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Fats: <b>{{ this.recMeal1.fats }}g</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Carbs: <b>{{ this.recMeal1.carbs }}g</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Proteins: <b>{{ this.recMeal1.proteins }}g</b></span>
                                                        </v-list-item-content>
                                                        <p class="ml-4 mr-6 mt-1" style="text-justify: inter-word;">Ingredients: <b>{{ printIngredients(this.recMeal1) }}</b></p>
                                                        <p class="ml-4 mr-6 mt-1" style="text-justify: inter-word;">{{ this.recMeal1.description }}</p>
                                                        <v-form v-model="isValidRec1" ref="formRec1">
                                                            <v-list-item-content class="px-4 mt-n4">
                                                                <v-text-field
                                                                    color="green"
                                                                    label="Grams"
                                                                    type="number"
                                                                    step=1
                                                                    v-model="rec1.grams"
                                                                    :rules="gramsRules"
                                                                    suffix="g"
                                                                    required
                                                                ></v-text-field>
                                                            </v-list-item-content>
                                                            <v-list-item-content class="px-4 mt-n4">
                                                                <v-btn 
                                                                    color="green"
                                                                    style="width: 100%; color: white;" 
                                                                    :disabled="!isValidRec1"
                                                                    v-on:click="eatRec1"
                                                                >Eat</v-btn>
                                                            </v-list-item-content>
                                                        </v-form>
                                                    </v-list>
                                                </div>
                                            </v-card>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-card class="pb-2" outlined>
                                                <div v-if="this.recommended.length >= 2">
                                                    <v-card-title class="subheading font-weight-bold green--text">
                                                        {{this.rec2.name}}
                                                    </v-card-title>
                                                    <v-divider></v-divider>
                                                    <v-list dense>
                                                        <v-list-item-content>
                                                            <span class="ml-4">Time: <b>{{ this.recMeal2.time }}m</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Calories: <b>{{ this.recMeal2.calories }}</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Fats: <b>{{ this.recMeal2.fats }}g</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Carbs: <b>{{ this.recMeal2.carbs }}g</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Proteins: <b>{{ this.recMeal2.proteins }}g</b></span>
                                                        </v-list-item-content>
                                                        <p class="ml-4 mr-6 mt-1" style="text-justify: inter-word;">Ingredients: <b>{{ printIngredients(this.recMeal2) }}</b></p>
                                                        <p class="ml-4 mr-6 mt-1" style="text-justify: inter-word;">{{ this.recMeal2.description }}</p>
                                                        <v-form v-model="isValidRec2" ref="formRec2">
                                                            <v-list-item-content class="px-4 mt-n4">
                                                                <v-text-field
                                                                    color="green"
                                                                    label="Grams"
                                                                    type="number"
                                                                    step=1
                                                                    v-model="rec2.grams"
                                                                    :rules="gramsRules"
                                                                    suffix="g"
                                                                    required
                                                                ></v-text-field>
                                                            </v-list-item-content>
                                                            <v-list-item-content class="px-4 mt-n4">
                                                                <v-btn 
                                                                    color="green"
                                                                    style="width: 100%; color: white;" 
                                                                    :disabled="!isValidRec2"
                                                                    v-on:click="eatRec2"
                                                                >Eat</v-btn>
                                                            </v-list-item-content>
                                                        </v-form>
                                                    </v-list>
                                                </div>
                                            </v-card>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-card class="pb-2" outlined>
                                                <div v-if="this.recommended.length >= 3">
                                                    <v-card-title class="subheading font-weight-bold green--text">
                                                        {{this.rec3.name}}
                                                    </v-card-title>
                                                    <v-divider></v-divider>
                                                    <v-list dense>
                                                        <v-list-item-content>
                                                            <span class="ml-4">Time: <b>{{ this.recMeal3.time }}m</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Calories: <b>{{ this.recMeal3.calories }}</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Fats: <b>{{ this.recMeal3.fats }}g</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Carbs: <b>{{ this.recMeal3.carbs }}g</b></span>
                                                        </v-list-item-content>
                                                        <v-list-item-content>
                                                            <span class="ml-4 mt-n3">Proteins: <b>{{ this.recMeal3.proteins }}g</b></span>
                                                        </v-list-item-content>
                                                        <p class="ml-4 mr-6 mt-1" style="text-justify: inter-word;">Ingredients: <b>{{ printIngredients(this.recMeal3) }}</b></p>
                                                        <p class="ml-4 mr-6 mt-1" style="text-justify: inter-word;">{{ this.recMeal3.description }}</p>
                                                        <v-form v-model="isValidRec3" ref="formRec3">
                                                            <v-list-item-content class="px-4 mt-n4">
                                                                <v-text-field
                                                                    color="green"
                                                                    label="Grams"
                                                                    type="number"
                                                                    step=1
                                                                    v-model="rec3.grams"
                                                                    :rules="gramsRules"
                                                                    suffix="g"
                                                                    required
                                                                ></v-text-field>
                                                            </v-list-item-content>
                                                            <v-list-item-content class="px-4 mt-n4">
                                                                <v-btn 
                                                                    color="green"
                                                                    style="width: 100%; color: white;" 
                                                                    :disabled="!isValidRec3"
                                                                    v-on:click="eatRec3"
                                                                >Eat</v-btn>
                                                            </v-list-item-content>
                                                        </v-form>
                                                    </v-list>
                                                </div>
                                            </v-card>
                                        </v-col>
                                    </v-row>
                                </v-col>

                                <v-col cols="6" md="5">
                                    <v-card style="width: 100%; height: 100%;" class="pa-4" outlined>
                                        <h1 class="mb-7 ml-1 mt-1" style="color: #515151">You ate today:</h1>
                                        <p class="ml-1 mt-1" style="color: #515151" v-if="day.concreteMeals.length === 0">Nothing yet.</p>
                                        <v-row v-for="concMeal in this.day.concreteMeals" :key="concMeal.id">
                                            <v-card style="width: 100%;" class="pa-3 mx-2 my-1" outlined>
                                                <h2 v-if="!concMeal.customMeal" class="font-weight-bold green--text">{{concMeal.meal.name}} <span style="color: #515151">{{concMeal.grams}}g</span></h2>
                                                <h2 v-else class="font-weight-bold green--text">Custom Meal <span style="color: #515151">({{getIngredients(concMeal)}})</span></h2>
                                            </v-card>
                                        </v-row>
                                    </v-card>
                                </v-col>
                            </v-row>

                        </v-container>
                    </v-card-text>
                </v-card>
            </v-flex>

        </v-layout>
        <BodyTypeDialog v-model="showBodyTypeDialog"></BodyTypeDialog>
        <UserReport v-model="showReport"></UserReport>
        <v-snackbar v-model="notAllowedToEat" top color="red darken-3">
            You are not allowed to eat! You ate 3 meals in last 6 hours.
        </v-snackbar>
        <v-snackbar v-model="showNotification" top color="red darken-3">
            You exceeded goal calories for today!
        </v-snackbar>
        <v-snackbar v-model="wrongIngredients" top color="red darken-3">
            You must add at least one ingredient.
        </v-snackbar>
    </div>
</template>

<script>
    import Navbar from './Navbar.vue'
    import BodyTypeDialog from './BodyTypeDialog.vue'
    import UserReport from './UserReport.vue'
    import { mapState } from 'vuex'

    export default {
        name: 'Home',
        components: {
            Navbar,
            BodyTypeDialog,
            UserReport
        },
        data: () => ({
            customForm: 'false',
            newMealName: null,
            options: {
                color: "#7579ff",
                "empty-color": "#324c7e",
                size: 180,
                thickness: 5,
                "empty-thickness": 3,
                "line-mode": "out 5",
                animation: "rs 700 1000",
                "font-size": "1.5rem",
                "font-color": "white",
            },
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
            day: {
                calories: 0,
                carbs: 0,
                fats: 0,
                proteins: 0,
                concreteMeals: [
                    
                ]
            },
            week: {
                goalCalories: 0,
                goalCarbs: 0,
                goalProteins: 0,
                goalFats: 0,
            },
            newMeal: {
                grams: null,
                customMeal: false,
                meal: null,
                ingredients: []
            },
            alarm: {
              message: '',  
            },
            rec1: {
                grams: null,
                name: 'Fruit Salad',
            },
            recMeal1: {},
            recMeal2: {},
            recMeal3: {},
            rec2: {
                grams: null,
                name: 'Avocado Salad',
            },
            rec3: {
                grams: null,
                name: 'Caesar Salad',
            },
            newCustomMeal: {
                grams: null,
                customMeal: true,
                meal: null,
                ingredients: []
            },
            groceryNames: [],
            allGroceryNames: [],
            ingredientsAdded: [],
            ingredientsAvailable: [],
            newIngredientName: null,
            newIngredient: {
                grocery: {
                    name: null,
                },
                grams: null,
            },
            recommended: [],
            weekNumber: 1,
            dayNumber: 1,
            isValid1: true,
            isValid2: true,
            isValidRec1: true,
            isValidRec2: true,
            isValidRec3: true,
            wrongIngredients: false,
            showNotification: false,
            isValidNewIngredient: true,
            showBodyTypeDialog: false,
            bodyTypeDetermined: true,
            showReport: false,
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
            notAllowedToEat: false,
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
            gramsRules: [
                v => (v && v >= 1 && !v.startsWith(0) && !v.includes('.')) || 'Grams are not valid!' 
            ],
        }),
        methods : {
            fillInfo : function () {
                const userInfo = JSON.parse(JSON.stringify(this.currentUser));
                let allergs = this.userInfo.allergens.map((a) => this.allergens[this.allergenNames.indexOf(a)]);
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
                .then(() => {
                    this.fillInfoCompleted = true;
                    this.$store.dispatch('getWeekAndDayNumber').then(resp => {
                        this.weekNumber = resp.data.split(" ")[0];
                        this.dayNumber = resp.data.split(" ")[1];
                    });
                    this.$store.dispatch('getWeek').then(respon => {
                        this.week = respon.data;
                    });
                    this.$store.dispatch('getDay').then(respon => {
                        this.day = respon.data;
                    });
                    this.$store.dispatch('getAllMeals', { name: '', fromTime: 0, toTime: 0, descending: false });
                    this.$store.dispatch('getAllGroceries', { name: '', fromTime: 0, toTime: 0, descending: false }).then(resp => {
                        this.groceryNames = resp.data.sorted.map(a => a.name);
                        this.allGroceryNames = resp.data.sorted.map(a => a.name);
                    });
                    this.$store.dispatch('getAlarm').then(resp => {
                        this.alarm = resp.data;
                    });
                    this.$store.dispatch('getRecommended').then(resp => {
                        this.recommended = resp.data;
                        if (this.recommended.length >= 1) {
                            this.rec1.name = this.recommended[0].meal.name;
                            this.recMeal1 = this.recommended[0].meal;
                        }
                        if (this.recommended.length >= 2) {
                            this.rec2.name = this.recommended[1].meal.name;
                            this.recMeal2 = this.recommended[1].meal;
                        }
                        if (this.recommended.length >= 3) {
                            this.rec3.name = this.recommended[2].meal.name;
                            this.recMeal3 = this.recommended[2].meal;
                        }
                    });
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
            eatNewMeal : function () {
                if (!this.currentUser.allowedToEat) {
                    this.notAllowedToEat = true;
                    return;
                }
                const meal = JSON.parse(JSON.stringify(this.newMeal));
                meal.meal = this.allMeals[this.allMealsNames.indexOf(this.newMealName)];
                this.$store.dispatch('eatMeal', { name: meal.meal.name, grams: parseInt(meal.grams) })
                .then(res => {
                    this.day.calories = res.data.calories;
                    this.day.fats = res.data.fats;
                    this.day.proteins = res.data.proteins;
                    this.day.carbs = res.data.carbs;
                    if (res.data.notification.message == "You exceeded goal calories for today!") {
                        this.showNotification = true;
                    }
                    this.day.concreteMeals.push(meal);
                    this.$refs.formEatNewMeal.reset();
                    this.newMeal = {
                        grams: null,
                        customMeal: false,
                        meal: null,
                        ingredients: []
                    };
                    this.$store.dispatch('getCurrentUser');
                })
                .catch(err => {
                    console.log(err.response)
                });
            },
            eatCustomMeal : function () {
                if (this.newCustomMeal.ingredients.length == 0) {
                    this.wrongIngredients = true;
                } else {
                    if (!this.currentUser.allowedToEat) {
                        this.notAllowedToEat = true;
                        return;
                    }
                    const meal = JSON.parse(JSON.stringify(this.newCustomMeal));
                    this.$store.dispatch('eatCustomMeal', meal)
                    .then(res => {
                        this.day.calories = res.data.calories;
                        this.day.fats = res.data.fats;
                        this.day.proteins = res.data.proteins;
                        this.day.carbs = res.data.carbs;
                        if (res.data.notification.message == "You exceeded goal calories for today!") {
                            this.showNotification = true;
                        }
                        this.day.concreteMeals.push(meal);
                        this.$refs.formAddNewCustomMeal.reset();
                        this.newCustomMeal = {
                            grams: null,
                            customMeal: true,
                            meal: null,
                            ingredients: []
                        };
                        this.$store.dispatch('getCurrentUser');
                    })
                    .catch(err => {
                        console.log(err.response)
                    });
                }
            },
            eatRec1 : function () {
                if (!this.currentUser.allowedToEat) {
                    this.notAllowedToEat = true;
                    return;
                }
                this.$store.dispatch('eatMeal', { name: this.rec1.name, grams: parseInt(this.rec1.grams) })
                .then(res => {
                    this.day.calories = res.data.calories;
                    this.day.fats = res.data.fats;
                    this.day.proteins = res.data.proteins;
                    this.day.carbs = res.data.carbs;
                    if (res.data.notification.message == "You exceeded goal calories for today!") {
                        this.showNotification = true;
                    }
                    this.day.concreteMeals.push({grams: this.rec1.grams, customMeal: false, meal: {name: this.rec1.name}, ingredients: []});
                    this.$refs.formRec1.reset();
                    this.$store.dispatch('getCurrentUser');
                })
                .catch(err => {
                    console.log(err.response)
                });
            },
            eatRec2 : function () {
                if (!this.currentUser.allowedToEat) {
                    this.notAllowedToEat = true;
                    return;
                }
                this.$store.dispatch('eatMeal', { name: this.rec2.name, grams: parseInt(this.rec2.grams) })
                .then(res => {
                    this.day.calories = res.data.calories;
                    this.day.fats = res.data.fats;
                    this.day.proteins = res.data.proteins;
                    this.day.carbs = res.data.carbs;
                    if (res.data.notification.message == "You exceeded goal calories for today!") {
                        this.showNotification = true;
                    }
                    this.day.concreteMeals.push({grams: this.rec2.grams, customMeal: false, meal: {name: this.rec2.name}, ingredients: []});
                    this.$refs.formRec2.reset();
                    this.$store.dispatch('getCurrentUser');
                })
                .catch(err => {
                    console.log(err.response)
                });
            },
            eatRec3 : function () {
                if (!this.currentUser.allowedToEat) {
                    this.notAllowedToEat = true;
                    return;
                }
                this.$store.dispatch('eatMeal', { name: this.rec3.name, grams: parseInt(this.rec3.grams) })
                .then(res => {
                    this.day.calories = res.data.calories;
                    this.day.fats = res.data.fats;
                    this.day.proteins = res.data.proteins;
                    this.day.carbs = res.data.carbs;
                    if (res.data.notification.message == "You exceeded goal calories for today!") {
                        this.showNotification = true;
                    }
                    this.day.concreteMeals.push({grams: this.rec3.grams, customMeal: false, meal: {name: this.rec3.name}, ingredients: []});
                    this.$refs.formRec3.reset();
                    this.$store.dispatch('getCurrentUser');
                })
                .catch(err => {
                    console.log(err.response)
                });
            },
            addNewIngredient : function () {
                let grocery = this.groceries[this.allGroceryNames.indexOf(this.newIngredientName)];
                this.newIngredient.grocery = grocery;
                this.newCustomMeal.ingredients.push(this.newIngredient);
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
                        this.newCustomMeal.ingredients = this.newCustomMeal.ingredients.filter(ingr => ingr.grocery.name != elem);
                    } else {
                        return elem;
                    }
                });
            },
            radioChanged : function () {
                if (this.customForm == 'true') {
                    this.$refs.formAddNewCustomMeal.reset();     
                    this.newCustomMeal = {
                        grams: null,
                        customMeal: true,
                        meal: null,
                        ingredients: []
                    };
                    this.newIngredient = {
                        grocery: {
                            name: null,
                        },
                        grams: null,
                    };
                } else {
                    this.$refs.formEatNewMeal.reset();
                    this.newMeal = {
                        grams: null,
                        customMeal: false,
                        meal: null,
                        ingredients: []
                    };
                    this.newIngredient = {
                        grocery: {
                            name: null,
                        },
                        grams: null,
                    };
                }
            },
            getIngredients : function (meal) {
                let ings = "";
                for (let i = 0; i < meal.ingredients.length; ++i) {
                    ings += meal.ingredients[i].grocery.name + " " + meal.ingredients[i].grams + "g, ";
                }
                return ings.slice(0, ings.length - 2);
            },
            printIngredients : function (meal) {
                let ings = "";
                for (let i = 0; i < meal.ingredients.length; ++i) {
                    ings += meal.ingredients[i].grocery.name + ", ";
                }
                return ings.slice(0, ings.length - 2);
            },
        },
        computed: {
            ...mapState({
                currentUser: state => state.user,
                allergens: state => state.allergens,
                allergenNames: state => state.allergens.map(a => a.name),
                bodyType: state => state.bodyType,
                bodyTypeCompleted: state => state.bodyType !== '',
                allMeals: state => state.allMeals,
                allMealsNames: state => state.allMeals.map(a => a.name),
                groceries: state => state.allGroceries,
            }),
        },
        mounted: function () {
            this.$store.dispatch('getCurrentUser').then((resp) => {
                this.fillInfoCompleted = resp.data.age !== 0;
                if (this.fillInfoCompleted) {
                    this.$store.dispatch('getWeekAndDayNumber').then(respon => {
                        this.weekNumber = respon.data.split(" ")[0];
                        this.dayNumber = respon.data.split(" ")[1];
                    });
                    this.$store.dispatch('getWeek').then(respon => {
                        this.week = respon.data;
                    });
                    this.$store.dispatch('getDay').then(respon => {
                        this.day = respon.data;
                    });
                    this.$store.dispatch('getAllMeals', { name: '', fromTime: 0, toTime: 0, descending: false });
                    this.$store.dispatch('getAllGroceries', { name: '', fromTime: 0, toTime: 0, descending: false }).then(resp => {
                        this.groceryNames = resp.data.sorted.map(a => a.name);
                        this.allGroceryNames = resp.data.sorted.map(a => a.name);
                    });
                    this.$store.dispatch('getAlarm').then(resp => {
                        this.alarm = resp.data;
                    });
                    this.$store.dispatch('getRecommended').then(resp => {
                        this.recommended = resp.data;
                        if (this.recommended.length >= 1) {
                            this.rec1.name = this.recommended[0].meal.name;
                            this.recMeal1 = this.recommended[0].meal;
                        }
                        if (this.recommended.length >= 2) {
                            this.rec2.name = this.recommended[1].meal.name;
                            this.recMeal2 = this.recommended[1].meal;
                        }
                        if (this.recommended.length >= 3) {
                            this.rec3.name = this.recommended[2].meal.name;
                            this.recMeal3 = this.recommended[2].meal;
                        }
                    })
                    .catch(err => {
                        console.log(err.response);
                    });
                }
            });
            this.$store.dispatch('getAllergens');
        }
    }
</script>

<style scoped>
    .home-container {
        background-image: url(../assets/222.png);
        background-repeat: repeat;
        height: 103%;
    }
</style>