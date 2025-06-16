import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        role: null,
        token: localStorage.getItem('token') || null
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
            this.token = data.token
            this.user = data.username
            this.role = data.role
            localStorage.setItem('token', data.token)
        },
        logout() {
            this.user = null
            this.role = null
            this.token = null
            localStorage.removeItem('token')
        }
    }
})