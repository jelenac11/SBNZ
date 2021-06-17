<template>
    <div>
        <v-row justify="center">
            <v-dialog v-model="show" persistent max-width="600px">
                <v-card class="px-4 py-4">
                    <v-card-title>
                        <h2 style="color: #4CAF50; margin-bottom: 30px; margin-top: 10px">Report</h2>
                    </v-card-title>
                    <v-card-text>
                        <h2 v-if="this.report.successfulWeeks.length === 0">No report yet. This is your first week.</h2>
                        <div v-else>
                            <v-row v-for="(week, index) in this.report.successfulWeeks" :key="index">
                                <v-card style="width: 100%;" class="pa-3 mx-2 my-1" outlined>
                                    <h2 v-if="report.successfulWeeks[index]" style="color: #515151" class="font-weight-bold">Week {{index + 1}}: <span class="ml-1 green--text">Successful</span></h2>
                                    <h2 v-else style="color: #515151" class="font-weight-bold">Week {{index + 1}}: <span class="ml-1 red--text">Failed</span></h2>
                                </v-card>
                            </v-row>
                            <v-row>
                                <v-card style="width: 100%;" class="pa-3 mx-2 my-1" outlined>
                                    <h2 style="color: #515151" class="font-weight-bold">Week {{this.report.successfulWeeks.length + 1}}: ?</h2>
                                </v-card>
                            </v-row>
                        </div>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="show = false">
                            Close
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-row>
    </div>
</template>

<script>
    export default {
        name: 'UserReport',
        data: () => ({
            report: {
                successfulWeeks: []
            },
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
                
            }
        },
        mounted: function () {
            this.$store.dispatch('getReport').then((resp) => {
                this.report = resp.data;
            });
        }
    }
</script>