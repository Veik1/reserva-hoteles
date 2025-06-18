<template>
    <section>
        <h2>Administrar Reservas</h2>
        <BookingForm :booking="editId ? editBooking : nuevaReserva" :loading="loading"
            :submitText="editId ? 'Actualizar' : 'Agregar Reserva'" :showCancel="!!editId"
            @submit="editId ? updateBooking(editId, $event) : addBooking($event)" @cancel="cancelEdit" />
        <AlertMessage :message="error" type="error" aria-live="polite" />
        <AlertMessage :message="success" type="success" aria-live="polite" />
        <Spinner v-if="loading" />
        <BookingTable :bookings="bookings" @edit="startEdit" @delete="remove" />
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBookings, createBooking, updateBooking as apiUpdateBooking, deleteBooking } from '../services/api'
import BookingForm from '../components/BookingForm.vue'
import BookingTable from '../components/BookingTable.vue'
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
const editBooking = ref({
    clienteId: '',
    habitacionId: '',
    hotelId: '',
    fechaInicio: '',
    fechaFin: ''
})
const editId = ref(null)
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadBookings() {
    error.value = ''
    try {
        const res = await getBookings()
        bookings.value = res.data
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al cargar reservas'
    }
}

async function addBooking(booking) {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await createBooking({
            cliente: { id: Number(booking.clienteId) },
            habitacion: { id: Number(booking.habitacionId) },
            hotel: { id: Number(booking.hotelId) },
            fechaInicio: booking.fechaInicio,
            fechaFin: booking.fechaFin
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

function startEdit(booking) {
    editId.value = booking.id
    editBooking.value = {
        clienteId: booking.cliente?.id || booking.clienteId || '',
        habitacionId: booking.habitacion?.id || booking.habitacionId || '',
        hotelId: booking.hotel?.id || booking.hotelId || '',
        fechaInicio: booking.fechaInicio,
        fechaFin: booking.fechaFin
    }
    error.value = ''
    success.value = ''
}

function cancelEdit() {
    editId.value = null
    editBooking.value = {
        clienteId: '',
        habitacionId: '',
        hotelId: '',
        fechaInicio: '',
        fechaFin: ''
    }
}

async function updateBooking(id, booking) {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await apiUpdateBooking(id, {
            cliente: { id: Number(booking.clienteId) },
            habitacion: { id: Number(booking.habitacionId) },
            hotel: { id: Number(booking.hotelId) },
            fechaInicio: booking.fechaInicio,
            fechaFin: booking.fechaFin
        })
        success.value = 'Reserva actualizada con éxito'
        editId.value = null
        await loadBookings()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al actualizar reserva'
    } finally {
        loading.value = false
    }
}

async function remove(id) {
    if (!confirm('¿Eliminar esta reserva?')) return
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