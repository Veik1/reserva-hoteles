<template>
    <section>
        <h2>Administrar Hoteles</h2>
        <HotelForm :hotel="editId ? editHotel : nuevoHotel" :loading="loading"
            :submitText="editId ? 'Actualizar' : 'Agregar Hotel'" :showCancel="!!editId"
            @submit="editId ? updateHotel(editId, $event) : addHotel($event)" @cancel="cancelEdit" />
        <AlertMessage :message="error" type="error" aria-live="polite" />
        <AlertMessage :message="success" type="success" aria-live="polite" />
        <Spinner v-if="loading" />
        <HotelTable :hotels="hotels" @edit="startEdit" @delete="remove" />
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHotels, createHotel, updateHotel as apiUpdateHotel, deleteHotel } from '../services/api'
import HotelForm from '../components/HotelForm.vue'
import HotelTable from '../components/HotelTable.vue'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const hotels = ref([])
const nuevoHotel = ref({ nombre: '', ciudad: '', direccion: '' })
const editHotel = ref({ nombre: '', ciudad: '', direccion: '' })
const editId = ref(null)
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadHotels() {
    error.value = ''
    try {
        const res = await getHotels()
        hotels.value = res.data
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al cargar hoteles'
    }
}

async function addHotel(hotel) {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await createHotel(hotel)
        success.value = 'Hotel agregado con éxito'
        nuevoHotel.value = { nombre: '', ciudad: '', direccion: '' }
        await loadHotels()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al agregar hotel'
    } finally {
        loading.value = false
    }
}

function startEdit(hotel) {
    editId.value = hotel.id
    editHotel.value = { ...hotel }
    error.value = ''
    success.value = ''
}

function cancelEdit() {
    editId.value = null
    editHotel.value = { nombre: '', ciudad: '', direccion: '' }
}

async function updateHotel(id, hotel) {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await apiUpdateHotel(id, hotel)
        success.value = 'Hotel actualizado con éxito'
        editId.value = null
        await loadHotels()
    } catch (e) {
        error.value = e.response?.data?.message || 'Error al actualizar hotel'
    } finally {
        loading.value = false
    }
}

async function remove(id) {
    if (!confirm('¿Eliminar este hotel?')) return
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