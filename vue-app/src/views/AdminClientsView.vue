<template>
  <section>
    <h2>Administrar Clientes</h2>

    <!-- Formulario Crear/Editar -->
    <form @submit.prevent="editId ? updateClient(editId) : addClient()" aria-label="Formulario cliente" class="form">
      <label for="nombre">Nombre:</label>
      <input id="nombre" v-model="form.nombre" placeholder="Nombre" required autocomplete="off" />
      <label for="email">Email:</label>
      <input id="email" v-model="form.email" placeholder="Email" type="email" required autocomplete="off" />
      <div class="form-actions">
        <button type="submit" :disabled="loading">
          {{ loading ? (editId ? 'Actualizando...' : 'Agregando...') : (editId ? 'Actualizar' : 'Agregar Cliente') }}
        </button>
        <button v-if="editId" type="button" @click="cancelEdit" :disabled="loading">
          Cancelar
        </button>
      </div>
    </form>

    <AlertMessage :message="error" type="error" aria-live="polite" />
    <AlertMessage :message="success" type="success" aria-live="polite" />
    <Spinner v-if="loading" />

    <table class="table" aria-label="Lista de clientes">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Email</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="client in clients" :key="client.id">
          <td>{{ client.nombre }}</td>
          <td>{{ client.email }}</td>
          <td>
            <button @click="startEdit(client)" :disabled="loading">Editar</button>
            <button @click="remove(client.id)" :disabled="loading">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getClients, createClient, updateClient as apiUpdateClient, deleteClient } from '../services/api'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const clients = ref([])
const form = ref({ nombre: '', email: '' })
const editId = ref(null)
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadClients() {
  error.value = ''
  try {
    const res = await getClients()
    clients.value = res.data
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al cargar clientes'
  }
}

async function addClient() {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await createClient(form.value)
    success.value = 'Cliente agregado con éxito'
    resetForm()
    await loadClients()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al agregar cliente'
  } finally {
    loading.value = false
  }
}

function startEdit(client) {
  editId.value = client.id
  form.value = { nombre: client.nombre, email: client.email }
  error.value = ''
  success.value = ''
}

function cancelEdit() {
  resetForm()
}

async function updateClient(id) {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await apiUpdateClient(id, form.value)
    success.value = 'Cliente actualizado con éxito'
    resetForm()
    await loadClients()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al actualizar cliente'
  } finally {
    loading.value = false
  }
}

async function remove(id) {
  if (!confirm('¿Eliminar este cliente?')) return
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await deleteClient(id)
    success.value = 'Cliente eliminado'
    await loadClients()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al eliminar cliente'
  } finally {
    loading.value = false
  }
}

function resetForm() {
  editId.value = null
  form.value = { nombre: '', email: '' }
}

onMounted(loadClients)
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