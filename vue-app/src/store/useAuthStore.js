import { defineStore } from 'pinia'
import api from '../services/api'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        user: null,
        role: null
    }),
    actions: {
        setToken(token) {
            this.token = token
            localStorage.setItem('token', token)
        },
        setRefreshToken(refreshToken) {
            this.refreshToken = refreshToken
            localStorage.setItem('refreshToken', refreshToken)
        },
        async logout() {
            if (this.refreshToken) {
                try {
                    await api.post('/auth/logout', { refreshToken: this.refreshToken })
                } catch (e) { }
            }
            this.token = null
            this.refreshToken = null
            this.user = null
            this.role = null
            localStorage.clear()
        },
        async revokeAll() {
            if (this.user) {
                try {
                    await api.post('/auth/revoke-all', { username: this.user })
                } catch (e) { }
            }
            this.token = null
            this.refreshToken = null
            this.user = null
            this.role = null
            localStorage.clear()
        }
    }
})