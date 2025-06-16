<template>
  <section>
    <h2>Administrar Habitaciones</h2>
    <form @submit.prevent="addRoom" aria-label="Agregar habitación">
      <input v-model="nuevaHabitacion.numero" placeholder="Número" required />
      <input v-model="nuevaHabitacion.tipo" placeholder="Tipo" required />
      <input v-model="nuevaHabitacion.hotelId" placeholder="ID Hotel" required />
      <button type="submit" :disabled="loading">{{ loading ? 'Agregando...' : 'Agregar Habitación' }}</button>
    </form>
    <AlertMessage :message="error" type="error" />
    <AlertMessage :message="success" type="success" />
    <Spinner v-if="loading" />
    <table class="table" aria-label="Lista de habitaciones">
      <thead>
        <tr>
          <th>Número</th>
          <th>Tipo</th>
          <th>Hotel</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="room in rooms" :key="room.id">
          <td>{{ room.numero }}</td>
          <td>{{ room.tipo }}</td>
          <td>{{ room.hotel?.nombre }}</td>
          <td>
            <button @click="remove(room.id)" aria-label="Eliminar habitación">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRooms, createRoom, deleteRoom } from '../services/api'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const rooms = ref([])
const nuevaHabitacion = ref({ numero: '', tipo: '', hotelId: '' })
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadRooms() {
  error.value = ''
  try {
    const res = await getRooms()
    rooms.value = res.data
  } catch (e) {
    error.value = 'Error al cargar habitaciones'
  }
}

async function addRoom() {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await createRoom({
      numero: nuevaHabitacion.value.numero,
      tipo: nuevaHabitacion.value.tipo,
      hotel: { id: Number(nuevaHabitacion.value.hotelId) }
    })
    success.value = 'Habitación agregada con éxito'
    nuevaHabitacion.value = { numero: '', tipo: '', hotelId: '' }
    await loadRooms()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al agregar habitación'
  } finally {
    loading.value = false
  }
}

async function remove(id) {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await deleteRoom(id)
    success.value = 'Habitación eliminada'
    await loadRooms()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al eliminar habitación'
  } finally {
    loading.value = false
  }
}

onMounted(loadRooms)
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