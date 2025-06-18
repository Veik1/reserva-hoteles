<template>
    <form @submit.prevent="onSubmit" class="form" aria-label="Formulario reserva">
        <label for="clienteId">ID Cliente:</label>
        <input id="clienteId" v-model="localBooking.clienteId" type="number" required />
        <label for="habitacionId">ID Habitación:</label>
        <input id="habitacionId" v-model="localBooking.habitacionId" type="number" required />
        <label for="hotelId">ID Hotel:</label>
        <input id="hotelId" v-model="localBooking.hotelId" type="number" required />
        <label for="fechaInicio">Desde:</label>
        <input id="fechaInicio" v-model="localBooking.fechaInicio" type="date" required />
        <label for="fechaFin">Hasta:</label>
        <input id="fechaFin" v-model="localBooking.fechaFin" type="date" required />
        <div class="form-actions">
            <button type="submit" :disabled="loading">{{ submitText }}</button>
            <button v-if="showCancel" type="button" @click="$emit('cancel')" :disabled="loading">Cancelar</button>
        </div>
    </form>
</template>

<script setup>
import { ref, watch } from 'vue'
const props = defineProps({
    booking: {
        type: Object,
        default: () => ({
            clienteId: '',
            habitacionId: '',
            hotelId: '',
            fechaInicio: '',
            fechaFin: ''
        })
    },
    loading: Boolean,
    submitText: { type: String, default: 'Guardar' },
    showCancel: Boolean
})
const emit = defineEmits(['submit', 'cancel'])
const localBooking = ref({ ...props.booking })

watch(() => props.booking, (val) => {
    localBooking.value = { ...val }
})

function onSubmit() {
    emit('submit', { ...localBooking.value })
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