<template>
    <div v-if="message" :class="['alert', type]" role="alert" tabindex="0">
        {{ message }}
    </div>
</template>

<script setup>
defineProps({
    message: String,
    type: { type: String, default: 'info' }
})
</script>

<style scoped>
.alert {
    margin: 1rem 0;
    padding: 1rem;
    border-radius: 4px;
    font-size: 1rem;
    border: 1px solid #b3c2cc;
}

.info {
    background: #e3f2fd;
    color: #1976d2;
}

.success {
    background: #e8f5e9;
    color: #388e3c;
}

.error {
    background: #ffebee;
    color: #3a6ea5;
    border-color: #3a6ea5;
}
</style>