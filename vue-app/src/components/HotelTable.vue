<template>
    <table class="table" aria-label="Lista de hoteles">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Ciudad</th>
                <th>Dirección</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="hotel in hotels" :key="hotel.id">
                <td>{{ hotel.nombre }}</td>
                <td>{{ hotel.ciudad }}</td>
                <td>{{ hotel.direccion }}</td>
                <td>
                    <button @click="$emit('edit', hotel)">Editar</button>
                    <button @click="$emit('delete', hotel.id)">Eliminar</button>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script setup>
defineProps({ hotels: Array })
defineEmits(['edit', 'delete'])
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