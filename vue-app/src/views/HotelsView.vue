<template>
  <section>
    <h2>Hoteles</h2>
    <AlertMessage v-if="error" :message="error" type="error" />
    <Spinner v-if="loading" />

    <table v-if="!loading && hoteles.length" class="table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Ciudad</th>
          <th>Dirección</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="hotel in hoteles" :key="hotel.id">
          <td>{{ hotel.nombre }}</td>
          <td>{{ hotel.ciudad }}</td>
          <td>{{ hotel.direccion }}</td>
        </tr>
      </tbody>
    </table>
    <p v-if="!loading && !hoteles.length && !error" class="empty-msg">No hay hoteles disponibles.</p>
    <button v-if="!loading" @click="fetchHotels" class="reload-btn" aria-label="Recargar hoteles">
      Recargar
    </button>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHotels } from '../services/api'
import Spinner from '../components/Spinner.vue'
import AlertMessage from '../components/AlertMessage.vue'

const hoteles = ref([])
const loading = ref(false)
const error = ref('')

async function fetchHotels() {
  loading.value = true
  error.value = ''
  try {
    const res = await getHotels()
    hoteles.value = res.data
  } catch (e) {
    error.value = e?.response?.data?.message || 'No se pudieron cargar los hoteles.'
  } finally {
    loading.value = false
  }
}

onMounted(fetchHotels)
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