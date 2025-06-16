<template>
    <section>
        <h2>Administrar Hoteles</h2>
        <form @submit.prevent="addHotel" aria-label="Agregar hotel">
            <input v-model="nuevoHotel.nombre" placeholder="Nombre" required />
            <input v-model="nuevoHotel.ciudad" placeholder="Ciudad" required />
            <input v-model="nuevoHotel.direccion" placeholder="Dirección" required />
            <button type="submit" :disabled="loading">{{ loading ? 'Agregando...' : 'Agregar Hotel' }}</button>
        </form>
        <AlertMessage :message="error" type="error" />
        <AlertMessage :message="success" type="success" />
        <Spinner v-if="loading" />
        <table class="table" aria-label="Lista de hoteles">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Ciudad</th>
                    <th>Dirección</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="hotel in hotels" :key="hotel.id">
                    <td>{{ hotel.nombre }}</td>
                    <td>{{ hotel.ciudad }}</td>
                    <td>{{ hotel.direccion }}</td>
                    <td>
                        <button @click="remove(hotel.id)" aria-label="Eliminar hotel">Eliminar</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHotels, createHotel, deleteHotel } from '../services/api'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const hotels = ref([])
const nuevoHotel = ref({ nombre: '', ciudad: '', direccion: '' })
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadHotels() {
    error.value = ''
    try {
        const res = await getHotels()
        hotels.value = res.data
    } catch (e) {
        error.value = 'Error al cargar hoteles'
    }
}

async function addHotel() {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await createHotel(nuevoHotel.value)
        success.value = 'Hotel agregado con éxito'
        nuevoHotel.value = { nombre: '', ciudad: '', direccion: '' }
        await loadHotels()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al agregar hotel'
    } finally {
        loading.value = false
    }
}

async function remove(id) {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await deleteHotel(id)
        success.value = 'Hotel eliminado'
        await loadHotels()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al eliminar hotel'
    } finally {
        loading.value = false
    }
}

onMounted(loadHotels)
</script>

<style scoped>
.table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 1rem;
    background: #eaf6fb;
}

.table th,
.table td {
    border: 1px solid #b3c2cc;
    padding: 0.75rem;
    text-align: left;
}

.table th {
    background: #3a6ea5;
    color: #fff;
}

.table tr:nth-child(even) {
    background: #dbeaf7;
}

form {
    margin-bottom: 1.5rem;
    background: #f7fbfd;
    padding: 1rem;
    border-radius: 6px;
}

input {
    margin-right: 0.5rem;
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