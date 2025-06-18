<template>
    <section>
        <h2 tabindex="0">Habitaciones</h2>
        <AlertMessage v-if="error" :message="error" type="error" />
        <Spinner v-if="loading" />

        <table v-if="!loading && habitaciones.length" class="table" aria-label="Lista de habitaciones">
            <thead>
                <tr>
                    <th>Número</th>
                    <th>Tipo</th>
                    <th>Hotel</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="habitacion in habitaciones" :key="habitacion.id">
                    <td>{{ habitacion.numero }}</td>
                    <td>{{ habitacion.tipo }}</td>
                    <td>{{ habitacion.hotel?.nombre || habitacion.hotel_id || 'Sin hotel' }}</td>
                </tr>
            </tbody>
        </table>
        <p v-if="!loading && !habitaciones.length && !error" class="empty-msg">
            No hay habitaciones disponibles.
        </p>
        <button v-if="!loading" @click="fetchRooms" class="reload-btn" aria-label="Recargar habitaciones">
            Recargar
        </button>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRooms } from '../services/api'
import Spinner from '../components/Spinner.vue'
import AlertMessage from '../components/AlertMessage.vue'

const habitaciones = ref([])
const loading = ref(false)
const error = ref('')

async function fetchRooms() {
    loading.value = true
    error.value = ''
    try {
        const res = await getRooms()
        habitaciones.value = res.data
    } catch (e) {
        error.value = e?.response?.data?.message || 'No se pudieron cargar las habitaciones.'
    } finally {
        loading.value = false
    }
}

onMounted(fetchRooms)
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

.empty-msg {
    color: #3a6ea5;
    margin-top: 2rem;
    text-align: center;
    font-size: 1.1rem;
}

.reload-btn {
    margin: 1rem 0 0.5rem 0;
    padding: 0.5rem 1.2rem;
    background: #1976d2;
    color: #fff;
    border: none;
    border-radius: 6px;
    font-size: 1rem;
    cursor: pointer;
    transition: background 0.2s;
}

.reload-btn:hover,
.reload-btn:focus {
    background: #125ea2;
}
</style>