import Axios from 'axios'
import { browserHistory } from 'react-router'
import { store } from './main'
import Application from 'core/Application'

const http = Axios.create({
  baseURL: 'http://localhost:8080/api/',
  timeout: 10000,
})

http.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('token')

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

  const applicationErr = error.response.data
  if (applicationErr) {
    store.dispatch(Application.RESPONSE_ERROR.dispatch(applicationErr))
  }

  if (error.response && error.response.status === 401) {
    localStorage.clear()
    sessionStorage.clear()
    store.dispatch(Application.LOGOUT.dispatch())
    browserHistory.push('/login')
  }

  return Promise.reject(error)
})

export default http
