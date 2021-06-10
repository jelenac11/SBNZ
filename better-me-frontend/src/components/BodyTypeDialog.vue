<template>
    <div>
        <v-row justify="center">
            <v-dialog v-model="show" persistent max-width="600px">
                <v-card class="px-4 py-4">
                    <v-card-title>
                        <h3 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Determine Body Type</h3>
                    </v-card-title>
                    <v-card-text>
                        <v-form v-model="isValid" ref="formBodyType">
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.shoulders"
                                :items="shouldersItems"
                                label="Your shoulders are:"
                                :rules="[v => !!v || 'Description of shoulders must not be empty!']"
                                required
                            ></v-select>
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.jeans"
                                :items="jeansItems"
                                label="Your jeans fit:"
                                :rules="[v => !!v || 'Description of fitting jeans must not be empty!']"
                                required
                            ></v-select>
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.forearms"
                                :items="forearmsItems"
                                label="Your forearms are:"
                                :rules="[v => !!v || 'Description of forearms must not be empty!']"
                                required
                            ></v-select>
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.bodyTendation"
                                :items="bodyTendationItems"
                                label="Your body tendates to:"
                                :rules="[v => !!v || 'Body tendation must not be empty!']"
                                required
                            ></v-select>
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.bodyLook"
                                :items="bodyLookItems"
                                label="Your body looks like:"
                                :rules="[v => !!v || 'Body look must not be empty!']"
                                required
                            ></v-select>
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.weightTendation"
                                :items="weightTendationItems"
                                label="Your weight tednate to:"
                                :rules="[v => !!v || 'Weight tendation must not be empty!']"
                                required
                            ></v-select>
                            <v-select
                                color="green"
                                prepend-icon="accessibility"
                                v-model="bodyType.encircleHandWrist"
                                :items="encircleHandWristItems"
                                label="When you encircle your handwrist, your fingers:"
                                :rules="[v => !!v || 'Description of encircle hand wrist must not be empty!']"
                                required
                            ></v-select>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="determineBodyType()">
                            Determine
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-row>
        <v-snackbar v-model="wrong" top color="red darken-4">
            Answers you entered don't belong to any body type!
        </v-snackbar>
    </div>
</template>

<script>
    export default {
        name: 'BodyTypeDialog',
        data: () => ({
            bodyType: {
                shoulders: null,
                jeans: null,
                forearms: null,
                bodyTendation: null,
                bodyLook: null,
                weightTendation: null,
                encircleHandWrist: null
            },
            isValid: true,
            shouldersItems: ['Narrower than hips', 'Wider than hips', 'Same as hips'],
            jeansItems: ['Tight around your glutes', 'Loose around your glutes', 'Perfect'],
            forearmsItems: ['Small', 'Big', 'Average'],
            bodyTendationItems: ['Stay skinny', 'Carry some extra fat', 'Stay fit and muscular'],
            bodyLookItems: ['Mostly rectangle', 'Pear', 'Hourglass'],
            weightTendationItems: ['Difficult to gain and maintain', 'Gain easily but have a hard time losing it', 'Easy time losing or gaining'],
            encircleHandWristItems: ['Overlap', 'Don\'t touch', 'Touch'],
            
            realShouldersItems: ['NARROWER THAN YOUR HIPS', 'WIDER THAN YOUR HIPS', 'SAME AS YOUR HIPS'],
            realJeansItems: ['TIGHT AROUND YOUR GLUTES', 'LOOSE AROUND YOUR GLUTES', 'PERFECT AROUND YOUR GLUTES'],
            realForearmsItems: ['SMALL', 'BIG', 'AVERAGE'],
            realBodyTendationItems: ['STAY SKINNY', 'CARRY SOME EXTRA FAT', 'STAY FIT AND MUSCULAR'],
            realBodyLookItems: ['MOSTLY RECTANGLE', 'PEAR', 'HOURGLASS'],
            realWeightTendationItems: ['FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT', 'GAIN WEIGHT EASILY BUT HAVE A HARD TIME LOSING IT', 'HAVE AN EASY TIME LOSING OR GAINING WEIGHT'],
            realEncircleHandWristItems: ['THE FINGERS OVERLAP', 'THE FINGERS DON\'T TOUCH', 'THE FINGERS JUST TOUCH'],
            wrong: false,
        }),
        props: {
            value: Boolean
        },
        computed: {
            show: {
                get () {
                    return this.value
                },
                set (value) {
                    this.$emit('input', value)
                }
            },
        },
        methods : {
            determineBodyType : function () {
                const bodyType = JSON.parse(JSON.stringify(this.bodyType));
                bodyType.shoulders = this.realShouldersItems[this.shouldersItems.indexOf(this.bodyType.shoulders)];
                bodyType.jeans = this.realJeansItems[this.jeansItems.indexOf(this.bodyType.jeans)];
                bodyType.forearms = this.realForearmsItems[this.forearmsItems.indexOf(this.bodyType.forearms)];
                bodyType.bodyTendation = this.realBodyTendationItems[this.bodyTendationItems.indexOf(this.bodyType.bodyTendation)];
                bodyType.bodyLook = this.realBodyLookItems[this.bodyLookItems.indexOf(this.bodyType.bodyLook)];
                bodyType.weightTendation = this.realWeightTendationItems[this.weightTendationItems.indexOf(this.bodyType.weightTendation)];
                bodyType.encircleHandWrist = this.realEncircleHandWristItems[this.encircleHandWristItems.indexOf(this.bodyType.encircleHandWrist)];
                this.$store.dispatch('determineBodyType', bodyType)
                .then(() => {
                    this.show = false;
                })
                .catch(err => {
                    console.log(err);
                    this.wrong = true;
                });
            }
        }
    }
</script>