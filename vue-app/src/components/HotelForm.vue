<template>
    <form @submit.prevent="save">
        <input v-model="hotel.nombre" placeholder="Nombre" required />
        <input v-model="hotel.ciudad" placeholder="Ciudad" required />
        <input v-model="hotel.direccion" placeholder="Dirección" required />
        <button type="submit">Guardar</button>
    </form>
</template>

<script setup>
import { ref } from 'vue'
import { createHotel } from '../services/hotelService'
const emit = defineEmits(['saved'])
const hotel = ref({ nombre: '', ciudad: '', direccion: '' })

async function save() {
    await createHotel(hotel.value)
    hotel.value = { nombre: '', ciudad: '', direccion: '' }
    emit('saved')
}
</script>