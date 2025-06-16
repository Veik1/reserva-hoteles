import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        role: null
    }),
    actions: {
        async login(username, password) {
            const res = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            })
            if (!res.ok) throw new Error('Credenciales inválidas')
            // Si el backend devuelve el rol, puedes extraerlo aquí
            this.user = username
            // this.role = (await res.json()).role
        },
        logout() {
            this.user = null
            this.role = null
        }
    }
})