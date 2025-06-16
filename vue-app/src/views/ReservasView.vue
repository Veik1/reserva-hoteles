<template>
    <section>
        <h2>Reservas</h2>
        <AlertMessage :message="error" type="error" />
        <Spinner v-if="loading" />
        <table v-if="!loading && reservas.length" class="table">
            <thead>
                <tr>
                    <th>Cliente</th>
                    <th>Habitación</th>
                    <th>Hotel</th>
                    <th>Desde</th>
                    <th>Hasta</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="reserva in reservas" :key="reserva.id">
                    <td>{{ reserva.cliente?.nombre }}</td>
                    <td>{{ reserva.habitacion?.numero }}</td>
                    <td>{{ reserva.hotel?.nombre }}</td>
                    <td>{{ reserva.fechaInicio }}</td>
                    <td>{{ reserva.fechaFin }}</td>
                </tr>
            </tbody>
        </table>
        <p v-if="!loading && !reservas.length && !error" class="empty-msg">No hay reservas registradas.</p>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getReservas } from '../services/api'
import Spinner from '../components/Spinner.vue'
import AlertMessage from '../components/AlertMessage.vue'

const reservas = ref([])
const loading = ref(false)
const error = ref('')

onMounted(async () => {
    loading.value = true
    error.value = ''
    try {
        const res = await getReservas()
        reservas.value = res.data
    } catch (e) {
        error.value = 'No se pudieron cargar las reservas.'
    } finally {
        loading.value = false
    }
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
</style>