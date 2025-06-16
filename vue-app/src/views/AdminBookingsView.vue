<template>
    <section>
        <h2>Administrar Reservas</h2>
        <form @submit.prevent="addBooking" aria-label="Agregar reserva">
            <input v-model="nuevaReserva.clienteId" placeholder="ID Cliente" required />
            <input v-model="nuevaReserva.habitacionId" placeholder="ID Habitación" required />
            <input v-model="nuevaReserva.hotelId" placeholder="ID Hotel" required />
            <input v-model="nuevaReserva.fechaInicio" type="date" placeholder="Desde" required />
            <input v-model="nuevaReserva.fechaFin" type="date" placeholder="Hasta" required />
            <button type="submit" :disabled="loading">{{ loading ? 'Agregando...' : 'Agregar Reserva' }}</button>
        </form>
        <AlertMessage :message="error" type="error" />
        <AlertMessage :message="success" type="success" />
        <Spinner v-if="loading" />
        <table class="table" aria-label="Lista de reservas">
            <thead>
                <tr>
                    <th>Cliente</th>
                    <th>Habitación</th>
                    <th>Hotel</th>
                    <th>Desde</th>
                    <th>Hasta</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="booking in bookings" :key="booking.id">
                    <td>{{ booking.cliente?.nombre }}</td>
                    <td>{{ booking.habitacion?.numero }}</td>
                    <td>{{ booking.hotel?.nombre }}</td>
                    <td>{{ booking.fechaInicio }}</td>
                    <td>{{ booking.fechaFin }}</td>
                    <td>
                        <button @click="remove(booking.id)" aria-label="Eliminar reserva">Eliminar</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBookings, createBooking, deleteBooking } from '../services/api'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const bookings = ref([])
const nuevaReserva = ref({
    clienteId: '',
    habitacionId: '',
    hotelId: '',
    fechaInicio: '',
    fechaFin: ''
})
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadBookings() {
    error.value = ''
    try {
        const res = await getBookings()
        bookings.value = res.data
    } catch (e) {
        error.value = 'Error al cargar reservas'
    }
}

async function addBooking() {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await createBooking({
            cliente: { id: Number(nuevaReserva.value.clienteId) },
            habitacion: { id: Number(nuevaReserva.value.habitacionId) },
            hotel: { id: Number(nuevaReserva.value.hotelId) },
            fechaInicio: nuevaReserva.value.fechaInicio,
            fechaFin: nuevaReserva.value.fechaFin
        })
        success.value = 'Reserva agregada con éxito'
        nuevaReserva.value = {
            clienteId: '',
            habitacionId: '',
            hotelId: '',
            fechaInicio: '',
            fechaFin: ''
        }
        await loadBookings()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al agregar reserva'
    } finally {
        loading.value = false
    }
}

async function remove(id) {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await deleteBooking(id)
        success.value = 'Reserva eliminada'
        await loadBookings()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al eliminar reserva'
    } finally {
        loading.value = false
    }
}

onMounted(loadBookings)
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