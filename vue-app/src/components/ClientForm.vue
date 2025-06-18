<template>
    <form @submit.prevent="onSubmit" class="form" aria-label="Formulario cliente">
        <label for="nombre">Nombre:</label>
        <input id="nombre" v-model="localClient.nombre" required />
        <label for="email">Email:</label>
        <input id="email" v-model="localClient.email" type="email" required />
        <button type="submit" :disabled="loading">{{ submitText }}</button>
        <button v-if="showCancel" type="button" @click="$emit('cancel')" :disabled="loading">Cancelar</button>
    </form>
</template>

<script setup>
import { ref, watch } from 'vue'
const props = defineProps({
    client: { type: Object, default: () => ({ nombre: '', email: '' }) },
    loading: Boolean,
    submitText: { type: String, default: 'Guardar' },
    showCancel: Boolean
})
const emit = defineEmits(['submit', 'cancel'])
const localClient = ref({ ...props.client })

watch(() => props.client, (val) => {
    localClient.value = { ...val }
})

function onSubmit() {
    emit('submit', { ...localClient.value })
}
</script>

<style scoped>
.form {
    margin-bottom: 1.5rem;
    background: #f7fbfd;
    padding: 1rem;
    border-radius: 6px;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    max-width: 400px;
}

input {
    margin-bottom: 0.5rem;
    border: 1px solid #b3c2cc;
    border-radius: 4px;
    padding: 0.5rem;
}

button {
    background: #3a6ea5;
    color: #fff;
    border: none;
    border-radius: 4px;
    padding: 0.5rem 1.2rem;
    cursor: pointer;
    transition: background 0.2s;
    margin-right: 0.3rem;
}

button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
}

button:hover:not(:disabled) {
    background: #125ea2;
}
</style>