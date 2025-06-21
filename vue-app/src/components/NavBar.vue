<template>
    <nav class="navbar" aria-label="Navegación principal">
        <div class="logo">Reserva Hoteles</div>
        <ul class="nav-links">
            <li><router-link to="/">Inicio</router-link></li>
            <li><router-link to="/hoteles">Hoteles</router-link></li>
            <li><router-link to="/habitaciones">Habitaciones</router-link></li>

            <!-- Vista cliente: solo si está logueado y no es admin -->
            <li v-if="auth.user && auth.role !== 'admin'">
                <router-link to="/reservas">Mis Reservas</router-link>
            </li>
            <li v-if="auth.user && auth.role !== 'admin'">
                <router-link to="/clientes">Mi Perfil</router-link>
            </li>

            <!-- Vista admin -->
            <li v-if="auth.role === 'admin'">
                <router-link to="/admin/hoteles">Admin Hoteles</router-link>
            </li>
            <li v-if="auth.role === 'admin'">
                <router-link to="/admin/habitaciones">Admin Habitaciones</router-link>
            </li>
            <li v-if="auth.role === 'admin'">
                <router-link to="/admin/clientes">Admin Clientes</router-link>
            </li>
            <li v-if="auth.role === 'admin'">
                <router-link to="/admin/reservas">Admin Reservas</router-link>
            </li>

            <!-- Login/Logout -->
            <li v-if="auth.user">
                <a href="#" @click.prevent="logout">Salir ({{ auth.user }})</a>
            </li>
            <li v-else>
                <router-link to="/login">Login</router-link>
            </li>
        </ul>
    </nav>
</template>

<script setup>
import { useAuthStore } from '../store/auth'
const auth = useAuthStore()
function logout() {
    auth.logout()
}
</script>

<style scoped>
.navbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #1976d2;
    padding: 1rem 2rem;
}

.logo {
    color: #fff;
    font-size: 1.5rem;
    font-weight: bold;
}

.nav-links {
    list-style: none;
    display: flex;
    gap: 1.5rem;
    margin: 0;
    padding: 0;
}

.nav-links a {
    color: #fff;
    text-decoration: none;
    font-weight: 500;
}

.nav-links a.router-link-active {
    border-bottom: 2px solid #fff;
}
</style>