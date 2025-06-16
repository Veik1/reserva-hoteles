<template>
    <section>
        <h2>Clientes</h2>
        <AlertMessage :message="error" type="error" />
        <Spinner v-if="loading" />
        <table v-if="!loading && clientes.length" class="table">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="cliente in clientes" :key="cliente.id">
                    <td>{{ cliente.nombre }}</td>
                    <td>{{ cliente.email }}</td>
                </tr>
            </tbody>
        </table>
        <p v-if="!loading && !clientes.length && !error" class="empty-msg">No hay clientes registrados.</p>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getClientes } from '../services/api'
import Spinner from '../components/Spinner.vue'
import AlertMessage from '../components/AlertMessage.vue'

const clientes = ref([])
const loading = ref(false)
const error = ref('')

onMounted(async () => {
    loading.value = true
    error.value = ''
    try {
        const res = await getClientes()
        clientes.value = res.data
    } catch (e) {
        error.value = 'No se pudieron cargar los clientes.'
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