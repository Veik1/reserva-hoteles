<template>
  <section>
    <h2>Administrar Habitaciones</h2>
    <RoomForm :room="editId ? editRoom : nuevaHabitacion" :loading="loading"
      :submitText="editId ? 'Actualizar' : 'Agregar Habitación'" :showCancel="!!editId"
      @submit="editId ? updateRoom(editId, $event) : addRoom($event)" @cancel="cancelEdit" />
    <AlertMessage :message="error" type="error" aria-live="polite" />
    <AlertMessage :message="success" type="success" aria-live="polite" />
    <Spinner v-if="loading" />
    <RoomTable :rooms="rooms" @edit="startEdit" @delete="remove" />
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRooms, createRoom, updateRoom as apiUpdateRoom, deleteRoom } from '../services/api'
import RoomForm from '../components/RoomForm.vue'
import RoomTable from '../components/RoomTable.vue'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const rooms = ref([])
const nuevaHabitacion = ref({ numero: '', tipo: '', hotelId: '' })
const editRoom = ref({ numero: '', tipo: '', hotelId: '' })
const editId = ref(null)
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadRooms() {
  error.value = ''
  try {
    const res = await getRooms()
    rooms.value = res.data
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al cargar habitaciones'
  }
}

async function addRoom(room) {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await createRoom({
      numero: room.numero,
      tipo: room.tipo,
      hotel: { id: Number(room.hotelId) }
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

function startEdit(room) {
  editId.value = room.id
  editRoom.value = {
    numero: room.numero,
    tipo: room.tipo,
    hotelId: room.hotel?.id || room.hotelId || ''
  }
  error.value = ''
  success.value = ''
}

function cancelEdit() {
  editId.value = null
  editRoom.value = { numero: '', tipo: '', hotelId: '' }
}

async function updateRoom(id, room) {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await apiUpdateRoom(id, {
      numero: room.numero,
      tipo: room.tipo,
      hotel: { id: Number(room.hotelId) }
    })
    success.value = 'Habitación actualizada con éxito'
    editId.value = null
    await loadRooms()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al actualizar habitación'
  } finally {
    loading.value = false
  }
}

async function remove(id) {
  if (!confirm('¿Eliminar esta habitación?')) return
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