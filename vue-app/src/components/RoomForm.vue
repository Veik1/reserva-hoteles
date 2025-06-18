<template>
    <form @submit.prevent="onSubmit" class="form" aria-label="Formulario habitación">
        <label for="numero">Número:</label>
        <input id="numero" v-model="localRoom.numero" type="number" required />
        <label for="tipo">Tipo:</label>
        <input id="tipo" v-model="localRoom.tipo" required />
        <label for="hotelId">ID Hotel:</label>
        <input id="hotelId" v-model="localRoom.hotelId" type="number" required />
        <div class="form-actions">
            <button type="submit" :disabled="loading">{{ submitText }}</button>
            <button v-if="showCancel" type="button" @click="$emit('cancel')" :disabled="loading">Cancelar</button>
        </div>
    </form>
</template>

<script setup>
import { ref, watch } from 'vue'
const props = defineProps({
    room: { type: Object, default: () => ({ numero: '', tipo: '', hotelId: '' }) },
    loading: Boolean,
    submitText: { type: String, default: 'Guardar' },
    showCancel: Boolean
})
const emit = defineEmits(['submit', 'cancel'])
const localRoom = ref({ ...props.room })

watch(() => props.room, (val) => {
    localRoom.value = { ...val }
})

function onSubmit() {
    emit('submit', { ...localRoom.value })
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

.form-actions {
    display: flex;
    gap: 0.5rem;
    margin-top: 0.5rem;
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
}

button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
}

button:hover:not(:disabled) {
    background: #125ea2;
}
</style>