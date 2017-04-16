import * as React from 'react'
import * as ReactDOM from 'react-dom'
import createStore from './store/createStore'
import createRoutes from './routes/baseRoute'
import AppContainer from './containers/AppContainer'
import './theme.less'
import http from './http'

declare const global: any
declare const module: any
declare const require: any
declare const __DEV__: boolean

const initialState = global.___INITIAL_STATE__
export const store = createStore(initialState)

const MOUNT_NODE = document.getElementById('root') as HTMLElement

let render = () => {
  const routes = createRoutes(store)

  ReactDOM.render(
    <AppContainer store={store} routes={routes}/>,
    MOUNT_NODE,
  )
}

if (__DEV__) {
  global.http = http
  global.store = store

  if (module.hot) {
    const renderApp = render
    const renderError = (error: any) => {
      const RedBox = require('redbox-react').default

      ReactDOM.render(<RedBox error={error}/>, MOUNT_NODE)
    }

    render = () => {
      try {
        renderApp()
      } catch (error) {
        console.error(error)
        renderError(error)
      }
    }

    module.hot.accept('./routes/baseRoute', () =>
      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE)
        render()
      }),
    )
  }
}

render()
