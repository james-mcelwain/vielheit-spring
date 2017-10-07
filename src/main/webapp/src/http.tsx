import Axios from 'axios'
import { browserHistory } from 'react-router'
import {store} from './main'
import Application from 'core/Application'
import { ErrorCode } from './domain/ErrorCode'

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

const logout = () => {
    localStorage.clear()
    sessionStorage.clear()
    store.dispatch(Application.LOGOUT.dispatch())
    browserHistory.push('/login')
}

http.interceptors.response.use((response) => {
  return response
}, (error) => {
  Reflect.set(window, 'lastError', error)

  const applicationErr = error.response && error.response.data
  if (applicationErr && applicationErr.errorCode === ErrorCode.JWT_EXPIRED) {
    const refreshToken = sessionStorage.getItem('refreshToken')
    if (refreshToken) {
      http.get('auth/token', {
        headers: {
          'X-Authorization': `Bearer ${refreshToken}`,
        },
      }).then((response) => {
        console.log(response)
      }).catch(logout)
    }
  }

  if (applicationErr && error.status !== 404) {
    store.dispatch(Application.RESPONSE_ERROR.dispatch(applicationErr))
  } else {
    console.log(error)
  }

  if (error.response && error.response.status === 401) {
    return logout()
  }

  return Promise.reject(error)
})

export default http
