<template>
    <section>
        <h2>Mis Reservas</h2>
        <AlertMessage v-if="error" :message="error" type="error" />
        <Spinner v-if="loading" />

        <!-- Formulario Nueva Reserva -->
        <form @submit.prevent="onSubmit" class="booking-form" v-if="!editMode && !loading">
            <h3>Nueva reserva</h3>
            <label>
                Hotel:
                <select v-model="form.hotel_id" @change="fetchRooms" required>
                    <option value="" disabled>Selecciona un hotel</option>
                    <option v-for="hotel in hoteles" :key="hotel.id" :value="hotel.id">
                        {{ hotel.nombre }}
                    </option>
                </select>
            </label>
            <label>
                Habitación:
                <select v-model="form.habitacion_id" required :disabled="!form.hotel_id">
                    <option value="" disabled>Selecciona una habitación</option>
                    <option v-for="room in habitaciones" :key="room.id" :value="room.id">
                        {{ room.numero }} - {{ room.tipo }}
                    </option>
                </select>
            </label>
            <label>
                Desde:
                <input v-model="form.fecha_inicio" type="date" required />
            </label>
            <label>
                Hasta:
                <input v-model="form.fecha_fin" type="date" required />
            </label>
            <button type="submit">Reservar</button>
        </form>

        <!-- Formulario Editar Reserva -->
        <form @submit.prevent="onSubmit" class="booking-form" v-if="editMode && !loading">
            <h3>Editar reserva</h3>
            <label>
                Hotel:
                <select v-model="form.hotel_id" @change="fetchRooms" required>
                    <option value="" disabled>Selecciona un hotel</option>
                    <option v-for="hotel in hoteles" :key="hotel.id" :value="hotel.id">
                        {{ hotel.nombre }}
                    </option>
                </select>
            </label>
            <label>
                Habitación:
                <select v-model="form.habitacion_id" required :disabled="!form.hotel_id">
                    <option value="" disabled>Selecciona una habitación</option>
                    <option v-for="room in habitaciones" :key="room.id" :value="room.id">
                        {{ room.numero }} - {{ room.tipo }}
                    </option>
                </select>
            </label>
            <label>
                Desde:
                <input v-model="form.fecha_inicio" type="date" required />
            </label>
            <label>
                Hasta:
                <input v-model="form.fecha_fin" type="date" required />
            </label>
            <button type="submit">Actualizar</button>
            <button type="button" @click="cancelEdit">Cancelar</button>
        </form>

        <button v-if="!loading" @click="fetchBookings" class="reload-btn" aria-label="Recargar reservas">
            Recargar
        </button>

        <table v-if="!loading && reservas.length" class="table">
            <thead>
                <tr>
                    <th>Hotel</th>
                    <th>Habitación</th>
                    <th>Desde</th>
                    <th>Hasta</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="reserva in reservas" :key="reserva.id">
                    <td>{{ reserva.hotel?.nombre || reserva.hotel_id }}</td>
                    <td>{{ reserva.habitacion?.numero || reserva.habitacion_id }}</td>
                    <td>{{ reserva.fechaInicio || reserva.fecha_inicio }}</td>
                    <td>{{ reserva.fechaFin || reserva.fecha_fin }}</td>
                    <td>
                        <button @click="startEdit(reserva)">Editar</button>
                        <button @click="removeBooking(reserva.id)">Eliminar</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <p v-if="!loading && !reservas.length && !error" class="empty-msg">No hay reservas registradas.</p>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBookings, createBooking, updateBooking, deleteBooking, getHotels, getRooms } from '../services/api'
import Spinner from '../components/Spinner.vue'
import AlertMessage from '../components/AlertMessage.vue'
import { useAuthStore } from '../store/auth'

const auth = useAuthStore()

const reservas = ref([])
const hoteles = ref([])
const habitaciones = ref([])
const loading = ref(false)
const error = ref('')
const editMode = ref(false)
const editId = ref(null)
const form = ref({
    cliente_id: auth.cliente_id,
    habitacion_id: '',
    hotel_id: '',
    fecha_inicio: '',
    fecha_fin: ''
})

async function fetchHotels() {
    try {
        const res = await getHotels()
        hoteles.value = res.data
    } catch (e) {
        error.value = e?.response?.data?.message || 'No se pudieron cargar los hoteles.'
    }
}

async function fetchRooms() {
    if (!form.value.hotel_id) {
        habitaciones.value = []
        form.value.habitacion_id = ''
        return
    }
    try {
        const res = await getRooms()
        habitaciones.value = res.data.filter(r => {
            return (r.hotel?.id || r.hotel_id) == form.value.hotel_id
        })
        if (!habitaciones.value.find(r => r.id == form.value.habitacion_id)) {
            form.value.habitacion_id = ''
        }
    } catch (e) {
        error.value = e?.response?.data?.message || 'No se pudieron cargar las habitaciones.'
    }
}

async function fetchBookings() {
    loading.value = true
    error.value = ''
    try {
        const res = await getBookings()
        reservas.value = res.data.filter(r => (r.cliente_id || r.cliente?.id) == auth.cliente_id)
    } catch (e) {
        error.value = e?.response?.data?.message || 'No se pudieron cargar las reservas.'
    } finally {
        loading.value = false
    }
}

async function onSubmit() {
    // Validaciones frontend
    const { fecha_inicio, fecha_fin, habitacion_id, hotel_id } = form.value
    error.value = ''

    if (!hotel_id || !habitacion_id || !fecha_inicio || !fecha_fin) {
        error.value = 'Completa todos los campos obligatorios.'
        return
    }

    const hoy = new Date()
    hoy.setHours(0, 0, 0, 0)
    const inicio = new Date(fecha_inicio)
    const fin = new Date(fecha_fin)

    if (inicio < hoy) {
        error.value = 'La fecha de inicio no puede ser anterior a hoy.'
        return
    }
    if (fin < inicio) {
        error.value = 'La fecha de fin no puede ser anterior a la de inicio.'
        return
    }

    try {
        // Armar el payload como espera el backend
        const payload = {
            fechaInicio: form.value.fecha_inicio,
            fechaFin: form.value.fecha_fin,
            cliente: { id: auth.cliente_id },
            habitacion: { id: form.value.habitacion_id },
            hotel: { id: form.value.hotel_id }
        }
        if (editMode.value) {
            await updateBooking(editId.value, payload)
            error.value = ''
        } else {
            await createBooking(payload)
            error.value = ''
        }
        await fetchBookings()
        resetForm()
    } catch (e) {
        error.value = e?.response?.data?.message || 'Error al guardar la reserva.'
    }
}

function startEdit(reserva) {
    editMode.value = true
    editId.value = reserva.id
    form.value = {
        cliente_id: reserva.cliente_id || (reserva.cliente && reserva.cliente.id) || auth.cliente_id,
        habitacion_id: reserva.habitacion_id || (reserva.habitacion && reserva.habitacion.id) || '',
        hotel_id: reserva.hotel_id || (reserva.hotel && reserva.hotel.id) || '',
        fecha_inicio: reserva.fecha_inicio || reserva.fechaInicio || '',
        fecha_fin: reserva.fecha_fin || reserva.fechaFin || ''
    }
    fetchRooms()
}

function cancelEdit() {
    resetForm()
}

function resetForm() {
    editMode.value = false
    editId.value = null
    form.value = {
        cliente_id: auth.cliente_id,
        habitacion_id: '',
        hotel_id: '',
        fecha_inicio: '',
        fecha_fin: ''
    }
    habitaciones.value = []
}

async function removeBooking(id) {
    if (confirm('¿Eliminar esta reserva?')) {
        try {
            await deleteBooking(id)
            await fetchBookings()
        } catch (e) {
            error.value = e?.response?.data?.message || 'Error al eliminar la reserva.'
        }
    }
}

onMounted(async () => {
    await fetchHotels()
    await fetchBookings()
})
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

.booking-form {
    margin: 1.5rem 0;
    padding: 1rem;
    background: #eaf6fb;
    border-radius: 8px;
    box-shadow: 0 1px 8px #1976d210;
    display: flex;
    flex-direction: column;
    gap: 0.7rem;
    max-width: 400px;
}

.booking-form label {
    display: flex;
    flex-direction: column;
    font-weight: 500;
    color: #1976d2;
}

.booking-form input,
.booking-form select {
    margin-top: 0.2rem;
    padding: 0.4rem;
    border-radius: 4px;
    border: 1px solid #b3c2cc;
}

.booking-form button {
    margin-top: 0.7rem;
    padding: 0.5rem 1.2rem;
    background: #1976d2;
    color: #fff;
    border: none;
    border-radius: 6px;
    font-size: 1rem;
    cursor: pointer;
    transition: background 0.2s;
}

.booking-form button:hover,
.booking-form button:focus {
    background: #125ea2;
}
</style>