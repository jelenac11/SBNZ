<template>
    <v-app>
        <v-main>
            <router-view id="app"></router-view>
        </v-main>
    </v-app>
</template>

<script>
    export default {
        name: 'App',

        data: () => ({
        
        }),
    };
</script>

<style>
    html {
        overflow: auto;
    }

    ::-webkit-scrollbar {
        width: 0px;
        height: 7.5px;
        background: #FFFFFF;
    }

    ::-webkit-scrollbar-corner {
        background: #FFFFFF;
    }

    ::-webkit-scrollbar-thumb {
        background: #4CAF50;
        border-radius: 20px;
        box-shadow: inset 0 0 2px rgba(0,0,0,0.5);
    }

    ::-webkit-scrollbar-thumb:hover {
        background: #339036;
    }
</style>