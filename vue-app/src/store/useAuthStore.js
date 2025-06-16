import { defineStore } from 'pinia'
import api from '../services/api'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        role: null
    }),
    actions: {
        async login(username, password) {
            const res = await api.post('/auth/login', { username, password })
            if (res.data && res.data.message === 'Login exitoso') {
                this.user = username
                // Si el backend devuelve el rol, asígnalo aquí:
                // this.role = res.data.role
            } else {
                throw new Error('Credenciales inválidas')
            }
        },
        logout() {
            this.user = null
            this.role = null
        }
    }
})