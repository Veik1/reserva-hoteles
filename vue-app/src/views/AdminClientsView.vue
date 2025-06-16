<template>
  <section>
    <h2>Administrar Clientes</h2>
    <form @submit.prevent="addClient" aria-label="Agregar cliente">
      <input v-model="nuevoCliente.nombre" placeholder="Nombre" required />
      <input v-model="nuevoCliente.email" placeholder="Email" required />
      <button type="submit" :disabled="loading">{{ loading ? 'Agregando...' : 'Agregar Cliente' }}</button>
    </form>
    <AlertMessage :message="error" type="error" />
    <AlertMessage :message="success" type="success" />
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
            <button @click="remove(client.id)" aria-label="Eliminar cliente">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getClients, createClient, deleteClient } from '../services/api'
import AlertMessage from '../components/AlertMessage.vue'
import Spinner from '../components/Spinner.vue'

const clients = ref([])
const nuevoCliente = ref({ nombre: '', email: '' })
const error = ref('')
const success = ref('')
const loading = ref(false)

async function loadClients() {
  error.value = ''
  try {
    const res = await getClients()
    clients.value = res.data
  } catch (e) {
    error.value = 'Error al cargar clientes'
  }
}

async function addClient() {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await createClient(nuevoCliente.value)
    success.value = 'Cliente agregado con éxito'
    nuevoCliente.value = { nombre: '', email: '' }
    await loadClients()
  } catch (e) {
    error.value = e.response?.data?.message || 'Error al agregar cliente'
  } finally {
    loading.value = false
  }
}

async function remove(id) {
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