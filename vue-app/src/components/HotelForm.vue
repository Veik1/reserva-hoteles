<template>
    <form @submit.prevent="onSubmit" class="form" aria-label="Formulario hotel">
        <label for="nombre">Nombre:</label>
        <input id="nombre" v-model="localHotel.nombre" required />
        <label for="ciudad">Ciudad:</label>
        <input id="ciudad" v-model="localHotel.ciudad" required />
        <label for="direccion">Dirección:</label>
        <input id="direccion" v-model="localHotel.direccion" required />
        <div class="form-actions">
            <button type="submit" :disabled="loading">{{ submitText }}</button>
            <button v-if="showCancel" type="button" @click="$emit('cancel')" :disabled="loading">Cancelar</button>
        </div>
    </form>
</template>

<script setup>
import { ref, watch } from 'vue'
const props = defineProps({
    hotel: { type: Object, default: () => ({ nombre: '', ciudad: '', direccion: '' }) },
    loading: Boolean,
    submitText: { type: String, default: 'Guardar' },
    showCancel: Boolean
})
const emit = defineEmits(['submit', 'cancel'])
const localHotel = ref({ ...props.hotel })

watch(() => props.hotel, (val) => {
    localHotel.value = { ...val }
})

function onSubmit() {
    emit('submit', { ...localHotel.value })
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