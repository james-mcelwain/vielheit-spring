import React from 'react'
import ReactDOM from 'react-dom'
import createStore from './store/createStore'
import AppContainer from './containers/AppContainer'
import './theme.less'
import User from './domain/User'

// ========================================================
// Store Instantiation
// ========================================================
const initialState = window.___INITIAL_STATE__
export const store = createStore({
  user: sessionStorage.getItem('user') ? new User(JSON.parse(sessionStorage.getItem('user'))) : {}
})

// ========================================================
// Render Setup
// ========================================================
const MOUNT_NODE = document.getElementById('root')

let render = () => {
  const routes = require('./routes/index').default(store)

  ReactDOM.render(
    <AppContainer store={store} routes={routes}/>,
    MOUNT_NODE
  )
}

if (__DEV__) {
  global['http'] = require('./http').default
  global['store'] = store

  store.subscribe((function () {
    let errorCount = 0

    return function () {
      const { application: { errors } } = store.getState()

      if (errors.length > errorCount) {
        errors.slice(errorCount, errors.length).forEach((err) => console.log('ERROR: ', err))
        errorCount++
      }
    }
  })())

  if (module.hot) {
    // Development render functions
    const renderApp = render
    const renderError = (error) => {
      const RedBox = require('redbox-react').default

      ReactDOM.render(<RedBox error={error}/>, MOUNT_NODE)
    }

    // Wrap render in try/catch
    render = () => {
      try {
        renderApp()
      } catch (error) {
        console.error(error)
        renderError(error)
      }
    }

    // Setup hot module replacement
    module.hot.accept('./routes/index', () =>
      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE)
        render()
      })
    )
  }
}

// ========================================================
// Go!
// ========================================================
render()
