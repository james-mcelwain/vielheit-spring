import axios from 'axios'
import { browserHistory } from 'react-router'

const http = axios.create({
  baseURL: 'http://localhost:8080/api/',
  timeout: 10000,
})

http.interceptors.request.use((config) => {
  const token = sessionStorage.getItem("token")

  if (token) {
    config.headers = Object.assign({
      'X-Authorization' : `Bearer ${token}`,
      'Cache-Control'   : `no-cache`,
    }, config.headers)
  }

  return config
})

http.interceptors.response.use((response) => {
  return response
}, (error) => {

  if (error.response && error.response.status === 401) {
    localStorage.clear()
    sessionStorage.clear()
    browserHistory.push('/login')
  }
  return Promise.reject(error)
})

export default http
