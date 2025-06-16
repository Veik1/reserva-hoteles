<template>
    <main class="login-container" aria-label="Iniciar sesión">
        <h2>Iniciar sesión</h2>
        <form @submit.prevent="login" autocomplete="on">
            <label for="username">Usuario</label>
            <input id="username" v-model="username" required autocomplete="username" :aria-invalid="!!error"
                :aria-describedby="error ? 'login-error' : null" />
            <label for="password">Contraseña</label>
            <input id="password" type="password" v-model="password" required autocomplete="current-password"
                :aria-invalid="!!error" :aria-describedby="error ? 'login-error' : null" />
            <button type="submit" :disabled="loading" aria-busy="loading"
                :aria-label="loading ? 'Entrando...' : 'Entrar'">
                <span v-if="loading" class="spinner" aria-hidden="true"></span>
                {{ loading ? 'Entrando...' : 'Entrar' }}
            </button>
            <p v-if="error" class="error" role="alert" id="login-error" tabindex="0" aria-live="assertive">
                {{ error }}
            </p>
        </form>
    </main>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const username = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)
const router = useRouter()
const auth = useAuthStore()

async function login() {
    error.value = ''
    loading.value = true
    try {
        await auth.login(username.value, password.value)
        router.push('/')
    } catch (e) {
        error.value = e.message || 'Usuario o contraseña incorrectos'
        await nextTick()
        document.getElementById('login-error')?.focus()
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
.login-container {
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
</style>