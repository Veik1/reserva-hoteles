import axios from 'axios'
import { useAuthStore } from '../store/auth'

const api = axios.create({ baseURL: '/api' })

api.interceptors.request.use(config => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

api.interceptors.response.use(
  res => res,
  async err => {
    const auth = useAuthStore()
    if (
      err.response &&
      err.response.status === 401 &&
      auth.refreshToken &&
      !err.config._retry
    ) {
      err.config._retry = true
      try {
        const resp = await axios.post('/api/auth/refresh', {
          refreshToken: auth.refreshToken
        })
        auth.setToken(resp.data.token)
        err.config.headers.Authorization = `Bearer ${resp.data.token}`
        return api(err.config)
      } catch (refreshErr) {
        auth.logout()
        window.location = '/login'
      }
    }
    return Promise.reject(err)
  }
)

export default api