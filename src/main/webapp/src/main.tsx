import * as React from 'react'
import * as ReactDOM from 'react-dom'
import createStore from './store/createStore'
import AppContainer from './containers/AppContainer'
import './theme.less'

declare const global: any
declare const module: any
declare const require: any

// ========================================================
// Store Instantiation
// ========================================================
const initialState = global.___INITIAL_STATE__
export const store = createStore(initialState)

// ========================================================
// Render Setup
// ========================================================
const MOUNT_NODE = document.getElementById('root') as HTMLElement

let render = () => {
  const routes = require('./routes/index').default(store)

  ReactDOM.render(
    <AppContainer store={store} routes={routes}/>,
    MOUNT_NODE
  )
}

if (global.__DEV__) {
  global['http'] = require('./http').default
  global['store'] = store

  if (module.hot) {
    // Development render functions
    const renderApp = render
    const renderError = (error: any) => {
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
