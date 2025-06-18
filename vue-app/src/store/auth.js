import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        role: null,
        cliente_id: null
    }),
    actions: {
        async login(username, password) {
            const res = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            })
            if (!res.ok) throw new Error('Credenciales inválidas')
            const data = await res.json()
            this.user = username
            this.role = data.role || null
            this.cliente_id = data.cliente_id || null
        },
        logout() {
            this.user = null
            this.role = null
            this.cliente_id = null
        }
    }
})