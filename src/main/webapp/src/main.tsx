import React from "react"
import ReactDOM from "react-dom"
import createStore from "./store/createStore"
import AppContainer from "./containers/AppContainer"
import "./theme.less"
import {PlainRoute} from "react-router"

export declare const global: any

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
  const routes: PlainRoute = global.require('./routes/index').default(store)

  ReactDOM.render(
    <AppContainer store={store} routes={routes}/>,
    MOUNT_NODE
  )
}

if (global.__DEV__) {
  global['http'] = global.require('./http').default
  global['store'] = store


  if (global.module.hot) {
    // Development render functions
    const renderApp = render
    const renderError = (error: any) => {
      const RedBox = global.require('redbox-react').default

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
    global.module.hot.accept('./routes/index', () =>
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
