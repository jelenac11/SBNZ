<template>
    <div class="admin-report-container">
        <Navbar></Navbar>
        <v-layout align-center justify-center>
            <v-flex xs12 sm8 md10>
                <v-card class="elevation-12" style="padding: 20px; margin-top: 40px;">
                    <v-card-text>
                        <table style="height: 100%;">
                            <tr style="height: 100%;">
                                <td class="halfScreen">
                                    <v-chart class="chart" :option="option"/>
                                </td>
                                <td class="halfScreen">
                                    <div class="most-rated">
                                        <h1 style="color: #4CAF50; margin-bottom: 80px; margin-top: 10px">Best Rated Meals</h1>
                                        <h1 style="margin-bottom: 30px;"><span style="color: red;">{{this.rated5}}</span>: {{this.rate5}}</h1>
                                        <h1 style="margin-bottom: 30px;"><span style="color: #FFA726;">{{this.rated4}}</span>: {{this.rate4}}</h1>
                                        <h1 style="margin-bottom: 30px;"><span style="color: #880E4F;">{{this.rated3}}</span>: {{this.rate3}}</h1>
                                        <h1 style="margin-bottom: 30px;"><span style="color: #1976D2;">{{this.rated2}}</span>: {{this.rate2}}</h1>
                                        <h1 style="margin-bottom: 30px;"><span style="color: #D32F2F;">{{this.rated1}}</span>: {{this.rate1}}</h1>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import { use } from "echarts/core";
    import { CanvasRenderer } from "echarts/renderers";
    import { PieChart } from "echarts/charts";
    import { TitleComponent, TooltipComponent, LegendComponent } from "echarts/components";
    import VChart, { THEME_KEY } from "vue-echarts";
    import Navbar from './Navbar.vue'
    import { mapState } from 'vuex'

    use([
        CanvasRenderer,
        PieChart,
        TitleComponent,
        TooltipComponent,
        LegendComponent
    ]);

    export default {
        name: "HelloWorld",
        data: () => ({
            option: {
                textStyle: {
                    fontFamily: 'Inter, "Helvetica Neue", Arial, sans-serif',
                    fontSize: 15
                },
                title: {
                    text: "Most Eaten Meals",
                    left: "center",
                    textStyle: {
                        fontFamily: 'Inter, "Helvetica Neue", Arial, sans-serif',
                        fontSize: 28,
                        color: '#4CAF50'
                    },
                },
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: "vertical",
                    left: "left",
                    data: ["", "", "", "", "", ""],
                },
                series: [
                    {
                        name: "Most Eaten Meals",
                        type: "pie",
                        radius: "65%",
                        center: ["50%", "60%"],
                        data: [
                            { value: 0, name: "" },
                            { value: 0, name: "" },
                            { value: 0, name: "" },
                            { value: 0, name: "" },
                            { value: 0, name: "" },
                            { value: 0, name: "" }
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: "rgba(0, 0, 0, 0.5)"
                            }
                        },
                    }
                ]
            },
            rated1: '',
            rated2: '',
            rated3: '',
            rated4: '',
            rated5: '',
            rate1: 0,
            rate2: 0,
            rate3: 0,
            rate4: 0,
            rate5: 0,
        }),
        components: {
            VChart,
            Navbar
        },
        provide: {
            [THEME_KEY]: "light"
        },
        computed: {
            ...mapState({
                adminReport: state => state.adminReport
            }),
        },
        created () {
            this.$store.dispatch('getAdminReport').then(resp => {
                this.option.series[0].data[0].name = resp.data.mostPopularMeals[0].meal
                this.option.series[0].data[1].name = resp.data.mostPopularMeals[1].meal
                this.option.series[0].data[2].name = resp.data.mostPopularMeals[2].meal
                this.option.series[0].data[3].name = resp.data.mostPopularMeals[3].meal
                this.option.series[0].data[4].name = resp.data.mostPopularMeals[4].meal
                this.option.series[0].data[5].name = 'Other meals'

                this.option.legend.data[0] = resp.data.mostPopularMeals[0].meal
                this.option.legend.data[1] = resp.data.mostPopularMeals[1].meal
                this.option.legend.data[2] = resp.data.mostPopularMeals[2].meal
                this.option.legend.data[3] = resp.data.mostPopularMeals[3].meal
                this.option.legend.data[4] = resp.data.mostPopularMeals[4].meal
                this.option.legend.data[5] = 'Other meals'

                this.option.series[0].data[0].value = resp.data.mostPopularMeals[0].eaten
                this.option.series[0].data[1].value = resp.data.mostPopularMeals[1].eaten
                this.option.series[0].data[2].value = resp.data.mostPopularMeals[2].eaten
                this.option.series[0].data[3].value = resp.data.mostPopularMeals[3].eaten
                this.option.series[0].data[4].value = resp.data.mostPopularMeals[4].eaten
                this.option.series[0].data[5].value = resp.data.eatings - resp.data.mostPopularMeals[0].eaten - resp.data.mostPopularMeals[1].eaten - resp.data.mostPopularMeals[2].eaten - resp.data.mostPopularMeals[3].eaten - resp.data.mostPopularMeals[4].eaten

                resp.data.mostRatedMeals.sort((a, b) => (a.rate > b.rate) ? 1 : ((b.rate > a.rate) ? -1 : 0))
                this.rated1 = resp.data.mostRatedMeals[0].meal;
                this.rated2 = resp.data.mostRatedMeals[1].meal;
                this.rated3 = resp.data.mostRatedMeals[2].meal;
                this.rated4 = resp.data.mostRatedMeals[3].meal;
                this.rated5 = resp.data.mostRatedMeals[4].meal;

                this.rate1 = resp.data.mostRatedMeals[0].rate;
                this.rate2 = resp.data.mostRatedMeals[1].rate;
                this.rate3 = resp.data.mostRatedMeals[2].rate;
                this.rate4 = resp.data.mostRatedMeals[3].rate;
                this.rate5 = resp.data.mostRatedMeals[4].rate;
            });
        },
    }
</script>

<style scoped>
    .admin-report-container {
        background-image: url(../assets/222.png);
        height: 100%;
    }
    .chart {
        height: 70vh;
        margin: 0;
        margin-top: 10px;
        margin-left: 10px;
        padding: 2%;
    }
    .halfScreen {
        width: 50vw;
        height: 100%;
    }
    .most-rated {
        height: 100%;
        padding: 2%;
    }
</style>