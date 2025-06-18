<template>
    <main class="register-container" aria-label="Registrarse">
        <h2>Registrarse</h2>
        <form @submit.prevent="onRegister" autocomplete="on">
            <label for="username">Usuario</label>
            <input id="username" v-model="username" required autocomplete="username" :aria-invalid="!!error"
                :aria-describedby="error ? 'register-error' : null" />

            <label for="password">Contraseña</label>
            <input id="password" type="password" v-model="password" required autocomplete="new-password"
                :aria-invalid="!!error" :aria-describedby="error ? 'register-error' : null" />

            <label for="email">Email</label>
            <input id="email" type="email" v-model="email" required autocomplete="email" :aria-invalid="!!error"
                :aria-describedby="error ? 'register-error' : null" />

            <button type="submit" :disabled="loading" aria-busy="loading"
                :aria-label="loading ? 'Registrando...' : 'Registrarse'">
                <span v-if="loading" class="spinner" aria-hidden="true"></span>
                {{ loading ? 'Registrando...' : 'Registrarse' }}
            </button>
            <p v-if="error" class="error" role="alert" id="register-error" tabindex="0" aria-live="assertive">
                {{ error }}
            </p>
            <p v-if="success" class="success" role="status" tabindex="0" aria-live="polite">
                {{ success }}
            </p>
        </form>
    </main>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../services/api'

const username = ref('')
const password = ref('')
const email = ref('')
const error = ref('')
const success = ref('')
const loading = ref(false)
const router = useRouter()

async function onRegister() {
    error.value = ''
    success.value = ''
    loading.value = true
    try {
        await register({ username: username.value, password: password.value, email: email.value })
        success.value = 'Registro exitoso. Ahora puedes iniciar sesión.'
        username.value = ''
        password.value = ''
        email.value = ''
        setTimeout(() => router.push('/login'), 1500)
    } catch (e) {
        error.value =
            e?.response?.data?.error ||
            e?.response?.data?.message ||
            e.message ||
            'No se pudo registrar el usuario'
        await nextTick()
        document.getElementById('register-error')?.focus()
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
.register-container {
    max-width: 350px;
    margin: 2rem auto;
    padding: 2rem;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px #0001;
}

label {
    display: block;
    margin-top: 1rem;
}

input {
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.25rem;
    border: 1px solid #bbb;
    border-radius: 4px;
    font-size: 1rem;
}

input[aria-invalid="true"] {
    border-color: #c00;
    background: #ffebee;
}

button {
    margin-top: 1.5rem;
    width: 100%;
    padding: 0.75rem;
    background: #1976d2;
    color: #fff;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    cursor: pointer;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
}

button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
}

.spinner {
    border: 3px solid #eee;
    border-top: 3px solid #1976d2;
    border-radius: 50%;
    width: 18px;
    height: 18px;
    margin-right: 8px;
    animation: spin 1s linear infinite;
    display: inline-block;
    vertical-align: middle;
}

@keyframes spin {
    to {
        transform: rotate(360deg);
    }
}

.error {
    color: #c00;
    margin-top: 1rem;
    background: #ffebee;
    padding: 0.5rem;
    border-radius: 4px;
    font-size: 1rem;
}

.success {
    color: #1976d2;
    margin-top: 1rem;
    background: #eaf6fb;
    padding: 0.5rem;
    border-radius: 4px;
    font-size: 1rem;
}
</style>